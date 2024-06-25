package com.zhiend.photo.service;

import com.zhiend.photo.dto.OrderDTO;
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
}
