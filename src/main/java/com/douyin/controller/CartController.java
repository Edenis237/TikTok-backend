package com.douyin.controller;

import com.douyin.dto.CartDTO.AddToCartRequest;
import com.douyin.dto.CartDTO.CartResponse;
import com.douyin.entity.CartItem;
import com.douyin.service.CartService;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车服务接口
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 获取购物车信息
     */
    @GetMapping("/get")
    public ResponseEntity<?> getCart() {
        Long userId = Long.valueOf(StpUtil.getLoginId().toString());
        try {
            CartResponse cartResponse = cartService.getCart(userId);
            return ResponseEntity.ok(cartResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) {
        Long userId = Long.valueOf(StpUtil.getLoginId().toString());
        try {
            CartItem cartItem = cartService.addToCart(userId, request);
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        Long userId = Long.valueOf(StpUtil.getLoginId().toString());
        try {
            cartService.clearCart(userId);
            return ResponseEntity.ok("Cart cleared");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * 移除购物车中的商品
     */
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {
        Long userId = Long.valueOf(StpUtil.getLoginId().toString());
        try {
            cartService.removeFromCart(userId, productId);
            return ResponseEntity.ok("Item removed from cart");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
