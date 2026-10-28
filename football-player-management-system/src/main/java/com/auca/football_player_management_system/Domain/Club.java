package com.auca.football_player_management_system.Domain;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "club")
public class Club {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;
    @Column(name = "club_name", nullable = false)
    private String clubName;
    @Column(name = "manager_name", nullable = false)
    private String managerName;
    @Column(name = "city", nullable = false)
    private String city;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getClubName() {
        return clubName;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    // getters and setters for competitions
    public List<Competition> getCompetitions() {
        return competitions;
    }
    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    //getter and setter for stadium
    public Stadium getStadium() {
        return stadium;
    }
    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    // relation with competition
    @ManyToMany
    @JoinTable(name = "club_competition",
            joinColumns = @JoinColumn (name = "club_id"),
            inverseJoinColumns = @JoinColumn (name = "competition_id")
        )
    private List<Competition> competitions;

    // relation with stadium 
    @ManyToOne 
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;
}