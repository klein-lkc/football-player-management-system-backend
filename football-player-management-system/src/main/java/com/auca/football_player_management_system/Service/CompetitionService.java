package com.auca.football_player_management_system.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Competition;
import com.auca.football_player_management_system.Repository.CompetitionRepository;

@Service 
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    public CompetitionService(CompetitionRepository competitionRepository){
        this.competitionRepository=competitionRepository;
    }

     // save and update Competition
    public Competition saveCompetition(Competition competition){
        return competitionRepository.save(competition);
    }

    //Get all Competition
    public List<Competition> getAllCompetitions(){
        return competitionRepository.findAll();
    }
    // Get Competition by UUID
    public Optional<Competition> getCompetitionId(UUID id){
        return competitionRepository.findById(id);
    }
    //delete a Competition by UUID 
    public void deleteCompetition(UUID ID){
        competitionRepository.deleteById(ID);
    }
    // check if by ID if Competition exists
    public boolean existsById(UUID id){
        return competitionRepository.existsById(id);
    }

    // Competitions that no club holds

    public List<Competition> getUnusedCompetions(){
        return competitionRepository.findUnusedCompetition();
    }

    // add competition to club
}
