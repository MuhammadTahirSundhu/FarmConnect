package com.example.springone.FeedbackPackage.Service;


import com.example.springone.FeedbackPackage.Model.Feedback;

import java.util.List;

public interface FeedbackService {

    Feedback insertFeedback(Feedback feedback);

    void deleteFeedback(int id);

    void updateFeedback(int id, Feedback feedback);

    Feedback getFeedbackById(int id);

    List<Feedback> getAllFeedback();

//    List<Feedback> getFeedbackByConsumerId(int consumerId);

//    List<Feedback> getFeedbackByFarmerId(int farmerId);
}
