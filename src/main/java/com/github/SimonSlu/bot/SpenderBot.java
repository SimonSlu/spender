package com.github.SimonSlu.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.InputStream;
import java.util.Properties;

public class SpenderBot extends TelegramLongPollingBot{
    private final String botToken;

    public SpenderBot(){
        this.botToken = loadTokenFromConfig();
    }

    private String loadTokenFromConfig(){
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if(input == null) {
                throw new IllegalStateException("файл config.properties не найден");
            }
            Properties prop = new Properties();
            prop.load(input);

            String token = prop.getProperty("bot.token");
            if(token == null || token.isEmpty()){
                throw new IllegalStateException("Токен не найден в config.properties");
            }
            return token;
        }catch (Exception e){
            throw new RuntimeException("Ошибка загрузки токена", e);
        }
    }

    @Override
    public String getBotUsername() {
        return "MySpenderBot";
    }

    @Override
    public String getBotToken(){
        return botToken;
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
