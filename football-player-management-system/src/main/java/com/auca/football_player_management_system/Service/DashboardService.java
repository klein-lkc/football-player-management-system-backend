package com.auca.football_player_management_system.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.ContractStatus;
import com.auca.football_player_management_system.Repository.ClubRepository;
import com.auca.football_player_management_system.Repository.ContractRepository;
import com.auca.football_player_management_system.Repository.PerformanceReportRepository;
import com.auca.football_player_management_system.Repository.PlayerReposistory;
import com.auca.football_player_management_system.Repository.StadiumRepository;

@Service 
public class DashboardService {

    @Autowired
    private PlayerReposistory playerRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private StadiumRepository stadiumRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PerformanceReportRepository performanceReportRepository;

    public Map<String, Long> getDashboardSummary(){
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("players", playerRepository.count());
        summary.put("clubs", clubRepository.count());

        long freeStadiumCount = stadiumRepository.findFreeStadiums().size();
        summary.put("freeStadiums", freeStadiumCount);

        long contractsToday = contractRepository.countBySigningDate(LocalDate.now());
        summary.put("contractsToday", contractsToday);

        long offeredCount = contractRepository.countByStatus(ContractStatus.OFFERED);
        summary.put("offered", offeredCount);

        summary.put("performanceReports", performanceReportRepository.count());
        return summary;
    }
}
