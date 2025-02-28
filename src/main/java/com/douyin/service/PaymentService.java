package com.douyin.service;

import com.douyin.entity.Order;
import com.douyin.dto.PaymentResponse;

public interface PaymentService {

    /**
     * 订单结算
     * 1. 计算订单总金额
     * 2. 检查库存
     * 3. 更新订单状态为已支付
     * 4. 更新商品库存
     */
    PaymentResponse settleOrder(Long orderId,Long userId);

    /**
     * 执行支付：将订单状态更新为 "paid"
     */
    PaymentResponse payOrder(Long orderId,Long userId);

    /**
     * 取消支付：将订单状态更新为 "canceled" 并回退库存
     */
    PaymentResponse cancelPayment(Long orderId,Long userId);
}
