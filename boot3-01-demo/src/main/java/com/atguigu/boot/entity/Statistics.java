package com.atguigu.boot.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Statistics {
    private Long id;
    private String name;
    private Integer value;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 