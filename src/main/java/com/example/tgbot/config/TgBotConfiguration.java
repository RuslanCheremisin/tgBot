package com.example.tgbot.config;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TgBotConfiguration {
    @Bean
    public TelegramBot telegramBot(@Value("${tg.bot.token}")String token){
        return new TelegramBot(token);
    }

}
