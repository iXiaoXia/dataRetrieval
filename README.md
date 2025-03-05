# 技术栈

后端: SpringBoot 3.4.3 + MyBatis + Spring Security
前端: Vue 3 + Axios
数据库: MySQL 8




# 如何启动这个项目


## 1. 准备数据库


首先，需要创建并初始化MySQL数据库：

1. 登录MySQL：

> mysql -u root -p

2. 创建数据库:

> CREATE DATABASE demo_db;
>    USE demo_db;

3. 执行数据库脚本（database.sql）：
```sql
   -- 用户表
   CREATE TABLE `users` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `username` varchar(50) NOT NULL,
     `password` varchar(100) NOT NULL,
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
     `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_username` (`username`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
   
   -- 统计数据表 (示例)
   CREATE TABLE `statistics` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `name` varchar(50) NOT NULL,
     `value` int DEFAULT '0',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
     `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
   
   -- 插入测试数据
   INSERT INTO `users` (`username`, `password`) VALUES ('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG'); -- 密码为'password'
   INSERT INTO `statistics` (`name`, `value`) VALUES ('访问次数', 0);
   INSERT INTO `statistics` (`name`, `value`) VALUES ('登录次数', 0);
```