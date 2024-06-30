package com.zhiend.photo.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zhiend.photo.config.AliPayConfig;
import com.zhiend.photo.entity.Order;
import com.zhiend.photo.entity.PaymentStatus;
import com.zhiend.photo.service.impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
@Api(tags = "支付宝支付接口")
public class AliPayController {
 
    // 支付宝沙箱网关地址
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";
 
    @Resource
    private AliPayConfig aliPayConfig;
 
    @Resource
    private OrderServiceImpl ordersService;

    /**
     * 处理支付宝支付请求。
     * 当用户点击支付按钮后，前端会发送一个GET请求到此接口，接口接收到请求后会生成支付宝支付页面并返回给用户。
     *
     * @param orderNo 订单号，用于查询待支付的订单。
     * @param httpResponse 用于直接向客户端浏览器发送响应。
     * @throws Exception 如果支付页面生成过程中发生错误。
     */
    @ApiOperation(value = "支付宝支付")
    @GetMapping("/pay")  //  /alipay/pay?orderNo=xxx
    public void pay(Long orderNo, HttpServletResponse httpResponse) throws Exception {
        // 查询订单信息
        Order order = ordersService.getUnpaidOrdersByOrderId(orderNo);
        if (order == null) {
            return;
        }
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
 
        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", order.getId());  // 我们自己生成的订单编号
        bizContent.set("total_amount", order.getAmount()); // 订单的总金额
        bizContent.set("subject", "支付宝支付");   // 支付的名称
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());
        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    /**
     * 处理支付宝异步通知的接口。
     * 接收支付宝异步通知请求，验证通知的合法性，并根据通知内容更新订单状态。
     *
     * @param request 异步通知请求的HttpServletRequest对象。
     * @throws Exception 如果验证过程中出现异常。
     */
    @ApiOperation(value = "支付宝异步回调")
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public void payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
 
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
 
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
//                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));

                Long tradeNo = Long.parseLong(params.get("out_trade_no"));
                Order order = ordersService.getUnpaidOrdersByOrderId(tradeNo);
                order.setPaymentStatus(PaymentStatus.PAID);
                ordersService.updateById(order);
            }
        }
    }
 
}