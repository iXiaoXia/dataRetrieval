package com.atguigu.boot.mapper;

import com.atguigu.boot.entity.Statistics;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    
    @Select("SELECT * FROM statistics")
    List<Statistics> findAll();
    
    @Select("SELECT * FROM statistics WHERE name = #{name}")
    Statistics findByName(@Param("name") String name);
    
    @Update("UPDATE statistics SET value = value + 1 WHERE name = #{name}")
    int incrementByName(@Param("name") String name);
} 