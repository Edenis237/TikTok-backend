package com.douyin.dto.UserDTO;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
}
