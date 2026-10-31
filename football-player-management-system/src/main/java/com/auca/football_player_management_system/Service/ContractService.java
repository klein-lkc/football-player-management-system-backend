package com.auca.football_player_management_system.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Contract;
import com.auca.football_player_management_system.Domain.ContractStatus;
import com.auca.football_player_management_system.Repository.ContractRepository;

@Service 
public class ContractService {
 
    private final ContractRepository contractRepository;
    public ContractService(ContractRepository contractRepository){
        this.contractRepository=contractRepository;
    }

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
    public void deleteContract(UUID ID){
        contractRepository.deleteById(ID);
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
    


}
