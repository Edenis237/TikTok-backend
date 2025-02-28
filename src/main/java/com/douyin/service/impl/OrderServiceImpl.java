package com.douyin.service.impl;

import com.douyin.dto.OrderDTO.CreateOrderRequest;
import com.douyin.dto.OrderDTO.UpdateOrderRequest;
import com.douyin.dto.OrderDTO.OrderResponse;
import com.douyin.entity.Order;
import com.douyin.entity.OrderItem;
import com.douyin.entity.Product;
import com.douyin.mapper.OrderMapper;
import com.douyin.mapper.OrderItemMapper;
import com.douyin.service.OrderService;
import com.douyin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductService productService;

    @Override
    public Order createOrder(Long userId, CreateOrderRequest request) {
        List<OrderItem> items = new ArrayList<>();
        double totalAmount = 0.0;

        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            // 实时查询商品数据
            Product product = productService.getProductById(itemRequest.getProductId());
            if (product == null) {
                throw new RuntimeException("商品不存在: ID=" + itemRequest.getProductId());
            }
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("库存不足: " + product.getName());
            }
            totalAmount += product.getPrice() * itemRequest.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            items.add(orderItem);
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus("pending");
        orderMapper.createOrder(order);

        items.forEach(item -> {
            item.setOrderId(order.getId());
            orderItemMapper.insertOrderItem(item);
        });

        return order;
    }

    @Override
    public Order updateOrderStatus(Long orderId, UpdateOrderRequest request) {
        orderMapper.updateOrderStatus(orderId, request.getStatus());
        return orderMapper.selectOrderForUpdate(orderId);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        Order order = orderMapper.selectOrderForUpdate(orderId);
        List<OrderItem> items = orderItemMapper.selectItemsByOrderId(orderId);

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setUserId(order.getUserId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());

        List<OrderResponse.OrderItemResponse> itemResponses = items.stream().map(item -> {
            OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
            itemResponse.setProductId(item.getProductId());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            return itemResponse;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderMapper.selectOrdersByUserId(userId);
        return orders.stream().map(order -> {
            List<OrderItem> items = orderItemMapper.selectItemsByOrderId(order.getId());

            OrderResponse response = new OrderResponse();
            response.setOrderId(order.getId());
            response.setUserId(order.getUserId());
            response.setTotalAmount(order.getTotalAmount());
            response.setStatus(order.getStatus());

            List<OrderResponse.OrderItemResponse> itemResponses = items.stream().map(item -> {
                OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
                itemResponse.setProductId(item.getProductId());
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setPrice(item.getPrice());
                return itemResponse;
            }).collect(Collectors.toList());

            response.setItems(itemResponses);
            return response;
        }).collect(Collectors.toList());
    }
}