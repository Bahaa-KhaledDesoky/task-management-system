package com.example.demo.controllers;

import com.example.demo.Dtos.ActivityLogsDto;
import com.example.demo.Dtos.RoleDto;
import com.example.demo.Dtos.UserDto;
import com.example.demo.Services.ActivityLogsServicelmp;
import com.example.demo.Services.RoleServiceImp;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.model.AppUser;
import com.example.demo.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserServiceImp userService;
    private final RoleServiceImp roleService;
    private final ActivityLogsServicelmp activityLogsServicelmp;
    private final JwtUtils jwtUtils;
    @GetMapping("/users")
    public List<UserDto> getAllUsers()
    {
        return userService.findAllUser();
    }
    @GetMapping("/users/{username}")
    public AppUser getUser(@PathVariable String username) {
        return userService.findUserByUserName(username);
    }
    @GetMapping("/logs")
    public ResponseEntity<?> allLogs() {
        try {
            return ResponseEntity.ok(activityLogsServicelmp.allLogs());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
        }
    }
    @GetMapping("/logs/{id}")
    public ResponseEntity<?> allLogsOfUser(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(activityLogsServicelmp.allLogsOfOwner(id));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
        }
    }
    @GetMapping("/roles/{name}")
    public RoleDto getRole(@PathVariable String name)
    {
        return roleService.findRole(name);
    }
    @PostMapping("/addroletouser/{username}/{role}")
    public ResponseEntity<?> addRoleToUser(HttpServletRequest httpServletRequest, @PathVariable String role, @PathVariable String username) {
        boolean flag= userService.AddRoleToUser(role,username);
        if (flag)
        {
            AppUser user=jwtUtils.getUserfromRequest(httpServletRequest);
            ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                    "add "+role+" Role to "+username,
                    "User_roles",
                    null ,
                    LocalDateTime.now(),
                    user.getId());
            activityLogsServicelmp.addLog(activityLogsDto);
            return ResponseEntity.ok(userService.findUserByUserName(username).getRoles());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong role or username");
    }
    @DeleteMapping("/deleterolefromuser/{username}/{role}")
    public ResponseEntity<?> deleteRoleFromUser(HttpServletRequest httpServletRequest,@PathVariable String role, @PathVariable String username) {
        boolean flag= userService.deleteRolefromUser(role,username);
        if (flag){
            AppUser user=jwtUtils.getUserfromRequest(httpServletRequest);
            ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                    "delete "+role+" Role from "+username,
                    "User_roles",
                    null ,
                    LocalDateTime.now(),
                    user.getId());
            activityLogsServicelmp.addLog(activityLogsDto);
            return ResponseEntity.ok(userService.findUserByUserName(username).getRoles());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong role or username");
    }

}


