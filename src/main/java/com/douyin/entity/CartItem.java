package com.douyin.entity;

import lombok.Data;

@Data
public class CartItem {
    private Long id;        // 购物车项 ID
    private Long cartId;    // 购物车 ID
    private Product product; // 关联的商品实体
    private Integer quantity; // 商品数量
}