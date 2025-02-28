package com.douyin.controller;

import com.douyin.dto.UserDTO.CreateUserRequest;
import com.douyin.dto.UserDTO.LoginRequest;
import com.douyin.dto.UserDTO.LoginResponse;
import com.douyin.dto.UserDTO.UpdateUserRequest;
import com.douyin.entity.User;
import com.douyin.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务接口
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户（注册）
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        try {
            User created = userService.createUser(user);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            if (user != null ) {
                // 用户名和密码正确，使用 Sa-Token 登录
                StpUtil.login(user.getId());
                // 在需要登录验证的接口中添加调试代码
                System.out.println("当前Token：" + StpUtil.getTokenValue());
                System.out.println("是否登录：" + StpUtil.isLogin());

                String token = StpUtil.getTokenValue();  // 获取生成的 token
                return ResponseEntity.ok(new LoginResponse(token));  // 返回 token

            } else {

                return ResponseEntity.status(401).body("用户名或密码错误");  // 登录失败
            }
        } catch (Exception e) {
            e.printStackTrace(); // 打印完整异常堆栈
            System.out.println("Login Request: " + loginRequest.getUsername() + ", " + loginRequest.getPassword());
            System.out.println("异常错误");
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    /**
     * token 校验接口
     * 调用 Sa-Token 提供的 StpUtil.checkLogin() 方法校验当前请求的 token 是否有效
     * 若 token 有效，则返回登录标识；否则抛出异常并返回错误响应
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verify() {
        try {
            // 校验当前请求是否已登录（如果未登录，会抛出异常）
            StpUtil.checkLogin();
            Object loginId = StpUtil.getLoginId();
            return ResponseEntity.ok("Token 有效，用户标识：" + loginId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Token 无效或已过期");
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            userService.logout();
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 删除用户（注销）
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            boolean result = userService.deleteUser(id);
            if (result) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Delete failed");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        User user = new User();
        user.setUsername(updateUserRequest.getUsername());
        user.setPassword(updateUserRequest.getPassword());
        try {
            User updated = userService.updateUser(id, user);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            User user = userService.getCurrentUser();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<Long> getCurrentUserId() {
        if (!StpUtil.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long userId = StpUtil.getLoginIdAsLong();
        return ResponseEntity.ok(userId);
    }
}
