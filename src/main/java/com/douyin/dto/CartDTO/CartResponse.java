package com.douyin.dto.CartDTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponse {
    private Long cartId;              // 购物车 ID
    private List<CartItemResponse> items =new ArrayList<>(); // 购物车中的商品项

    @Data
    public static class CartItemResponse {
        private Long productId;    // 商品 ID
        private String productName; // 商品名称
        private Integer quantity;   // 商品数量
        private Double price;       // 商品价格
    }
}
