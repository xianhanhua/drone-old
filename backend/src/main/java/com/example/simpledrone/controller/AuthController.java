package com.example.simpledrone.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simpledrone.dto.ApiResponse;
import com.example.simpledrone.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final String token;
    private final String username;

    public AuthController(@Value("${app.security.api-token}") String token,
                          @Value("${app.security.username}") String username) {
        this.token = token;
        this.username = username;
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(request.getUsername(), request.getPassword()));
        return ApiResponse.ok(user(true));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        SecurityUtils.getSubject().logout();
        return ApiResponse.ok(null);
    }

    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(@RequestHeader(value = "X-Auth-Token", required = false) String requestToken) {
        return ApiResponse.ok(user(token.equals(requestToken)));
    }

    private Map<String, Object> user(boolean authenticated) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("authenticated", authenticated);
        map.put("username", authenticated ? username : "");
        map.put("token", authenticated ? token : "");
        return map;
    }
}
