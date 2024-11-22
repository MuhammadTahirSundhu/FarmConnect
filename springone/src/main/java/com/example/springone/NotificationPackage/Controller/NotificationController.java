package com.example.springone.NotificationPackage.Controller;

import com.example.springone.DTO.NotificationRequest;
import com.example.springone.NotificationPackage.Model.Notification;
import com.example.springone.NotificationPackage.Service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        try {
            Notification savedNotification = notificationService.createNotification(notification);
            return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating notification", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") int id) {
        try {
            notificationService.deleteNotification(id);
            return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting notification with ID: {}", id, e);
            return new ResponseEntity<>("Notification not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNotification(@PathVariable("id") int id, @RequestBody Notification notification) {
        try {
            notificationService.updateNotification(id, notification);
            return new ResponseEntity<>("Notification updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating notification with ID: {}", id, e);
            return new ResponseEntity<>("Error updating notification with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable("id") int id) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            if (notification == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching notification with ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        try {
            List<Notification> notifications = notificationService.getAllNotifications();
            if (notifications == null || notifications.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all notifications", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/send/farmer")
    public ResponseEntity<Notification> sendNotificationToFarmer(@RequestBody NotificationRequest request) {
        try {
            Notification savedNotification = notificationService.sendNotificationToFarmer(request.getNotificationId(), request.getFarmerId());
            return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating notification for farmer ID: {}", request.getFarmerId(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send/consumer")
    public ResponseEntity<Notification> sendNotificationToConsumer(@RequestBody NotificationRequest request) {
        try {
            Notification savedNotification = notificationService.sendNotificationToConsumer(request.getNotificationId(), request.getConsumerId());
            return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating notification for consumer ID: {}", request.getConsumerId(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
