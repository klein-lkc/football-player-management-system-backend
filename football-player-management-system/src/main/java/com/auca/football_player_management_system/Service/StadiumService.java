package com.auca.football_player_management_system.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Stadium;
import com.auca.football_player_management_system.Repository.StadiumRepository;

@Service 
public class StadiumService {
    
    private final StadiumRepository stadiumRepository;
    public StadiumService(StadiumRepository stadiumRepository){
        this.stadiumRepository=stadiumRepository;
    }

     // save and update Stadium
    public Stadium saveStadium(Stadium stadium){
        return stadiumRepository.save(stadium);
    }

    //Get all Stadium
    public List<Stadium> getAllStadiums(){
        return stadiumRepository.findAll();
    }
    // Get Stadium by UUID
    public Optional<Stadium> getStadiumId(UUID id){
        return stadiumRepository.findById(id);
    }
    //delete a Stadium by UUID 
    public void deleteStadium(UUID ID){
        stadiumRepository.deleteById(ID);
    }
    // check if by ID if Stadium exists
    public boolean existsById(UUID id){
        return stadiumRepository.existsById(id);
    }
}
