package com.example.demo.Dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInRequest {
    String userName;
    String password;

}
