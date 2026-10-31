package com.auca.football_player_management_system.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Club;
import com.auca.football_player_management_system.Repository.ClubRepository;


@Service 
public class ClubService {
    
    public final ClubRepository clubRepository;
    public ClubService(ClubRepository clubRepository){
        this.clubRepository=clubRepository;
    }

    // save and update Club
    public Club saveClub(Club club){
        return clubRepository.save(club);
    }

    //Get all Club
    public List<Club> getAllClubs(){
        return clubRepository.findAll();
    }
    // Get Club by UUID
    public Optional<Club> getClubId(UUID id){
        return clubRepository.findById(id);
    }
    //delete a Club by UUID 
    public void deleteClub(UUID ID){
        clubRepository.deleteById(ID);
    }
    // check if by ID if Club exists
    public boolean existsById(UUID id){
        return clubRepository.existsById(id);
    }
    
}
