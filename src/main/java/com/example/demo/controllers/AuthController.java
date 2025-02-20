package com.example.demo.controllers;

import com.example.demo.Dtos.ActivityLogsDto;
import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.SignIn;
import com.example.demo.Dtos.UserDto;
import com.example.demo.Services.ActivityLogsServicelmp;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.model.AppUser;
import com.example.demo.model.RefreshTokenRequest;
import com.example.demo.model.TokenResponse;
import com.example.demo.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserServiceImp userServiceImp;
    private final ActivityLogsServicelmp activityLogsServicelmp;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignIn signIn) {

        try {
            if (userServiceImp.userExist(signIn.getUsername())) {
                return ResponseEntity.status(HttpStatus.IM_USED)
                        .body("This user name is taken");
            }
            AppUser user =userServiceImp.signInToUserMapping(signIn);
            userServiceImp.AddUser(user);
            user=userServiceImp.findUserByUserName(signIn.getUsername());
            ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                    "Sign up "+signIn.getUsername() ,
                    "User",
                    user.getId() ,
                    LocalDateTime.now(),
                    user.getId());
            activityLogsServicelmp.addLog(activityLogsDto);
            return ResponseEntity.ok(user);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequest logInRequest) {

        try {
            if (!userServiceImp.userExist(logInRequest.getUserName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("wrong user name");
            }
            AppUser user=userServiceImp.findUserByUserName(logInRequest.getUserName());
            if(!user.getPassword().equals(logInRequest.getPassword()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("wrong password");

            TokenResponse tokenResponse=authService.getRefreshToken(user.getUsername());
            user.setRefreshToken(tokenResponse.getRefreshToken());
            userServiceImp.AddUser(user);

            return ResponseEntity.ok(tokenResponse.getAccessToken());
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        }

    }


}