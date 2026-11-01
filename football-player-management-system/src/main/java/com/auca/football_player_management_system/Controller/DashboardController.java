package com.auca.football_player_management_system.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auca.football_player_management_system.Service.DashboardService;

@RestController 
@RequestMapping ("/api/dashboard")
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // get dashboard summary
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getDashboardSummary(){
        Map<String, Long> summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }
    
}
