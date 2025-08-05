package com.smartbillsplittr.controller;

import com.smartbillsplittr.dto.response.MessageResponse;
import com.smartbillsplittr.model.Notification;
import com.smartbillsplittr.model.User;
import com.smartbillsplittr.repository.NotificationRepository;
import com.smartbillsplittr.repository.UserRepository;
import com.smartbillsplittr.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.smartbillsplittr.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationController(NotificationService notificationService,
                                  NotificationRepository notificationRepository,
                                  UserRepository userRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    private Long currentUserId() {
        return ((UserPrincipal)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId();
    }

    @GetMapping
    public ResponseEntity<List<Notification>> listNotifications() {
        User user = userRepository.findById(currentUserId()).orElseThrow();
        return ResponseEntity.ok(notificationRepository.findByUserOrderByCreatedAtDesc(user));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<MessageResponse> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PostMapping("/read-all")
    public ResponseEntity<MessageResponse> markAllAsRead() {
        return ResponseEntity.ok(notificationService.markAllAsRead(currentUserId()));
    }
}
