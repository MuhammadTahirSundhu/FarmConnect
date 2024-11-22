package com.example.springone.FeedbackPackage.Controller;

import com.example.springone.FeedbackPackage.Model.Feedback;
import com.example.springone.FeedbackPackage.Service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Insert new feedback
    @PostMapping
    public ResponseEntity<Feedback> insertFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.insertFeedback(feedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete feedback by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable("id") int id) {
        try {
            feedbackService.deleteFeedback(id);
            return new ResponseEntity<>("Feedback deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Feedback not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // Update feedback by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFeedback(@PathVariable("id") int id, @RequestBody Feedback feedback) {
        try {
            feedbackService.updateFeedback(id, feedback);
            return new ResponseEntity<>("Feedback updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating feedback with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") int id) {
        try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            if (feedback == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all feedback
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        try {
            List<Feedback> feedbackList = feedbackService.getAllFeedback();
            if (feedbackList == null || feedbackList.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get feedback by Consumer ID
//    @GetMapping("/consumer/{consumerId}")
//    public ResponseEntity<List<Feedback>> getFeedbackByConsumerId(@PathVariable("consumerId") int consumerId) {
//        try {
//            List<Feedback> feedbackList = feedbackService.getFeedbackByConsumerId(consumerId);
//            if (feedbackList == null || feedbackList.isEmpty()) {
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // Get feedback by Farmer ID
//    @GetMapping("/farmer/{farmerId}")
//    public ResponseEntity<List<Feedback>> getFeedbackByFarmerId(@PathVariable("farmerId") int farmerId) {
//        try {
//            List<Feedback> feedbackList = feedbackService.getFeedbackByFarmerId(farmerId);
//            if (feedbackList == null || feedbackList.isEmpty()) {
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
