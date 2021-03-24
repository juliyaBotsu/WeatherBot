import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Bot extends TelegramLongPollingBot {
    public String getBotUsername() {
        return "WeatherJuliaBotsuBot";
    }

    public String getBotToken() {
        return "1572266916:AAEd3OIJ7ew8KhT5p9igsEnmO90mRgyBhZc";
    }
   public void sendMsg(String text,Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text.replaceAll("([_*`])", "\\\\$1"));
       try {
           setButton(sendMessage);
           execute(sendMessage);

       } catch (TelegramApiException e) {
           e.printStackTrace();
       }
        System.out.println("hi");
   }
    public void onUpdateReceived(Update update) {
Model model=new Model();
              Message message = update.getMessage();
              if (message!=null && message.hasText()){
                  switch (message.getText()){
                      case "/help":
                          sendMsg("Какие сложности у вас возникли?",message);
                          break;
                      case "/start":
                          sendMsg("Доброго времени суток! В каком городе хотите узнать погоду?☀️🌦",message);
                          break;
                      default:
                          try {
                              sendMsg(Weather.getWeather(message.getText(),model),message);
                          } catch (IOException e) {
                              sendMsg("Город не найден",message);
                          }
                  }
              }

    }
    public void setButton(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirst = new KeyboardRow();
        keyboardFirst.add(new KeyboardButton("/start"));
        keyboardFirst.add(new KeyboardButton("/help"));
        keyboardRowList.add(keyboardFirst);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
