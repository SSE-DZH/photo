package com.zhiend.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhiend.photo.dto.OrderDTO;
import com.zhiend.photo.entity.BackPage;
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
    public Long createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setPhotoId(orderDTO.getPhotoId());
        order.setAmount(new BigDecimal("0.01")); // 设置价格为0.01
        order.setOrderTime(LocalDateTime.now());
        order.setPaymentStatus(PaymentStatus.UNPAID); // 设置初始状态为未支付
        this.save(order);
        return order.getId();
    }

    @Override
    public Order getUnpaidOrdersByOrderId(Long orderId) {
        return this.getOne(new QueryWrapper<Order>()
                .eq("id", orderId)
                .eq("payment_status", PaymentStatus.UNPAID));
    }

    @Override
    public BackPage<Order> queryOrderPage(Long pageNo, Long pageSize) {
        BackPage<Order> orderBackPage = new BackPage<>();
        // 设置条件构造器
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是第几页，而第二个参数是每页的记录数
        Page<Order> OrderPage = new Page<>(pageNo, pageSize);
        // page(OrderPage, wrapper)这里的第一个参数就是上面定义了的Page对象，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<Order> OrderIPage = page(OrderPage, wrapper);
        // 封装数据，其中getRecords()是获取记录数，getCurrent()获取当前页数，getPages()获取总页数，getTotal()获取记录总数，还要其他更多的方法，大家可以自行查看，在这里就不过多赘述了
        orderBackPage.setContentList(OrderIPage.getRecords());
        orderBackPage.setCurrentPage(OrderIPage.getCurrent());
        orderBackPage.setTotalPage(OrderIPage.getPages());
        orderBackPage.setTotalNum(OrderIPage.getTotal());
        return orderBackPage;
    }

    @Override
    public BackPage<Order> queryOrderPageByUserId(Long userId, Long pageNo, Long pageSize) {
        BackPage<Order> orderBackPage = new BackPage<>();
        // 设置条件构造器
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是第几页，而第二个参数是每页的记录数
        Page<Order> OrderPage = new Page<>(pageNo, pageSize);
        // page(OrderPage, wrapper)这里的第一个参数就是上面定义了的Page对象，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<Order> OrderIPage = page(OrderPage, wrapper);
        // 封装数据，其中getRecords()是获取记录数，getCurrent()获取当前页数，getPages()获取总页数，getTotal()获取记录总数，还要其他更多的方法，大家可以自行查看，在这里就不过多赘述了
        orderBackPage.setContentList(OrderIPage.getRecords());
        orderBackPage.setCurrentPage(OrderIPage.getCurrent());
        orderBackPage.setTotalPage(OrderIPage.getPages());
        orderBackPage.setTotalNum(OrderIPage.getTotal());
        return orderBackPage;
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return this.list(new QueryWrapper<Order>().eq("user_id", userId));
    }


}
