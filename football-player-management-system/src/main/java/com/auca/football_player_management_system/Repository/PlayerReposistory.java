package com.auca.football_player_management_system.Repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.football_player_management_system.Domain.Player;

public interface PlayerReposistory extends JpaRepository<Player, UUID> {
    List<Player> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
    @Query("SELECT DISTINCT c.player FROM Contract c WHERE c.club.id = :clubId")
    List<Player> findPlayersByClubId(@Param ("clubId") UUID cludId); 
}
