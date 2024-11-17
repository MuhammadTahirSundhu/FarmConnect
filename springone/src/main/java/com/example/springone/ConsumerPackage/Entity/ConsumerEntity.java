package com.example.springone.ConsumerPackage.Entity;

import jakarta.persistence.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "consumers")
public class ConsumerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consumerID;

    private String name;

    private String location;

    @Temporal(TemporalType.DATE)
    private Date registeredDate;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Default constructor
    public ConsumerEntity() {}

    // Constructor with parameters
    public ConsumerEntity(int consumerID, String name, String location, Date registeredDate, String email, String password) {
        this.consumerID = consumerID;
        this.name = name;
        this.location = location;
        this.registeredDate = registeredDate;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getConsumerID() { return consumerID; }
    public void setConsumerID(int consumerID) { this.consumerID = consumerID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Date getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(Date registeredDate) { this.registeredDate = registeredDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
