package com.example.demo.Dtos;

import com.example.demo.model.AppUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class RoleDto {

    private String roleName;
    private String description;
}
