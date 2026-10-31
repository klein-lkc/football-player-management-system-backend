package com.auca.football_player_management_system.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.football_player_management_system.Domain.Club;
import com.auca.football_player_management_system.Service.ClubService;

@RestController 
@RequestMapping("api/clubs")
public class ClubController {
    
    private final ClubService clubService;
    public ClubController(ClubService clubService){
        this.clubService=clubService;
    }

    //1. save post Club 
    @PostMapping("/save")
    public ResponseEntity<String> saveClub(@RequestBody Club club){
        clubService.saveClub(club);
        return ResponseEntity.status(HttpStatus.CREATED).body("Club saved successfully");
    }

    // 2. get all Club
    @GetMapping("/all")
    public ResponseEntity<List<Club>> getAllClubs(){
        List<Club> Clubs = clubService.getAllClubs();
        return ResponseEntity.status(HttpStatus.OK).body(Clubs);
    }

    // 3. get by Club by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getClubById(@PathVariable UUID id){
        Optional<Club> ClubOpt = clubService.getClubId(id);
        if(ClubOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(ClubOpt.get());    
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The club with that id does not exist");
        }
    }

    // 4. update Club 
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClub(@PathVariable UUID id, @RequestBody Club updatedClub){
        if(!clubService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The club with that id does not exist");
        }
        else{
            updatedClub.setId(id);
            clubService.saveClub(updatedClub);
            return ResponseEntity.status(HttpStatus.OK).body("Club is updated");
        }
    }

    // 5 .Delete a Club
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable UUID id){
        if(!clubService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The club with that id does not exist");
        }
        clubService.deleteClub(id);
        return ResponseEntity.status(HttpStatus.OK).body("Club is deleted");
    }

    // get club by competition
    @GetMapping("/by-competition")
    public ResponseEntity<List<Club>> getClubsByCompetition(@RequestParam String name){
        List<Club> clubs = clubService.getClubsByCompetiotionName(name);
        return ResponseEntity.status(HttpStatus.OK).body(clubs);
    }

}
