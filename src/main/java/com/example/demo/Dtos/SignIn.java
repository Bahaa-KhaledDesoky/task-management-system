package com.example.demo.Dtos;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class SignIn {
    private String username;
    private String phone;
    private String password;
}
