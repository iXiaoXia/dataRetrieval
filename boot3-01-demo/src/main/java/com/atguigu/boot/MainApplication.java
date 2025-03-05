package com.atguigu.boot;

import com.atguigu.boot.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication //这是一个SpringBoot
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
    
    @Bean
    public CommandLineRunner testDatabase(UserService userService) {
        return args -> {
            System.out.println("测试数据库连接...");
            userService.testDatabaseConnection();
        };
    }

}
