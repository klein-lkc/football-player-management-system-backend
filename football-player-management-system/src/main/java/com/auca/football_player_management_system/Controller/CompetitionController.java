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
import org.springframework.web.bind.annotation.RestController;

import com.auca.football_player_management_system.Domain.Competition;
import com.auca.football_player_management_system.Service.CompetitionService;

@RestController 
@RequestMapping("api/competitions")
public class CompetitionController {
    
    private final CompetitionService competitionService;
    public CompetitionController(CompetitionService competitionService){
        this.competitionService=competitionService;
    }

    //1. save post competition 
    @PostMapping("/save")
    public ResponseEntity<String> saveCompetition(@RequestBody Competition competition){
        competitionService.saveCompetition(competition);
        return ResponseEntity.status(HttpStatus.CREATED).body("Competition saved successsfully");
    }

    // 2. get all Competition
    @GetMapping("/all")
    public ResponseEntity<List<Competition>> getAllCompetitions(){
        List<Competition> Competitions = competitionService.getAllCompetitions();
        return ResponseEntity.status(HttpStatus.OK).body(Competitions);
    }

    // 3. get by Competition by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompetitionById(@PathVariable UUID id){
        Optional<Competition> CompetitionOpt = competitionService.getCompetitionId(id);
        if(CompetitionOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(CompetitionOpt.get());    
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The competition with that id does not exist");
        }
    }

    // 4. update Competition 
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCompetition(@PathVariable UUID id, @RequestBody Competition updatedCompetition){
        if(!competitionService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The competition with that id does not exist");
        }
        else{
            updatedCompetition.setId(id);
            competitionService.saveCompetition(updatedCompetition);
            return ResponseEntity.status(HttpStatus.OK).body("Competition is updated");
        }
    }

    // 5 .Delete a Competition
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteCompetition(@PathVariable UUID id){
        if(!competitionService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The competition with that id does not exist");
        }
        competitionService.deleteCompetition(id);
        return ResponseEntity.status(HttpStatus.OK).body("Competition is deleted");
    }

    // Competitions that no club holds
    @GetMapping("/unused")
    public ResponseEntity<List<Competition>> getUnusedCompetitions(){
        List<Competition> comps = competitionService.getUnusedCompetions();
        return ResponseEntity.status(HttpStatus.OK).body(comps);
    }
}
