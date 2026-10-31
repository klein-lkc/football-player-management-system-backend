package com.auca.football_player_management_system.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Contract;
import com.auca.football_player_management_system.Repository.ContractRepository;

@Service 
public class ContractService {
 
    private final ContractRepository contractRepository;
    public ContractService(ContractRepository contractRepository){
        this.contractRepository=contractRepository;
    }

     // save and update Contract
    public Contract saveContract(Contract contract){
        return contractRepository.save(contract);
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
}
