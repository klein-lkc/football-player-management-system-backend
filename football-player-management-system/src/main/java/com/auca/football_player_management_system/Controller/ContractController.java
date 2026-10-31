package com.auca.football_player_management_system.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.football_player_management_system.Domain.Contract;
import com.auca.football_player_management_system.Domain.ContractStatus;
import com.auca.football_player_management_system.Service.ContractService;

@RestController 
@RequestMapping("api/contracts")
public class ContractController {
    private final ContractService contractService;
    public ContractController(ContractService contractService){
        this.contractService=contractService;
    }

    //1. save post Contract 
    @PostMapping("/save")
    public ResponseEntity<String> saveContract(@RequestBody Contract contract){
      String result = contractService.saveContract(contract);
      if(result.equals("Contract date cannot be in the past")){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
      }
      if(result.equals("Club already signs a contract on that date")){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
      }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 2. get all Contract
    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContracts(){
        List<Contract> Contracts = contractService.getAllContracts();
        return ResponseEntity.status(HttpStatus.OK).body(Contracts);
    }

    // 3. get by Contract by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getContractById(@PathVariable UUID id){
        Optional<Contract> ContractOpt = contractService.getContractId(id);
        if(ContractOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(ContractOpt.get());    
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The contract with that id does not exist");
        }
    }

    // 4. update Contract 
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateContract(@PathVariable UUID id, @RequestBody Contract updatedContract){
        if(!contractService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The contract with that id does not exist");
        }
        else{
            updatedContract.setId(id);
            contractService.saveContract(updatedContract);
            return ResponseEntity.status(HttpStatus.OK).body("Contract is updated");
        }
    }

    // 5 .Delete a Contract
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteContract(@PathVariable UUID id){
        if(!contractService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The contract with that id does not exist");
        }
        contractService.deleteContract(id);
        return ResponseEntity.status(HttpStatus.OK).body("Contract is deleted");
    }

    // 6. get contract by  status
    @GetMapping("/by-status")
    public ResponseEntity<List<Contract>> getContractsByStatus(@RequestParam ContractStatus status){
        List<Contract> contracts = contractService.getContractsBYStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(contracts);
    }

    // get contract by start date and end date
    @GetMapping("/between")
    public ResponseEntity<List<Contract>> getContractsBetween(
        @RequestParam String start,
        @RequestParam String end){
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            List<Contract> contracts = contractService.getContractsBetweenDates(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(contracts);
        }

    // update contract status
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateContractStatus(@PathVariable UUID id, @RequestParam ContractStatus status) {
    String result = contractService.updateContractStatus(id, status);

    if (result.equals("NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The contract with that id does not exist");
    }
    if (result.startsWith("Cannot change status")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    
}
