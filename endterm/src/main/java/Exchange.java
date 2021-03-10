import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
public class Exchange  {
    public double getExchange(String message, String secmsg, Model model) throws IOException {

        URL url=new URL("https://currate.ru/api/?get=rates&pairs="+message+secmsg+"&key=b5bd566fd00892920c968669259c22ea");
        Scanner in=new Scanner((InputStream) url.getContent());
        String result="";
        System.out.println(in);
        while (in.hasNext()==true){
            result+=in.nextLine();

        }
        JSONObject object=new JSONObject(result);
        JSONObject main=object.getJSONObject("data");
        try{
        model.setNum(main.getDouble(message+secmsg));} catch (JSONException e) {
            e.printStackTrace();

        }


        return model.getNum();
    }


}
