package com.atguigu.boot.controller;

import com.atguigu.boot.entity.Statistics;
import com.atguigu.boot.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<List<Statistics>> getAllStatistics() {
        // 更新访问次数
        statisticsService.incrementStatistic("访问次数");
        
        List<Statistics> statistics = statisticsService.getAllStatistics();
        return ResponseEntity.ok(statistics);
    }
    
    @GetMapping("/{name}")
    public ResponseEntity<Statistics> getStatisticByName(@PathVariable String name) {
        Statistics statistic = statisticsService.getStatisticByName(name);
        if (statistic != null) {
            return ResponseEntity.ok(statistic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 