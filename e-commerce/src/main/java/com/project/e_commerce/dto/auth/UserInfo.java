package com.project.e_commerce.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private int id;
    private String email;
    private String role;
}
