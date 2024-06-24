package com.zhiend.photo.service.impl;

import com.zhiend.photo.entity.Order;
import com.zhiend.photo.mapper.OrderMapper;
import com.zhiend.photo.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
