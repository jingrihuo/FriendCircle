package com.example.a82173.friendcircle.http;

import android.os.StrictMode;

import com.example.a82173.friendcircle.activity.LoginActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.a82173.friendcircle.http.HttpParsing.getStringFromInputStream;

/**
 * Created by yuritian on 2017/4/10.
 */

public class HttpDynamic {
    String sevlet = "http://192.168.1.16:8080/ClassCircle/clientservlet";
    public String loadClasscircle(){
        String result = "null";
        try {
            String loadSevlet = sevlet+"?method=loaddynamic";
            URL url = new URL(loadSevlet);
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
            JSONObject loadDynamic = new JSONObject();
            loadDynamic.put("check","classcircle-android");
            loadDynamic.put("schoolId", LoginActivity.userData.getSchoolId());
            loadDynamic.put("classId", LoginActivity.userData.getClassId());
            loadDynamic.put("dynamicId", 0);
            OutputStream os = urlConnection.getOutputStream();
            os.write(loadDynamic.toString().getBytes());
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
