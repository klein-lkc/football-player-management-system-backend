package com.auca.football_player_management_system.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auca.football_player_management_system.Domain.Player;
import com.auca.football_player_management_system.Repository.PlayerReposistory;

@Service 
public class PlayerService {
    
    
    private final PlayerReposistory playerReposistory;
        public PlayerService(PlayerReposistory playerReposistory){
            this.playerReposistory=playerReposistory;
        }

    // save and update player
    public Player savePlayer(Player player){
        return playerReposistory.save(player);
    }

    //Get all player
    public List<Player> getAllPlayers(){
        return playerReposistory.findAll();
    }
    // Get player by UUID
    public Optional<Player> getPlayerId(UUID id){
        return playerReposistory.findById(id);
    }
    //delete a player by UUID 
    public void deletePlayer(UUID ID){
        playerReposistory.deleteById(ID);
    }
    // check if by ID if player exists
    public boolean existsById(UUID id){
        return playerReposistory.existsById(id);
    }

    // get Players By lastName
    public List<Player> getPlayerByLastName(String lastName){
        return playerReposistory.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
    
}
