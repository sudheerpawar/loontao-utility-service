package com.loontao.utilityservice.service;

import com.loontao.utilityservice.dto.NotificationDTO;
import com.loontao.utilityservice.entity.Notification;
import com.loontao.utilityservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return new ArrayList<>(notificationRepository.findAll());
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification addNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setCategory(notificationDTO.getCategory());
        notification.setMainText(notificationDTO.getMainText());
        notification.setSubText(notificationDTO.getSubText());
        notification.setFullText(notificationDTO.getFullText());
        return notificationRepository.save(notification);
    }

    public Notification updateNotificationById(Long id, NotificationDTO notificationDTO) {
        return notificationRepository.findById(id).map(notification -> {
            notification.setCategory(notificationDTO.getCategory());
            notification.setMainText(notificationDTO.getMainText());
            notification.setSubText(notificationDTO.getSubText());
            notification.setFullText(notificationDTO.getFullText());
            return notificationRepository.save(notification);
        }).orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }


}
