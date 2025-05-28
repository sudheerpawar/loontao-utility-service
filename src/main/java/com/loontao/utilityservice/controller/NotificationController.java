package com.loontao.utilityservice.controller;

import com.loontao.utilityservice.dto.NotificationDTO;
import com.loontao.utilityservice.entity.Notification;
import com.loontao.utilityservice.exceptions.ResourceNotFoundException;
import com.loontao.utilityservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private NotificationService notificationService;

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Notification>> getAllNotification() {
        List<Notification> notificationList = notificationService.getAllNotifications();
        return ResponseEntity.ok(notificationList);
    }

    @GetMapping("/get/{id}")
    public Notification getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id).orElseThrow(() -> new ResourceNotFoundException("Entry not found"));
    }

    @PostMapping("/add")
    public Notification addNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationService.addNotification(notificationDTO);
    }

    @PutMapping("/update/{id}")
    public Notification updateNotificationById(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
        return notificationService.updateNotificationById(id, notificationDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteNotificationById(@PathVariable Long id) {
        notificationService.deleteNotificationById(id);
    }


}
