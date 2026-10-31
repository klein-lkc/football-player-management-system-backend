package com.auca.football_player_management_system.Repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auca.football_player_management_system.Domain.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium, UUID> {

  @Query("SELECT s FROM Stadium s WHERE NOT EXISTS (SELECT c FROM Club c WHERE c.stadium = s) ORDER BY s.stadiumNumber ASC")
    List<Stadium> findFreeStadiums();
}
