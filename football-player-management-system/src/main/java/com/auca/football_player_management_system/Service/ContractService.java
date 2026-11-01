package com.auca.football_player_management_system.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Contract;
import com.auca.football_player_management_system.Domain.ContractStatus;
import com.auca.football_player_management_system.Repository.ContractRepository;
import com.auca.football_player_management_system.Repository.PerformanceReportRepository;

import jakarta.transaction.Transactional;

@Service 
public class ContractService {
 
    private final ContractRepository contractRepository;
    public ContractService(ContractRepository contractRepository){
        this.contractRepository=contractRepository;
    }

    @Autowired 
    private PerformanceReportRepository performanceReportRepository;



     // save a Contract
    public String saveContract(Contract contract){
        // check date
        if (contract.getSigningDate() != null && contract.getSigningDate().isBefore(LocalDate.now())) {
          return "Contract date cannot be in the past";  
        }

        // check contract with club date in some periode
        if(contract.getClub() != null && contract.getSigningDate() != null){
            boolean exists = contractRepository.existsByClubIdAndSigningDateAndStatusNot(
               contract.getClub().getId() ,contract.getSigningDate(), ContractStatus.TERMINATED);
               if (exists) {
                return "Club already signs a contract on that date";
               }
        }

        // a new contract with offered
        contract.setStatus(ContractStatus.OFFERED);
        contractRepository.save(contract);
        return "Contract saved successfully";
    }

    //Get all Contract
    public List<Contract> getAllContracts(){
        return contractRepository.findAll();
    }
    // Get Contract by UUID
    public Optional<Contract> getContractId(UUID id){
        return contractRepository.findById(id);
    }
    //delete a Contract by UUID 
    public String deleteContract(UUID ID){
        if (!contractRepository.existsById(ID)) {
            return "The contract with that id does not exist";
        }

        // verifie if it exist a rapport for a contract
        if(performanceReportRepository.existsByContractId(ID)){
            return "That contract has performance reports, delete them first";
        }
        contractRepository.deleteById(ID);
        return "Contract deleted successfully";
    }
    // check if by ID if Contract exists
    public boolean existsById(UUID id){
        return contractRepository.existsById(id);
    }

    // get contract status 
    public List<Contract> getContractsBYStatus(ContractStatus status){
        return contractRepository.findByStatusOrderBySigningDateAsc(status);
    }

    // get conract between date
    public List<Contract> getContractsBetweenDates(LocalDate start, LocalDate endDate){
        return contractRepository.findBySigningDateBetweenOrderBySigningDateAsc(start, endDate);
    }

    // update a contract 
    public String updateContractStatus(UUID id , ContractStatus newStatus){
    Optional<Contract> contractOpt = contractRepository.findById(id);
    if (!contractOpt.isPresent()) {
        return "NOT_FOUND";
    }
    Contract contract = contractOpt.get();
    ContractStatus currentStatus = contract.getStatus();
    boolean isValidTransition = false;

    // validation 
    if (currentStatus == ContractStatus.OFFERED) {
        if (newStatus == ContractStatus.NEGOTIATING || newStatus == ContractStatus.TERMINATED) {
            isValidTransition = true;
        }
    } else if (currentStatus == ContractStatus.NEGOTIATING) {
        if (newStatus == ContractStatus.SIGNED || newStatus == ContractStatus.TERMINATED) {
            isValidTransition = true;
        }
    }

    if (!isValidTransition) {
        return "Cannot change status from " + currentStatus + " to " + newStatus;
    }
    contract.setStatus(newStatus);
    contractRepository.save(contract);
    return "Contract status updated successfully";

    }
    
    // get contract count by status
    public List<Object[]> getContractsStatsByStatus(){
        return contractRepository.getContractsCountBystatus();
    }

    // cancellation of contracts on a specific date
    @Transactional
    public String cancelContractsDay(UUID clubId, LocalDate date){
        int updatedCount = contractRepository.cancelContractsForDay(clubId, date);
        return updatedCount + " contracts have been cancelled for the specified date.";
    }

    // fonction to update contract 
    public String updateContract(UUID id, Contract updatedContract){
        Optional<Contract> existingContractOpt = contractRepository.findById(id);
        if(!existingContractOpt.isPresent()){
            return "The contract with that id does not exist";
        }
        Contract existingContract = existingContractOpt.get();
        updatedContract.setId(id);
        updatedContract.setStatus(existingContract.getStatus());

        contractRepository.save(updatedContract);
        return "Contract updated successfully";
    }


}
