package com.auca.football_player_management_system.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.auca.football_player_management_system.Domain.Contract;
import com.auca.football_player_management_system.Domain.ContractStatus;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
    List<Contract> findByStatusOrderBySigningDateAsc(ContractStatus status);
    List<Contract> findBySigningDateBetweenOrderBySigningDateAsc(LocalDate startDate, LocalDate endDate);
}
