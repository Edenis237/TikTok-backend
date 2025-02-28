package com.douyin.entity;

import lombok.Data;

@Data
public class Cart {
    private Long id;        // 购物车 ID
    private Long userId;    // 用户 ID（与 Sa-Token 的登录用户关联）
}
