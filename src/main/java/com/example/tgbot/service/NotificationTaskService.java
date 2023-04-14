package com.example.tgbot.service;

import com.example.tgbot.entity.NotificationTask;
import com.example.tgbot.repository.NotificationTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }
    public void save(NotificationTask notificationTask){
        notificationTaskRepository.save(notificationTask);
    }
}
