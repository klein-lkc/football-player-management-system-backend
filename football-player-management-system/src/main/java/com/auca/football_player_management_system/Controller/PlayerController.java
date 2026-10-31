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

import com.auca.football_player_management_system.Domain.Player;
import com.auca.football_player_management_system.Service.PlayerService;

@RestController 
@RequestMapping("api/players")
public class PlayerController {
    
    private final PlayerService playerService;
    public PlayerController(PlayerService playerService){
        this.playerService=playerService;
    }

    //1. save post player 
    @PostMapping("/save")
    public ResponseEntity<String> savePlayer(@RequestBody Player player){
        playerService.savePlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body("Player saved successsfully");
    }

    // 2. get all player
    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAllPlayers(){
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.status(HttpStatus.OK).body(players);
    }

    // 3. get by player by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayerById(@PathVariable UUID id){
        Optional<Player> playerOpt = playerService.getPlayerId(id);
        if(playerOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(playerOpt.get());    
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The player with that id does not exist");
        }
    }

    // 4. update player 
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable UUID id, @RequestBody Player updatedPlayer){
        if(!playerService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The player with that id does not exist");
        }
        else{
            updatedPlayer.setId(id);
            playerService.savePlayer(updatedPlayer);
            return ResponseEntity.status(HttpStatus.OK).body("player is updated");
        }
    }

    // 5 .Delete a player
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable UUID id){
        if(!playerService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The player with that id does not exist");
        }
        playerService.deletePlayer(id);
        return ResponseEntity.status(HttpStatus.OK).body("player is deleted");
    }

    // to find lastName
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Player>> getPlayersByLastName(@RequestParam  String lastName){
        List<Player> players = playerService.getPlayerByLastName(lastName);
        return ResponseEntity.status(HttpStatus.OK).body(players);
    }
}
