package com.zhiend.photo.controller;


import com.zhiend.photo.dto.OrderDTO;
import com.zhiend.photo.entity.BackPage;
import com.zhiend.photo.entity.Order;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    /**
     * 创建订单
     * @param orderDTO 订单信息
     * @return 订单id
     */
    @ApiOperation(value = "创建订单")
    @PostMapping("/create")
    public Result<Long> createOrder(@RequestBody OrderDTO orderDTO) {
        return Result.success(orderService.createOrder(orderDTO));
    }

    /**
     * 根据用户id获取订单信息
     * @param userId 订单id
     * @return 订单信息
     */
    @ApiOperation(value = "根据用户id获取订单信息")
    @GetMapping("/{userId}")
    public Result<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return Result.success(orders);
    }

    /**
     * 获取所有订单信息
     * @return 所有订单信息
     */
    @ApiOperation(value = "获取所有订单信息")
    @GetMapping("/getAllPages")
    public BackPage<Order> queryOrderPage(@RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize) {
        return orderService.queryOrderPage(pageNo, pageSize);
    }


    //根据用户id获取所有分页订单信息

    /**
     * 根据用户id获取所有分页订单信息
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据用户id获取所有分页订单信息")
    @GetMapping("/getAllPagesByUserId")
    public BackPage<Order> queryOrderPageByUserId(@RequestParam("userId") Long userId, @RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize) {
        return orderService.queryOrderPageByUserId(userId, pageNo, pageSize);
    }



}
