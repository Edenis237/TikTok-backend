package com.douyin.dto.CartDTO;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long productId;  // 商品 ID
    private Integer quantity; // 商品数量
}
