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

import com.auca.football_player_management_system.Domain.Stadium;
import com.auca.football_player_management_system.Service.StadiumService;

@RestController 
@RequestMapping("api/stadiums")
public class StadiumController {
    private final StadiumService stadiumService;
    public StadiumController(StadiumService stadiumService){
        this.stadiumService=stadiumService;
    }

    //1. save post Stadium 
    @PostMapping("/save")
    public ResponseEntity<String> saveStadium(@RequestBody Stadium stadium){
        stadiumService.saveStadium(stadium);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stadium saved successsfully");
    }

    // 2. get all Stadium
    @GetMapping("/all")
    public ResponseEntity<List<Stadium>> getAllStadiums(){
        List<Stadium> Stadiums = stadiumService.getAllStadiums();
        return ResponseEntity.status(HttpStatus.OK).body(Stadiums);
    }

    // 3. get by Stadium by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getStadiumById(@PathVariable UUID id){
        Optional<Stadium> StadiumOpt = stadiumService.getStadiumId(id);
        if(StadiumOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(StadiumOpt.get());    
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The stadium with that id does not exist");
        }
    }

    // 4. update Stadium 
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStadium(@PathVariable UUID id, @RequestBody Stadium updatedStadium){
        if(!stadiumService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The stadium with that id does not exist");
        }
        else{
            updatedStadium.setId(id);
            stadiumService.saveStadium(updatedStadium);
            return ResponseEntity.status(HttpStatus.OK).body("Stadium is updated");
        }
    }

    // 5 .Delete a Stadium
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteStadium(@PathVariable UUID id){
        if(!stadiumService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The stadium with that id does not exist");
        }
        stadiumService.deleteStadium(id);
        return ResponseEntity.status(HttpStatus.OK).body("Stadium is deleted");
    }

    // free stadium
    @GetMapping("/free")
    public ResponseEntity<List<Stadium>> getFreeStadiums(){
        List<Stadium> stadiums =stadiumService.getFreeStadiums();
        return ResponseEntity.status(HttpStatus.OK).body(stadiums);
    }
}
