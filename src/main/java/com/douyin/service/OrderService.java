package com.douyin.service;

import com.douyin.dto.OrderDTO.CreateOrderRequest;
import com.douyin.dto.OrderDTO.UpdateOrderRequest;
import com.douyin.dto.OrderDTO.OrderResponse;
import com.douyin.entity.Order;

import java.util.List;

public interface OrderService {
    /**
     * 创建订单
     */
    Order createOrder(Long userId, CreateOrderRequest request);

    /**
     * 修改订单状态
     */
    Order updateOrderStatus(Long orderId, UpdateOrderRequest request);

    /**
     * 获取订单信息
     */
    OrderResponse getOrder(Long orderId);

    /**
     * 获取用户的所有订单
     */
    List<OrderResponse> getOrdersByUserId(Long userId);
}
