package com.github.SimonSlu.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SpenderBot extends TelegramLongPollingBot{

    @Override
    public String getBotUsername() {
        return "MySpenderBot";
    }

    @Override
    public String getBotToken(){
        return "7619002906:AAFm4TA2qNHsp5zOuUB7Q0_k6yFFUr3r0qc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if(messageText.equals("/start")){
                sendMessage(chatId, "Привет! Я бот для учета финансов");
            }
        }
    }

    public void sendMessage(long chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try{
            execute(message);
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }
}
