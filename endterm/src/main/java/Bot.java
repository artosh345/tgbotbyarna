import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi=new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
public void sendMsg(Message message, String text){
    SendMessage sendMessage= new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(message.getChatId().toString());
    sendMessage.setReplyToMessageId(message.getMessageId());
    sendMessage.setText(text);
    try{
        setButton(sendMessage);
        sendMessage(sendMessage);
}
    catch (TelegramApiException e){
        e.printStackTrace();
    }}
    @Override
    public void onUpdateReceived(Update update) {
        Message message=update.getMessage();
        if(message!=null && message.hasText()){
            switch (message.getText()){
                case "привет":
                    sendMsg(message, "салем");
                    break;
                case "саламалейкум":
                    sendMsg(message,"уалейкум салам");
                    break;
                default:
                    sendMsg(message, "салем");
            }
        }
    }
    public void setButton(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList=new ArrayList<>();
        KeyboardRow keyboardFirstRow=new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("привет"));
        keyboardFirstRow.add(new KeyboardButton("саламалейкум"));
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
    @Override
    public String getBotUsername() {
        return "artoshbot";
    }

    @Override
    public String getBotToken() {
        return "1610858877:AAG3W-z-1PoNkebahuZfzgHdE0NJa_-3Ec0";
    }
}
