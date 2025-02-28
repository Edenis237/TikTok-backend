package com.douyin.dto.UserDTO;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String password;
}
