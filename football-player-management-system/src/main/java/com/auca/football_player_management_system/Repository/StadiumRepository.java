package com.auca.football_player_management_system.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.auca.football_player_management_system.Domain.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium, UUID> {
    
}
