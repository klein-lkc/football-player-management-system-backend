package com.auca.football_player_management_system.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.football_player_management_system.Domain.Contract;
import com.auca.football_player_management_system.Domain.ContractStatus;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
    List<Contract> findByStatusOrderBySigningDateAsc(ContractStatus status);
    List<Contract> findBySigningDateBetweenOrderBySigningDateAsc(LocalDate startDate, LocalDate endDate);
    // check if the contact with a club in specific date
   boolean existsByClubIdAndSigningDateAndStatusNot(UUID clubId, LocalDate signingDate, ContractStatus status);

   // count the number of contract by status
   @Query("SELECT c.status, COUNT(c) FROM Contract c Group BY c.status")
   List<Object[]> getContractsCountBystatus();

   // cancellation of contracts on a specific date
  @Modifying
    @Query("UPDATE Contract c SET c.status = com.auca.football_player_management_system.Domain.ContractStatus.TERMINATED " +
           "WHERE c.club.id = :clubId AND c.signingDate = :date " +
           "AND c.status <> com.auca.football_player_management_system.Domain.ContractStatus.SIGNED")
    int cancelContractsForDay(@Param("clubId") UUID clubId, @Param("date") LocalDate date);
}
