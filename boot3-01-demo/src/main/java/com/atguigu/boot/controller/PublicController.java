package com.atguigu.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/public")
    public String publicPage() {
        return "这是一个公开页面，无需认证";
    }
    
    @GetMapping("/")
    public String home() {
        return "首页 - 无需认证";
    }
} 