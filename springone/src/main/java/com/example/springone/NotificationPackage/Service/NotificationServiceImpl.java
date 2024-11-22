package com.example.springone.NotificationPackage.Service;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import com.example.springone.ConsumerPackage.Repositry.ConsumerRepository;
import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import com.example.springone.FarmersPackage.Repositry.FarmerRepository;
import com.example.springone.NotificationPackage.Entity.NotificationEntity;
import com.example.springone.NotificationPackage.HelperClasses.EmailService;
import com.example.springone.NotificationPackage.Model.Notification;
import com.example.springone.NotificationPackage.Repositry.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private FarmerRepository farmerRepo;
    private ConsumerRepository consumerRepo;

    @Autowired
    private EmailService emailService;  // Inject the EmailService

    public NotificationServiceImpl(NotificationRepository notificationRepo, FarmerRepository farmerRepo,
                                   ConsumerRepository consumerRepo, EmailService emailService) {
        this.notificationRepo = notificationRepo;
        this.farmerRepo = farmerRepo;
        this.consumerRepo = consumerRepo;
        this.emailService = emailService;
    }

    @Override
    public Notification createNotification(Notification notification) {
        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(notification, notificationEntity);
        NotificationEntity savedEntity = notificationRepo.save(notificationEntity);

        // Manually set the ID in the response model
        notification.setNotificationID(savedEntity.getNotificationID());
        log.info("Notification created successfully!");
        return notification;
    }

    @Override
    public void deleteNotification(int id) {
        Optional<NotificationEntity> optionalNotification = notificationRepo.findById(id);
        if (optionalNotification.isPresent()) {
            notificationRepo.delete(optionalNotification.get());
            log.info("Notification deleted successfully!");
        } else {
            throw new RuntimeException("Notification with ID " + id + " not found.");
        }
    }

    @Override
    public void updateNotification(int id, Notification notification) {
        Optional<NotificationEntity> optionalNotificationEntity = notificationRepo.findById(id);
        if (optionalNotificationEntity.isPresent()) {
            NotificationEntity notificationEntity = optionalNotificationEntity.get();
            BeanUtils.copyProperties(notification, notificationEntity);
            notificationEntity.setNotificationID(id); // Ensure the ID is preserved
            notificationRepo.save(notificationEntity);
            log.info("Notification updated successfully!");
        } else {
            throw new RuntimeException("Notification with ID " + id + " not found.");
        }
    }

    @Override
    public Notification getNotificationById(int id) {
        Optional<NotificationEntity> optionalNotificationEntity = notificationRepo.findById(id);
        if (optionalNotificationEntity.isPresent()) {
            Notification notification = new Notification();
            BeanUtils.copyProperties(optionalNotificationEntity.get(), notification);
            notification.setNotificationID(id); // Manually set the ID
            return notification;
        } else {
            throw new RuntimeException("Notification with ID " + id + " not found.");
        }
    }

    @Override
    public List<Notification> getAllNotifications() {
        List<NotificationEntity> notificationEntities = notificationRepo.findAll();
        return notificationEntities.stream()
                .map(entity -> {
                    Notification notification = new Notification();
                    BeanUtils.copyProperties(entity, notification);
                    notification.setNotificationID(entity.getNotificationID()); // Manually set the ID
                    return notification;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Notification sendNotificationToFarmer(int notificationId, int farmerId) {
        try {
            NotificationEntity notification = notificationRepo.findById(notificationId)
                    .orElseThrow(() -> new RuntimeException("Notification with ID " + notificationId + " not found"));

            FarmerEntity farmer = farmerRepo.findById(farmerId)
                    .orElseThrow(() -> new RuntimeException("Farmer with ID " + farmerId + " not found"));

            notification.addRecipient(farmer.getEmail());
            notificationRepo.save(notification);

            // Send email to farmer
            emailService.sendEmail(farmer.getEmail(), "You are ruined!!!", notification.getMessage());

            Notification notification1 = new Notification();
            BeanUtils.copyProperties(notification, notification1);
            return notification1;
        } catch (Exception e) {
            log.error("Error while sending notification to farmer", e);
            throw new RuntimeException("Failed to send notification");
        }
    }

    @Override
    public Notification sendNotificationToConsumer(int notificationId, int consumerId) {
        try {
            NotificationEntity notification = notificationRepo.findById(notificationId)
                    .orElseThrow(() -> new RuntimeException("Notification with ID " + notificationId + " not found"));

            ConsumerEntity consumer = consumerRepo.findById(consumerId)
                    .orElseThrow(() -> new RuntimeException("Consumer with ID " + consumerId + " not found"));

            notification.addRecipient(consumer.getEmail());
            notificationRepo.save(notification);

            // Send email to consumer
            emailService.sendEmail(consumer.getEmail(), "You are ruined!!!", notification.getMessage());

            Notification notification1 = new Notification();
            BeanUtils.copyProperties(notification, notification1);
            return notification1;
        } catch (Exception e) {
            log.error("Error while sending notification to consumer", e);
            throw new RuntimeException("Failed to send notification");
        }
    }




}
