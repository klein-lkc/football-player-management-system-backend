package com.auca.football_player_management_system.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Club;
import com.auca.football_player_management_system.Domain.Competition;
import com.auca.football_player_management_system.Domain.Stadium;
import com.auca.football_player_management_system.Repository.ClubRepository;
import com.auca.football_player_management_system.Repository.CompetitionRepository;
import com.auca.football_player_management_system.Repository.StadiumRepository;


@Service 
public class ClubService {
    
    public final ClubRepository clubRepository;
    public ClubService(ClubRepository clubRepository){
        this.clubRepository=clubRepository;
    }
    @Autowired 
    private CompetitionRepository competitionRepository;
    @Autowired 
    private StadiumRepository stadiumRepository;

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

    //  get club by competition
    public  List<Club> getClubsByCompetiotionName(String name){
        return clubRepository.findClubsByCompetitonName(name);
    }

    // add competion to club
   public String addCompetitionToClub(UUID clubId, UUID competitionId) {
    Optional<Club> clubOpt = clubRepository.findById(clubId);
    Optional<Competition> compOpt = competitionRepository.findById(competitionId);

    if (!clubOpt.isPresent()) {
        return "The club with that id does not exist";
    }
    if (!compOpt.isPresent()) {
        return "That competition does not exist";
    }

    Club club = clubOpt.get();
    Competition competition = compOpt.get();
    
    // check if a club already has a competition 
    if (club.getCompetitions().contains(competition)) {
        return "The club already has that competition";
    }

    // add competition
    club.getCompetitions().add(competition);
    clubRepository.save(club);

    return "Competition added to the club";

    }

    // move a club to a stadium 
    public String moveClubToStadium(UUID clubId, int stadiumNumber) {
    Optional<Club> clubOpt = clubRepository.findById(clubId);
    if (!clubOpt.isPresent()) {
        return "The club with that id does not exist";
    }

    Optional<Stadium> stadiumOpt = stadiumRepository.findByStadiumNumber(stadiumNumber);
    if (!stadiumOpt.isPresent()) {
        return "The stadium with that stadiumNumber does not exist";
    }

    Stadium newStadium = stadiumOpt.get();

    // check if stadium is already taken
    Club existingClubWithStadium = clubRepository.findByStadiumStadiumNumber(stadiumNumber);
    if (existingClubWithStadium != null && !existingClubWithStadium.getId().equals(clubId)) {
        return "That stadium is taken by another club";
    }

    Club club = clubOpt.get();
    club.setStadium(newStadium);
    clubRepository.save(club);

    return "Stadium changed successfully";

    }
}
