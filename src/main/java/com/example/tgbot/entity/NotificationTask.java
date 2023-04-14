package com.example.tgbot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_tasks")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false, name = "chat_id")
    private long chatId;
    @Column(nullable = false, name = "notification_date_time")
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