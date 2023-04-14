package com.example.tgbot.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_tasks")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;

    private long chatId;

    private LocalDateTime notificationDateTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public void setNotificationDateTime(LocalDateTime dateTime) {
        this.notificationDateTime = dateTime;
    }

    public long getId(){
        return id;
    }

    public String getMessage(){
        return message;
    }

    public long getChatId(){
        return chatId;
    }

    public LocalDateTime getNotificationDateTime(){
        return notificationDateTime;
    }

}
