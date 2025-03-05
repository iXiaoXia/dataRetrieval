package com.atguigu.boot.service;

import com.atguigu.boot.entity.Statistics;
import com.atguigu.boot.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    
    @Autowired
    private StatisticsMapper statisticsMapper;
    
    public List<Statistics> getAllStatistics() {
        return statisticsMapper.findAll();
    }
    
    public Statistics getStatisticByName(String name) {
        return statisticsMapper.findByName(name);
    }
    
    public void incrementStatistic(String name) {
        statisticsMapper.incrementByName(name);
    }
} 