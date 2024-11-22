package com.example.springone.FeedbackPackage.Entity;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackID;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "farmerID", nullable = false)
    private FarmerEntity farmerEntity;

    @ManyToOne
    @JoinColumn(name = "consumerID", nullable = false)
    private ConsumerEntity consumerEntity;

    // Default constructor
    public FeedbackEntity() {
        // No initialization required
    }

    // Constructor with parameters
    public FeedbackEntity(int feedbackID, int rating, String comment, LocalDate date, FarmerEntity farmer, ConsumerEntity consumer) {
        this.feedbackID = feedbackID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.farmerEntity = farmer;
        this.consumerEntity = consumer;
    }

    // Getters and Setters
    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public FarmerEntity getFarmer() {
        return farmerEntity;
    }

    public void setFarmer(FarmerEntity farmer) {
        this.farmerEntity = farmer;
    }

    public ConsumerEntity getConsumer() {
        return consumerEntity;
    }

    public void setConsumer(ConsumerEntity consumer) {
        this.consumerEntity = consumer;
    }
}
