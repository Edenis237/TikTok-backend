package com.douyin.service;

import com.douyin.entity.User;

public interface UserService {
    /**
     * 创建用户（注册）
     */
    User createUser(User user);

    /**
     * 用户登录
     */
    User login(String username, String password);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 删除用户
     */
    boolean deleteUser(Long id);

    /**
     * 更新用户信息
     */
    User updateUser(Long id, User user);

    /**
     * 获取当前登录用户信息
     */
    User getCurrentUser();
}
