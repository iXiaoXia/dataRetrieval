package com.atguigu.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot3!";
    }
}
