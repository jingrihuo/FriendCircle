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
    String sevlet = "http://118.89.173.107:8080/ClassCircle/clientservlet";

    public String login(String username,String password){
        String result = "null";
        try {
            String LoginSevlet = sevlet+"?method=login";
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
            // 获得一个输出流，向服务器写数据，默认情况下，不允许程序向服务器输出数据
            JSONObject check = new JSONObject();
            check.put("check","classcircle-android");
            JSONArray user = new JSONArray();
            JSONObject usertest = new JSONObject();
            usertest.put("userAccount",username);
            usertest.put("userPwd",password);
            user.put(usertest);
            check.put("user",user);
            OutputStream os = urlConnection.getOutputStream();
            os.write(check.toString().getBytes());
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

    public String modifyPwd(String userAccount,String userPwd,String userNewPwd){
        String result = null;
        try {
            String LoginSevlet = sevlet+"?method=updatepwd";
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
            // 获得一个输出流，向服务器写数据，默认情况下，不允许程序向服务器输出数据
            JSONObject check = new JSONObject();
            check.put("check","classcircle-android");
            JSONArray user = new JSONArray();
            JSONObject usertest = new JSONObject();
            usertest.put("userAccount",userAccount);
            usertest.put("userPwd",userPwd);
            usertest.put("userNewPwd",userNewPwd);
            user.put(usertest);
            check.put("user",user);
            OutputStream os = urlConnection.getOutputStream();
            os.write(check.toString().getBytes());
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
