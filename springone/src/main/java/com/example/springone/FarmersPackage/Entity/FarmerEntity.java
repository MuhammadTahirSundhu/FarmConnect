package com.example.springone.FarmersPackage.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "farmer")
public class FarmerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int farmerid;

    private String name;
    private String farmlocation;

    // Store cropTypes as a simple List of Strings, not overcomplicating it
    @ElementCollection
    @CollectionTable(name = "farmer_croptypes", joinColumns = @JoinColumn(name = "farmerid"))
    @Column(name = "croptype")
    private List<String> croptypes;

    @Temporal(TemporalType.DATE)
    private Date registereddate;

    private Boolean availabilitystatus = true;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Default constructor
    public FarmerEntity() {
        Date date = new Date();
    }

    // Constructor with parameters
    public FarmerEntity(int farmerid, String name, String farmlocation, List<String> croptypes, Date registereddate, Boolean availabilitystatus, String email, String password) {
        Date date = new Date();
        this.farmerid = farmerid;
        this.name = name;
        this.farmlocation = farmlocation;
        this.croptypes = croptypes;
        this.registereddate = date;
        this.availabilitystatus = availabilitystatus;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getFarmerid() { return farmerid; }
    public void setFarmerid(int farmerid) { this.farmerid = farmerid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFarmlocation() { return farmlocation; }
    public void setFarmlocation(String farmlocation) { this.farmlocation = farmlocation; }

    public List<String> getCroptypes() { return croptypes; }
    public void setCroptypes(List<String> croptypes) { this.croptypes = croptypes; }

    public Date getRegistereddate() { return registereddate; }
    public void setRegistereddate(Date registereddate) { this.registereddate = registereddate; }

    public Boolean getAvailabilitystatus() { return availabilitystatus; }
    public void setAvailabilitystatus(Boolean availabilitystatus) { this.availabilitystatus = availabilitystatus; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}