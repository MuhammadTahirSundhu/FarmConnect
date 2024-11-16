package com.example.springone.Model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.List;

public class Farmer {
    private int farmerid;
    private String name;
    private String farmlocation;
    private List<String> croptypes;
    private Date registereddate;
    private Boolean availabilitystatus = true;
    private String email;
    private String password;

    public Farmer() {
    }

    public Farmer(int farmerid, String name, String farmlocation, List<String> croptypes, Date registereddate, Boolean availabilitystatus, String email, String password) {
        this.farmerid = farmerid;
        this.name = name;
        this.farmlocation = farmlocation;
        this.croptypes = croptypes;
        this.registereddate = registereddate;
        this.availabilitystatus = availabilitystatus;
        this.email = email;
        this.password = password;
    }

    public int getFarmerid() {
        return farmerid;
    }

    public void setFarmerid(int farmerid) {
        this.farmerid = farmerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarmlocation() {
        return farmlocation;
    }

    public void setFarmlocation(String farmlocation) {
        this.farmlocation = farmlocation;
    }

    public List<String> getCroptypes() {
        return croptypes;
    }

    public void setCroptypes(List<String> croptypes) {
        this.croptypes = croptypes;
    }

    public Date getRegistereddate() {
        return registereddate;
    }

    public void setRegistereddate(Date registereddate) {
        this.registereddate = registereddate;
    }

    public Boolean getAvailabilitystatus() {
        return availabilitystatus;
    }

    public void setAvailabilitystatus(Boolean availabilitystatus) {
        this.availabilitystatus = availabilitystatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
