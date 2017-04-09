package com.example.a82173.friendcircle.http;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.a82173.friendcircle.http.HttpParsing.getStringFromInputStream;

public class HttpLogin {
    String LoginSevlet = "http://192.168.1.27:8080/ClassCircle/clienttest?method=login";

    public String login(String username,String password){
        String result = "null";
        try {
            URL url = new URL(LoginSevlet);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            urlConnection.setRequestMethod("POST");
            // 设置编码格式
            urlConnection.setRequestProperty("Charset", "UTF-8");
            // 设置容许输出
            urlConnection.setDoOutput(true);

            //把上面访问方式改为异步操作,就不会出现 android.os.NetworkOnMainThreadException异常
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

//            String data = "un=" + username + "&pw=" + password;
            // 获得一个输出流，向服务器写数据，默认情况下，不允许程序向服务器输出数据
            JSONObject test = new JSONObject();
            test.put("check1","Android");
            test.put("check2","Server");
            JSONArray user = new JSONArray();
            JSONObject usertest = new JSONObject();
            usertest.put("userAccount",username);
            usertest.put("userPassword",password);
            user.put(usertest);
            test.put("results",user);
            OutputStream os = urlConnection.getOutputStream();
            os.write(test.toString().getBytes());
            os.flush();
            os.close();

            urlConnection.setConnectTimeout(20);

            if (urlConnection.getResponseCode() == 200){
                InputStream is = urlConnection.getInputStream();
                result = getStringFromInputStream(is);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
