package com.example.demo.Services;

import com.example.demo.Dtos.SignIn;
import com.example.demo.Dtos.UserDto;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.AppUser;
import com.example.demo.model.Project;
import com.example.demo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public void AddUser(AppUser user)
    {
        userRepository.save(user);
    }

    @Override
    public boolean userExist(String user) {
        if(userRepository.findByUsername(user).isPresent()) {
            return true;
        }
        return false;
    }
    @Override
    public boolean roleExist(String role) {
        if(roleRepository.findByRolename(role).isPresent())
            return true;
        return false;
    }

    public Optional<AppUser> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public AppUser findUserByUserName(String userName) {
        return userRepository.findByUsername(userName).get();
    }
    @Override
    public void addProjectToUser(Project project) {
        AppUser appUser=project.getProjectOwner();
        appUser.getWorkOn().add(project);
        userRepository.save(appUser);
    }

    public List<UserDto> findAllUser()
    {
        List<AppUser> users =userRepository.findAll();
        return users.stream().map((user)->mapUserDto(user)).collect(Collectors.toList());
    }
    public UserDto mapUserDto(AppUser appUser)
    {
        return UserDto.builder().
                username(appUser.getUsername())
                .phone(appUser.getPhone())
                .build();
    }

    @Override
    public boolean AddRoleToUser(String rolename, String username) {
        try {
            if (!userExist(username))
                return false;
            if (!roleExist(rolename))
                return false;
           AppUser user=userRepository.findByUsername(username).get();
           Role role =roleRepository.findByRolename(rolename).get();
            if (roleExistInUser(role,user))
                return false;
            user.getRoles().add(role);
           userRepository.save(user);
           return true;
       }
       catch (Exception e)
       {
           return false;
       }

    }

    @Override
    public boolean deleteRolefromUser(String rolename, String username) {
        try {
            if (!userExist(username))
                return false;
            if (!roleExist(rolename))
                return false;
            AppUser user=userRepository.findByUsername(username).get();
            Role role =roleRepository.findByRolename(rolename).get();
            if (!roleExistInUser(role,user))
                return false;
            user.getRoles().remove(role);
            userRepository.save(user);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean roleExistInUser(Role role,AppUser user) {
        return user.getRoles().contains(role);
    }

    @Override
    public boolean projectExistInUser(Project project, AppUser user) {
        return user.getWorkOn().contains(project);
    }


    @Override
    public AppUser signInToUserMapping(SignIn signIn) {
        return AppUser.builder()
                .username(signIn.getUsername())
                .phone(signIn.getPhone())
                .password(signIn.getPassword())
                .build();
    }
}
