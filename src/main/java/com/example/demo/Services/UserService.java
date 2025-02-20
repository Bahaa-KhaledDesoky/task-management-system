package com.example.demo.Services;

import com.example.demo.Dtos.SignIn;
import com.example.demo.Dtos.UserDto;
import com.example.demo.model.AppUser;
import com.example.demo.model.Project;
import com.example.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void AddUser(AppUser user);
    public boolean userExist(String user);
    public boolean roleExist(String role);
    public Optional<AppUser> findUserById(Integer id);
    public AppUser findUserByUserName(String userName);
    public void addProjectToUser(Project project);
    public List<UserDto> findAllUser();
    public UserDto mapUserDto(AppUser appUser);
    public boolean AddRoleToUser(String role,String username);
    public boolean deleteRolefromUser(String role,String username);
    public boolean roleExistInUser(Role role, AppUser user);
    public boolean projectExistInUser(Project project, AppUser user);

    public AppUser signInToUserMapping(SignIn signIn);
}
