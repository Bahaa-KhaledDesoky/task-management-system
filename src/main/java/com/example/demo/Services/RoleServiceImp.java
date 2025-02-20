package com.example.demo.Services;

import com.example.demo.Dtos.RoleDto;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleServive{
    private final RoleRepository roleRepository;
    public void addRole(Role role)
    {
        roleRepository.save(role);
    }
    public RoleDto findRole(String name)
    {
        Role role = roleRepository.findByRolename(name).get();
        return mapRoleDto(role);

    }
    public RoleDto mapRoleDto(Role role)
    {
        return RoleDto.builder()
                .roleName(role.getRolename())
                .description(role.getDescription())
                .build();
    }

}
