package com.douyin.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Order {
    private Long id;               // 订单 ID
    private Long userId;           // 用户 ID
    private Date createdAt;        // 订单创建时间
    private Date updatedAt;        // 订单更新时间
    private Double totalAmount;    // 订单总金额
    private String status;         // 订单状态（例如：pending, completed, canceled）
}
