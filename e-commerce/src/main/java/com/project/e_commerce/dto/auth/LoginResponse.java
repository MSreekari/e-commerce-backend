package com.project.e_commerce.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private UserInfo userInfo;

    private static class UserInfo{
        private int id;
        private String email;
        private String role;
    }
}
