package com.auca.football_player_management_system.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.PerformanceReport;
import com.auca.football_player_management_system.Repository.ContractRepository;
import com.auca.football_player_management_system.Repository.PerformanceReportRepository;
import com.auca.football_player_management_system.Domain.Contract;
import com.auca.football_player_management_system.Domain.ContractStatus;


@Service 
public class PerformanceReportService {

    private final PerformanceReportRepository performanceReportRepository;
    public PerformanceReportService(PerformanceReportRepository performanceReportRepository){
        this.performanceReportRepository=performanceReportRepository;
    }

    @Autowired 
    private ContractRepository contractRepository;

    public String saveReport(UUID contractId, PerformanceReport report){
        Optional<Contract> contractOpt = contractRepository.findById(contractId);
        if(!contractOpt.isPresent()){
            return "The contract with that id does exist";
        }
        Contract contract = contractOpt.get();

        // check only signed contracts
        if(contract.getStatus() != ContractStatus.SIGNED ){
            return "Performance reports are only for signed contracts";
        }
        report.setReportdate(LocalDate.now());
        report.setContract(contract);
        performanceReportRepository.save(report);

        return "Performance report saved successfully";
    }

    // get all report by player licence
    public List<PerformanceReport> getReportsByPlayerLicence(String licenceNumber){
        return performanceReportRepository.findReportsByPlayerLicenceNumber(licenceNumber);
    }

    // delete Report
    public String deleteReport(UUID id){
        if(!performanceReportRepository.existsById(id)){
            return "The performance report with that id does not exist";
        }
    performanceReportRepository.deleteById(id);
    return "Performance report deleted successfully";
    }


}
