package com.auca.football_player_management_system.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.auca.football_player_management_system.Domain.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    
}
