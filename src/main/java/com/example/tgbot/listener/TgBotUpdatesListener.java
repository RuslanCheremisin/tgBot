package com.example.tgbot.listener;

import com.example.tgbot.entity.NotificationTask;
import com.example.tgbot.service.NotificationTaskService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TgBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TgBotUpdatesListener.class);

    private final Pattern pattern = Pattern.compile("" +
            "(\\d{1,2}\\.\\d{2}\\.\\d{4} \\d{1,2}:\\d{2})\\s+" +
            "([А-я\\d\\s.,?/!:;'()]+)");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final TelegramBot bot;

    private final NotificationTaskService notificationTaskService ;

    public TgBotUpdatesListener(TelegramBot bot, NotificationTaskService notificationTaskService) {
        this.bot = bot;
        this.notificationTaskService = notificationTaskService;
    }

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream().filter(update ->update.message()!=null).forEach(update -> {
                logger.info("Handles update: {}", update);
                Message message = update.message();
                Long chatId = message.chat().id();
                String text = message.text();
                if ("/start".equals(text)) {
                    sendMessage(chatId, """
                            Привет!
                            Я помогу тебе запланировать задачу.
                            Отправь её в формате: 13.04.2023 12:00 Сделать домашку
                            """);
                }
//                if ("/wave".equals(text)) {
//                    SendAudio sendAudio = new SendAudio(chatId, new File("C:\\Users\\rus\\Downloads\\Evanescence - Discography - 1998-2018\\Albums (CD)\\2000 - Origin - (320 kbps)\\01. Origin.mp3"));
//                    bot.execute(sendAudio);
//                }

            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = bot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        } else if (message != null) {
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                LocalDateTime dateTime = parse(matcher.group(1));
                if (Objects.isNull(dateTime)) {
                    sendMessage(chatId, "Некорректный формат даты и/или времени");
                } else {
                    String txt = matcher.group(2);
                    NotificationTask notificationTask = new NotificationTask();
                    notificationTask.setMessage(txt);
                    notificationTask.setChatId(chatId);
                    notificationTask.setNotificationDateTime(dateTime);
                    notificationTaskService.save(notificationTask);
                }
            } else {
                sendMessage(chatId, "Некорректный формат сообщения");
            }
        }
    }

    @Nullable
    private LocalDateTime parse(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, dtf);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
