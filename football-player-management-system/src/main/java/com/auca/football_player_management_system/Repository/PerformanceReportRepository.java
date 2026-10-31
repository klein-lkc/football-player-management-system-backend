package com.auca.football_player_management_system.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.football_player_management_system.Domain.PerformanceReport;

public interface PerformanceReportRepository extends JpaRepository<PerformanceReport, UUID>{
    // check previous report
    boolean existsByContractId(UUID contractId);

    // history report of a player
    @Query ("SELECT r FROM PerformanceReport r WHERE r.contract.player.licenceNumber = :licenceNumber ORDER BY r.reportDate DESC")
    List<PerformanceReport> findReportsByPlayerLicenceNumber(@Param("licenceNumber") String licenceNumber);
}
