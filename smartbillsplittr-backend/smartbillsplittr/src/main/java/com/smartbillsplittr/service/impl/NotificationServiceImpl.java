package com.smartbillsplittr.service.impl;

import com.smartbillsplittr.dto.response.MessageResponse;
import com.smartbillsplittr.exception.ResourceNotFoundException;
import com.smartbillsplittr.model.Notification;
import com.smartbillsplittr.model.User;
import com.smartbillsplittr.repository.NotificationRepository;
import com.smartbillsplittr.repository.UserRepository;
import com.smartbillsplittr.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void notifyUser(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Notification n = new Notification(user, message, Notification.NotificationType.EXPENSE_ADDED);
        notificationRepository.save(n);
    }

    @Override
    public MessageResponse markAsRead(Long notificationId) {
        notificationRepository.markAsRead(notificationId);
        return new MessageResponse("Notification marked as read");
    }

    @Override
    public MessageResponse markAllAsRead(Long userId) {
        notificationRepository.markAllAsReadByUserId(userId);
        return new MessageResponse("All notifications marked as read");
    }
}
