package com.example.demo.Services;

import com.example.demo.model.CustomUserDetailsService;
import com.example.demo.model.RefreshTokenRequest;
import com.example.demo.model.TokenResponse;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final JwtUtils jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public TokenResponse accessToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String newAccessToken = jwtUtil.generateToken(username);
            return new TokenResponse(refreshTokenRequest.getRefreshToken(),newAccessToken);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
    public TokenResponse getRefreshToken(String username) {
            CustomUserDetails customUserDetails =  userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtUtil.generateToken(username);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);
            return new TokenResponse(newAccessToken, newRefreshToken);

    }

}