package com.zhiend.photo.service;

import com.zhiend.photo.dto.OrderDTO;
import com.zhiend.photo.entity.BackPage;
import com.zhiend.photo.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
public interface IOrderService extends IService<Order> {

    List<Order> getOrdersByUser(Long userId);

    Long createOrder(OrderDTO orderDTO);

    //根据用户id获取未支付订单
    Order getUnpaidOrdersByOrderId(Long orderId);

    BackPage<Order> queryOrderPage(Long pageNo, Long pageSize);

    BackPage<Order> queryOrderPageByUserId(Long userId, Long pageNo, Long pageSize);
}
