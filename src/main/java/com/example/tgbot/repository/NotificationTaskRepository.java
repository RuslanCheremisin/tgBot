package com.example.tgbot.repository;

import com.example.tgbot.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
}
