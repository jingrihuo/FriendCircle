package com.example.a82173.friendcircle.http;

import android.os.StrictMode;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.a82173.friendcircle.http.HttpParsing.getStringFromInputStream;

public class HttpLogin {
    String LoginSevlet = "http://115.159.41.35:8080/ClassCircle/LoginServlet?method=login";

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

            String data = "un=" + username + "&pw=" + password;
            // 获得一个输出流，向服务器写数据，默认情况下，不允许程序向服务器输出数据
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();

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
