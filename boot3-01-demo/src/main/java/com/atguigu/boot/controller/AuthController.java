package com.atguigu.boot.controller;

import com.atguigu.boot.service.UserService;
import com.atguigu.boot.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        System.out.println("尝试登录: 用户名=" + username + ", 密码长度=" + (password != null ? password.length() : 0));
        
        // 使用数据库认证
        boolean isAuthenticated = userService.authenticate(username, password);
        
        // 同时支持硬编码的测试账号
        if (!isAuthenticated) {
            isAuthenticated = "admin".equals(username) && "password".equals(password);
        }
        
        System.out.println("认证结果: " + isAuthenticated);
        
        if (isAuthenticated) {
            // 更新登录次数统计
            try {
                statisticsService.incrementStatistic("登录次数");
            } catch (Exception e) {
                System.out.println("更新统计数据失败: " + e.getMessage());
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "登录成功");
            response.put("token", "sample-token-" + username);
            
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "用户名或密码错误");
            
            return ResponseEntity.status(401).body(response);
        }
    }
} 