package com.douyin.service.impl;

import com.douyin.entity.User;
import com.douyin.mapper.UserMapper;
import com.douyin.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User createUser(User user) {
        // 检查用户名是否已存在
        User existing = userMapper.selectByUsername(user.getUsername());
        if (existing != null) {
            throw new RuntimeException("Username already exists");
        }
        // 注意：实际项目中应对密码进行加密处理
        userMapper.insertUser(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        return user;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = userMapper.selectById(id);
        if (existing == null) {
            throw new RuntimeException("User not found");
        }
        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());
        userMapper.updateUser(existing);
        return existing;
    }

    @Override
    public User getCurrentUser() {
        // 从 Sa-Token 获取当前登录用户的标识
        Object loginId = StpUtil.getLoginId();
        if (loginId == null) {
            throw new RuntimeException("User not logged in");
        }
        Long userId = Long.valueOf(loginId.toString());
        return userMapper.selectById(userId);
    }
}
