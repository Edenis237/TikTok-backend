package com.douyin.dto.OrderDTO;

import lombok.Data;
import java.util.List;

@Data
public class UpdateOrderRequest {
    private String status;  // 订单状态（如：pending, completed, canceled）
}
