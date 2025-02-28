package com.douyin.controller;

import com.douyin.dto.PaymentResponse;
import com.douyin.service.PaymentService;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 订单结算服务接口
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 订单结算
     * 计算订单总金额、检查库存、更新订单状态和商品库存
     */
    @PostMapping("/settle/{orderId}")
    public ResponseEntity<?> settleOrder(@PathVariable Long orderId) {
        try {
            Long userId = Long.valueOf(StpUtil.getLoginId().toString());
            PaymentResponse paymentResponse = paymentService.settleOrder(orderId,userId);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e) {
            e.printStackTrace(); // 打印完整异常堆栈
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 支付订单：将订单状态更新为 paid
     * 请求示例：POST /payment/pay/1
     */
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payOrder(@PathVariable Long orderId) {
        try {
            // 通过 Sa-Token 获取当前用户 ID（如果需要校验订单归属）
            Long userId = Long.valueOf(StpUtil.getLoginId().toString());
            PaymentResponse response = paymentService.payOrder(orderId,userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 取消支付：将订单状态更新为 canceled，并回退库存
     * 请求示例：POST /payment/cancel/1
     */
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelPayment(@PathVariable Long orderId) {
        try {
            Long userId = Long.valueOf(StpUtil.getLoginId().toString());
            PaymentResponse response = paymentService.cancelPayment(orderId,userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
