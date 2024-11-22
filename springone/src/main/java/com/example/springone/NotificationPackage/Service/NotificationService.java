package com.example.springone.NotificationPackage.Service;


import com.example.springone.NotificationPackage.Model.Notification;

import java.util.List;

public interface NotificationService {
      Notification createNotification(Notification notification);

    void deleteNotification(int id);

    void updateNotification(int id, Notification notification);

    Notification getNotificationById(int id);

    List<Notification> getAllNotifications();

    Notification sendNotificationToFarmer(int notificationId,int farmerid);

    Notification sendNotificationToConsumer(int notificationId, int consumerid);
}
