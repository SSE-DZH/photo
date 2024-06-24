package com.zhiend.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhiend.photo.dto.OrderDTO;
import com.zhiend.photo.entity.Order;
import com.zhiend.photo.entity.PaymentStatus;
import com.zhiend.photo.mapper.OrderMapper;
import com.zhiend.photo.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Override
    public boolean createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setPhotoId(orderDTO.getPhotoId());
        order.setAmount(new BigDecimal("0.01")); // 设置价格为0.01
        order.setOrderTime(LocalDateTime.now());
        order.setPaymentStatus(PaymentStatus.UNPAID); // 设置初始状态为未支付
        return this.save(order);
    }
    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return this.list(new QueryWrapper<Order>().eq("user_id", userId));
    }
}
