package com.douyin.dto.OrderDTO;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderId;                // 订单 ID
    private Long userId;                 // 用户 ID
    private Double totalAmount;          // 订单总金额
    private String status;               // 订单状态
    private List<OrderItemResponse> items;  // 订单项列表

    @Data
    public static class OrderItemResponse {
        private Long productId;    // 商品 ID
        private Integer quantity;   // 商品数量
        private Double price;       // 商品价格
    }
}
