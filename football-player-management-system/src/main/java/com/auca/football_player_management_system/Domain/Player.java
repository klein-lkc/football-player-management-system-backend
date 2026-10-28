package com.auca.football_player_management_system.Domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "player")
public class Player {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String licenceNumber;
    private String position;
    private String preferredFoot;


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getlicenceNumber() {
        return licenceNumber;
    }
    public void setlicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPreferredFoot() {
        return preferredFoot;
    }
    public void setPreferredFoot(String preferredFoot) {
        this.preferredFoot = preferredFoot;
    }

}
