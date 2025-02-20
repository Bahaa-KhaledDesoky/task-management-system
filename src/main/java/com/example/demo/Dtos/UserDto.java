package com.example.demo.Dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String phone;
}
