package com.example.springone.ConsumerPackage.Model;

import java.util.Date;

public class Consumer {

    private int consumerID;
    private String name;
    private String location;
    private Date registeredDate;
    private String email;
    private String password;

    public Consumer() {
    }

    public Consumer(int consumerID, String name, String location, Date registeredDate, String email, String password) {
        this.consumerID = consumerID;
        this.name = name;
        this.location = location;
        this.registeredDate = registeredDate;
        this.email = email;
        this.password = password;
    }

    public int getConsumerID() {
        return consumerID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setConsumerID(int consumerID) {
        this.consumerID = consumerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
