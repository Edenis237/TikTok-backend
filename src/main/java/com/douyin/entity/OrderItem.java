package com.douyin.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OrderItem {
    private Long id;               // 订单项 ID

    @Column(name = "order_id")
    private Long orderId;          // 订单 ID

    @Column(name = "product_id")
    private Long productId;        // 商品 ID

    private Integer quantity;      // 商品数量
    private Double price;          // 商品价格
}
