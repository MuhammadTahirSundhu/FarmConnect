package com.example.springone.FeedbackPackage.Model;

import java.time.LocalDate;

public class Feedback {

    private int feedbackID;
    private int rating;
    private String comment;
    private LocalDate date;
    private int farmerID;  // Farmer's ID (instead of the whole Farmer object)
    private int consumerID; // Consumer's ID (instead of the whole Consumer object)

    // Default constructor
    public Feedback() {
        this.date = LocalDate.now(); // Set default date to current date
    }

    // Constructor with parameters
    public Feedback(int feedbackID, int rating, String comment, LocalDate date, int farmerID, int consumerID) {
        this.feedbackID = feedbackID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.farmerID = farmerID;
        this.consumerID = consumerID;
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

    public int getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(int farmerID) {
        this.farmerID = farmerID;
    }

    public int getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(int consumerID) {
        this.consumerID = consumerID;
    }
}
