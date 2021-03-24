import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    //http://api.openweathermap.org/data/2.5/find?q=London,uk&units=metric&appid=7439f02f7240d422974896ae251e9767
    public static String getWeather(String message,Model model) throws IOException {

            URL url =new URL("http://api.openweathermap.org/data/2.5/find?q="+message+",ua&units=metric&appid=7439f02f7240d422974896ae251e9767");
        Scanner in = new Scanner((InputStream)url.getContent());
        String result = "";
        while (in.hasNext()){
            result =result + in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONArray getArray = object.getJSONArray("list");


        for (int i = 0 ; i<getArray.length();i++){
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject main = obj.getJSONObject("main");
            JSONObject wind = obj.getJSONObject("wind");
            model.setName((String)obj.get("name"));
            model.setFeels_like(main.getDouble("feels_like"));
            model.setTemp(main.getDouble("temp"));
            model.setHumidity(main.getDouble("humidity"));
            model.setSpeed(wind.getInt("speed"));
        }


       return  "Город "+model.getName()+" 🏠"+"\n"+
               "Температура "+model.getTemp()+" C 🌡"+"\n"+
               "Ощущается как "+model.getFeels_like()+" C 🌡"+"\n"+
               "Скорость ветра "+model.getSpeed()+" м/с 💨"+"\n"+
               "Влажность "+model.getHumidity()+" % 💧"+"\n";
    }
}
