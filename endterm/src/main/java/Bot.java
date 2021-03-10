import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;

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

    String lastMessage="";
    String cout;

    String secMessage="";
    String start="/start";
    public int d=0;
    Model model=new Model();
    ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
    public long chat_id;
    Exchange exc=new Exchange();
    @Override
    public void onUpdateReceived(Update update) {


        update.getUpdateId();
        SendMessage sendMessage=new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id=update.getMessage().getChatId();
        String ping=update.getMessage().getText();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try{
            getMessage(ping);
            sendMessage.setText(cout);

            execute(sendMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }


    }
    public String getMessage(String msg) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        if(msg.equals("Back")){
            d=0;
            lastMessage="";
            secMessage="";
            getMessage("/start");
        }
        else if(start.equals(msg)||d==0){



            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardFirstRow.add("KZT");
            keyboardFirstRow.add("RUB");
            keyboardFirstRow.add("USD");
            keyboardFirstRow.add("EUR");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            cout= "Choose the first currency  ";
            d+=1;

        }


        else if(d==1) {


            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardFirstRow.add("KZT");
            keyboardFirstRow.add("RUB");
            keyboardFirstRow.add("USD");
            keyboardFirstRow.add("EUR");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            cout= "Choose the second currency";
            d+=1;
            lastMessage=msg;

        }
        else if(d==2){
            if(msg.equals(lastMessage)){
                cout="meaninglessly";
                keyboard.clear();
                keyboardFirstRow.clear();
                keyboardFirstRow.add("Back");
                keyboard.add(keyboardFirstRow);
                replyKeyboardMarkup.setKeyboard(keyboard);
            }
            else{
            secMessage=msg;

            cout="Choose the one option given below:\n" +
                        "1.Show me current exchange rate;\n" +
                        "2.Show me exchange of exact quantity;\n" +
                        "3.Back"+"\n";
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("current");
            keyboardFirstRow.add("exact quantity");
            keyboardFirstRow.add("Back");
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            d+=1;

            }}
            else if(d==3) {
                if(msg.equals("current")){
                    keyboard.clear();
                    keyboardFirstRow.clear();
                    keyboardFirstRow.add("Back");
                    keyboard.add(keyboardFirstRow);
                    replyKeyboardMarkup.setKeyboard(keyboard);
                    try {

                        cout ="1 "+lastMessage+ "="+exc.getExchange(lastMessage, secMessage, model)+secMessage;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
                else if(msg.equals("exact quantity")){
                    keyboard.clear();
                    keyboard.clear();
                    keyboardFirstRow.clear();
                    keyboardFirstRow.add("Back");
                    keyboard.add(keyboardFirstRow);
                    replyKeyboardMarkup.setKeyboard(keyboard);
                    cout="enter quantity..";



                }
                else {
                    double q=Double.parseDouble(msg);
            try {

                cout =q+" "+lastMessage+ "="+q*exc.getExchange(lastMessage, secMessage, model)+secMessage;
            } catch (IOException e) {
                e.printStackTrace();
            }
            lastMessage ="";
            secMessage ="";
            d=0;


        }}






        return msg;
    }


    @Override
    public String getBotUsername() {
        return "artoshbot";
    }

    @Override
    public String getBotToken() {
        return "1640391319:AAFipw8WnDg9GqZIdihuS5NszW5b47i8qHc";
    }



}
//try {
//                        sendMsg(message,Exchange.getExchange(message.getText(),model));
//                        System.out.println(Exchange.getExchange(message.getText(),model));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }