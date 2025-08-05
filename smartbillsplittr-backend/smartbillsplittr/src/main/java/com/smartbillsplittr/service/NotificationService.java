package com.smartbillsplittr.service;

import com.smartbillsplittr.dto.response.MessageResponse;

public interface NotificationService {

    void notifyUser(Long userId, String message);

    MessageResponse markAsRead(Long notificationId);

    MessageResponse markAllAsRead(Long userId);
}
