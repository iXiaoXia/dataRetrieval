package com.atguigu.boot.service;

import com.atguigu.boot.entity.User;
import com.atguigu.boot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    public boolean authenticate(String username, String password) {
        User user = findByUsername(username);
        if (user == null) {
            return false;
        }
        
        // 打印调试信息
        System.out.println("数据库中的密码: " + user.getPassword());
        System.out.println("输入的密码: " + password);
        
        // 如果数据库中的密码没有加密（不是以$2a$开头），则直接比较
        if (!user.getPassword().startsWith("$2a$")) {
            return password.equals(user.getPassword());
        }
        
        // 否则使用BCrypt验证
        return passwordEncoder.matches(password, user.getPassword());
    }
    
    public void testDatabaseConnection() {
        User user = userMapper.findByUsername("xzz");
        System.out.println("找到用户: " + (user != null ? user.getUsername() : "未找到"));
    }
    
    public void createUser(String username, String encodedPassword) {
        // 这里需要在UserMapper中添加insertUser方法
        userMapper.insertUser(username, encodedPassword);
    }
} 