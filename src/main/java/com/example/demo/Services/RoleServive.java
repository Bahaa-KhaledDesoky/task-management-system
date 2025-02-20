package com.example.demo.Services;

import com.example.demo.Dtos.RoleDto;
import com.example.demo.model.Role;

public interface RoleServive {
    public void addRole(Role role);
    public RoleDto findRole(String name);
    public RoleDto mapRoleDto(Role role);
}
