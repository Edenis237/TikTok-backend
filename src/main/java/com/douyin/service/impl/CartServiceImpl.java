package com.douyin.service.impl;

import com.douyin.dto.CartDTO.AddToCartRequest;
import com.douyin.dto.CartDTO.CartResponse;
import com.douyin.entity.Cart;
import com.douyin.entity.CartItem;
import com.douyin.entity.Product;
import com.douyin.mapper.CartMapper;
import com.douyin.mapper.CartItemMapper;
import com.douyin.service.CartService;
import com.douyin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartMapper.createCart(cart);
        return cart;
    }

    @Override
    public CartResponse getCart(Long userId) {
        Cart cart = cartMapper.selectCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("购物车未找到");
        }

        List<CartItem> items = cartItemMapper.selectItemsByCartId(cart.getId());
        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());

        // 获取商品的详细信息
        for (CartItem item : items) {
            CartResponse.CartItemResponse itemResponse = new CartResponse.CartItemResponse();
            itemResponse.setProductId(item.getProduct().getId());
            itemResponse.setQuantity(item.getQuantity());

            // 获取商品信息
            Product product = item.getProduct();
            if (product != null) {
                itemResponse.setProductName(product.getName());
                itemResponse.setPrice(product.getPrice());
            }
            response.getItems().add(itemResponse);
        }

        return response;
    }

    @Override
    public CartItem addToCart(Long userId, AddToCartRequest request) {
        // 获取用户购物车
        Cart cart = cartMapper.selectCartByUserId(userId);
        if (cart == null) {
            cart = createCart(userId);  // 如果没有购物车则创建
        }

        // 获取商品信息，确保商品存在
        Product product = productService.getProductById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }

        // 创建购物车项并添加
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cart.getId());
        cartItem.setProduct(product);  // 关联商品实体
        cartItem.setQuantity(request.getQuantity());

        // 判断商品是否已经在购物车中
        List<CartItem> existingItems = cartItemMapper.selectItemsByCartId(cart.getId());
        boolean itemExists = false;
        for (CartItem item : existingItems) {
            if (item.getProduct() != null && item.getProduct().getId().equals(cartItem.getProduct().getId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                cartItemMapper.updateItemQuantity(item);  // 更新购物车中的商品数量
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItemMapper.addItemToCart(cartItem);  // 添加新商品到购物车
        }

        return cartItem;
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartMapper.selectCartByUserId(userId);
        if (cart != null) {
            cartItemMapper.removeItemFromCart(cart.getId(), null);
        }
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        Cart cart = cartMapper.selectCartByUserId(userId);
        if (cart != null) {
            cartItemMapper.removeItemFromCart(cart.getId(), productId);
        }
    }
}