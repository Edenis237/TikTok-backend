package com.douyin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentResponse {
    private Long orderId;        // 订单 ID
    private Double totalAmount;  // 订单总金额
    private String status;       // 订单状态（例如：paid）


}
