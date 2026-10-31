package com.auca.football_player_management_system.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.football_player_management_system.Domain.PerformanceReport;
import com.auca.football_player_management_system.Service.PerformanceReportService;

@RestController 
@RequestMapping("/api/performance-reports")
public class PerformanceReportController {
    
    private final PerformanceReportService performanceReportService;
        public PerformanceReportController(PerformanceReportService performanceReportService){
            this.performanceReportService=performanceReportService;
    }

    // save a reports
    @PostMapping("/save")
    public ResponseEntity<String> saveReport(@RequestParam UUID contractId, @RequestBody PerformanceReport report) {
        String result = performanceReportService.saveReport(contractId, report);
        if (result.equals("The contract with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        if (result.equals("Performance reports are only for SIGNED contracts")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // player report history
    @GetMapping("/player/{licenceNumber}")
    public ResponseEntity<List<PerformanceReport>> getReportsByPlayerLicence(@PathVariable String licenceNumber){
        List<PerformanceReport> reports =performanceReportService.getReportsByPlayerLicence(licenceNumber);
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    // delete 
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<String> deleteReport(@PathVariable UUID id){
        String result = performanceReportService.deleteReport(id);
        if(result.equals("The performance report with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
