package com.auca.football_player_management_system.Repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auca.football_player_management_system.Domain.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    @Query("SELECT comp FROM Competition comp WHERE comp WHERE comp.clubs IS EMPTY")
    List<Competition> findUnusedCompetition();
}
