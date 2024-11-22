package com.example.springone.FeedbackPackage.Service;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import com.example.springone.ConsumerPackage.Model.Consumer;
import com.example.springone.ConsumerPackage.Repositry.ConsumerRepository;
import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import com.example.springone.FarmersPackage.Model.Farmer;
import com.example.springone.FarmersPackage.Repositry.FarmerRepository;
import com.example.springone.FeedbackPackage.Entity.FeedbackEntity;
import com.example.springone.FeedbackPackage.Model.Feedback;
import com.example.springone.FeedbackPackage.Repositry.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final FarmerRepository farmerRepo;
    private final ConsumerRepository consumerRepo;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepo, FarmerRepository farmerRepo, ConsumerRepository consumerRepo) {
        this.feedbackRepo = feedbackRepo;
        this.farmerRepo = farmerRepo;
        this.consumerRepo = consumerRepo;
    }

    // Insert new Feedback
    @Override
    public Feedback insertFeedback(Feedback feedback) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        BeanUtils.copyProperties(feedback, feedbackEntity); // Copy properties from Feedback model to FeedbackEntity

        // Handling associations (Farmer and Consumer)
        Optional<FarmerEntity> farmerEntity = farmerRepo.findById(feedback.getFarmerID());
        Optional<ConsumerEntity> consumerEntity = consumerRepo.findById(feedback.getConsumerID());

        if (farmerEntity.isPresent()) {
            feedbackEntity.setFarmer(farmerEntity.get());  // Set farmer from repository
        } else {
            throw new RuntimeException("Farmer not found");
        }

        if (consumerEntity.isPresent()) {
            feedbackEntity.setConsumer(consumerEntity.get());  // Set consumer from repository
        } else {
            throw new RuntimeException("Consumer not found");
        }

        feedbackRepo.save(feedbackEntity); // Save to DB

        // Manually set the IDs for the returned Feedback object
        feedback.setFarmerID(feedbackEntity.getFarmer().getFarmerid());
        feedback.setConsumerID(feedbackEntity.getConsumer().getConsumerID());

        return feedback; // Return the inserted Feedback model object
    }

    // Delete Feedback by ID
    @Override
    public void deleteFeedback(int feedbackID) {
        Optional<FeedbackEntity> feedbackEntityOptional = feedbackRepo.findById(feedbackID);
        if (feedbackEntityOptional.isPresent()) {
            feedbackRepo.delete(feedbackEntityOptional.get());
            log.info("Feedback with ID {} deleted successfully.", feedbackID);
        } else {
            log.error("Feedback with ID {} not found for deletion.", feedbackID);
            throw new RuntimeException("Feedback with ID " + feedbackID + " not found.");
        }
    }

    // Update Feedback by ID
    @Override
    public void updateFeedback(int feedbackID, Feedback feedback) {
        try {
            Optional<FeedbackEntity> optionalFeedbackEntity = feedbackRepo.findById(feedbackID);
            if (optionalFeedbackEntity.isPresent()) {
                FeedbackEntity feedbackEntity = optionalFeedbackEntity.get();
                feedbackEntity.setRating(feedback.getRating());
                feedbackEntity.setComment(feedback.getComment());
                feedbackEntity.setDate(feedback.getDate());

                // Update associations (Farmer and Consumer)
                Optional<FarmerEntity> farmer = farmerRepo.findById(feedback.getFarmerID());
                Optional<ConsumerEntity> consumer = consumerRepo.findById(feedback.getConsumerID());

                if (farmer.isPresent()) {
                    feedbackEntity.setFarmer(farmer.get()); // Set updated farmer
                } else {
                    throw new RuntimeException("Farmer not found");
                }

                if (consumer.isPresent()) {
                    feedbackEntity.setConsumer(consumer.get()); // Set updated consumer
                } else {
                    throw new RuntimeException("Consumer not found");
                }

                feedbackRepo.save(feedbackEntity); // Save the updated feedback entity

                // Manually set the IDs for the returned Feedback object
                feedback.setFarmerID(feedbackEntity.getFarmer().getFarmerid());
                feedback.setConsumerID(feedbackEntity.getConsumer().getConsumerID());

                log.info("Feedback with ID {} updated successfully.", feedbackID);
            } else {
                throw new RuntimeException("Feedback with ID " + feedbackID + " not found.");
            }
        } catch (Exception e) {
            log.error("Error updating feedback with ID {}: {}", feedbackID, e.getMessage());
            throw e;
        }
    }

    // Get Feedback by ID
    @Override
    public Feedback getFeedbackById(int feedbackID) {
        Optional<FeedbackEntity> feedbackEntityOptional = feedbackRepo.findById(feedbackID);
        if (feedbackEntityOptional.isPresent()) {
            FeedbackEntity feedbackEntity = feedbackEntityOptional.get();
            Feedback feedback = new Feedback();
            BeanUtils.copyProperties(feedbackEntity, feedback); // Copy properties from entity to model

            // Manually set the IDs for the returned Feedback object
            feedback.setFarmerID(feedbackEntity.getFarmer().getFarmerid());
            feedback.setConsumerID(feedbackEntity.getConsumer().getConsumerID());

            return feedback;
        } else {
            log.error("Feedback with ID {} not found.", feedbackID);
            throw new RuntimeException("Feedback with ID " + feedbackID + " not found.");
        }
    }

    // Get All Feedback
    @Override
    public List<Feedback> getAllFeedback() {
        List<FeedbackEntity> feedbackEntities = feedbackRepo.findAll();

        return feedbackEntities.stream()
                .map(feedbackEntity -> {
                    Feedback feedback = new Feedback();
                    BeanUtils.copyProperties(feedbackEntity, feedback); // Copy properties from entity to model

                    // Manually set the IDs for each Feedback object
                    feedback.setFarmerID(feedbackEntity.getFarmer().getFarmerid());
                    feedback.setConsumerID(feedbackEntity.getConsumer().getConsumerID());

                    return feedback;
                })
                .collect(Collectors.toList());
    }


    // Get Feedback by Consumer ID
//    @Override
//    public List<Feedback> getFeedbackByConsumerId(int consumerID) {
//        List<FeedbackEntity> feedbackEntities = feedbackRepo.findByConsumerId(consumerID);
//        return feedbackEntities.stream()
//                .map(feedbackEntity -> {
//                    Feedback feedback = new Feedback();
//                    BeanUtils.copyProperties(feedbackEntity, feedback); // Copy properties from entity to model
//                    return feedback;
//                })
//                .collect(Collectors.toList());
//    }

    // Get Feedback by Farmer ID
//    @Override
//    public List<Feedback> getFeedbackByFarmerId(int farmerID) {
//        List<FeedbackEntity> feedbackEntities = feedbackRepo.findByFarmerID(farmerID);
//        return feedbackEntities.stream()
//                .map(feedbackEntity -> {
//                    Feedback feedback = new Feedback();
//                    BeanUtils.copyProperties(feedbackEntity, feedback); // Copy properties from entity to model
//                    return feedback;
//                })
//                .collect(Collectors.toList());
//    }

}
