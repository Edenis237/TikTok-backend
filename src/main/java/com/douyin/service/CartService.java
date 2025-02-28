package com.douyin.service;

import com.douyin.dto.CartDTO.AddToCartRequest;
import com.douyin.dto.CartDTO.CartResponse;
import com.douyin.entity.Cart;
import com.douyin.entity.CartItem;

public interface CartService {
    /**
     * 创建购物车
     */
    Cart createCart(Long userId);

    /**
     * 获取用户购物车信息
     */
    CartResponse getCart(Long userId);

    /**
     * 添加商品到购物车
     */
    CartItem addToCart(Long userId, AddToCartRequest request);

    /**
     * 清空购物车
     */
    void clearCart(Long userId);

    /**
     * 移除购物车中的商品
     */
    void removeFromCart(Long userId, Long productId);
}
