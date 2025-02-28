package com.douyin.dto.OrderDTO;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequest {
    private List<OrderItemRequest> items;  // 订单项（商品及数量）

    @Data
    public static class OrderItemRequest {
        private Long productId;  // 商品 ID
        private Integer quantity; // 商品数量
    }
}
