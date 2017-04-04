package com.example.a82173.friendcircle.json;

import android.os.Handler;
import android.os.Message;

import com.example.a82173.friendcircle.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by pengbin on 2016/11/12.
 */
public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }
    public Message message = new Message();
    public String json = null;
    public void SetHttpJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://172.20.10.4/json.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null) {
                        response.append(line);
                    }
                    message.what = 1;
                    message.obj = response.toString();
                    handler.sendMessage(message);
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private Handler handler = new Handler(){
        public void handleMeg(Message msg){
            switch (message.what) {
                case 1:
                    json = (String) msg.obj;
                    testJson(json);
            }
        }
    };

    public static void testJson(String json){
        if (json!=null) {
            try {
                JSONArray contacts = new JSONArray(json);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject jsonObj = contacts.getJSONObject(i);
                    int megnumber = jsonObj.getInt("megnumber");
                    String username = jsonObj.getString("username");
                    String megstring = jsonObj.getString("megstring");
                    int megimage1 = jsonObj.getInt("megimage1");
                    MainActivity.db.execSQL("insert into friendcircle(megnumber,username,megstring,megimage1) values(?,?,?,?)",
                            new Object[]{megnumber, username, megstring, megimage1});
                    MainActivity.db.close();
                }
            } catch (final JSONException e) {

            }
        }
    }
}
