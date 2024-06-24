package com.zhiend.photo.controller;


import com.zhiend.photo.dto.OrderDTO;
import com.zhiend.photo.entity.Order;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IOrderService;
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
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody OrderDTO orderDTO) {
        boolean success = orderService.createOrder(orderDTO);
        if (success) {
            return Result.success("Order created successfully");
        } else {
            return Result.error("Order creation failed");
        }
    }

    @GetMapping("/{userId}")
    public Result<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return Result.success(orders);
    }
}
