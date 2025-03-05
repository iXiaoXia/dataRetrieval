package com.atguigu.boot.controller;

import com.atguigu.boot.entity.User;
import com.atguigu.boot.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/public")
    public String publicEndpoint() {
        return "这是一个公开的端点，无需认证";
    }

    @GetMapping("/test/private")
    public String privateEndpoint() {
        return "这是一个私有端点，需要认证";
    }


} 