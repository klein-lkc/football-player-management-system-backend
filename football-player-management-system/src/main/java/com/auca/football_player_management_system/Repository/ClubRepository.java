package com.auca.football_player_management_system.Repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.football_player_management_system.Domain.Club;

public interface ClubRepository extends JpaRepository<Club, UUID> {
    // check club by competition
    @Query("SELECT  DISTINCT c FROM Club c JOIN c.competitions comp WHERE LOWER(comp.name) LIKE LOWER(CONCAT('%' , :name, '%'))")
    List<Club> findClubsByCompetitonName(@Param("name") String name);
}
