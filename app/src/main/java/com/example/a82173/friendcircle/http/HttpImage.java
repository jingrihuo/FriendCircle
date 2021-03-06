package com.example.a82173.friendcircle.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.a82173.friendcircle.activity.LoginActivity.userData;

/**
 * Created by yuritian on 2017/4/11.
 */

public class HttpImage {
    String sevlet = "http://118.89.173.107:8080/ClassCircle/clientservlet";
    public String uploadImgs(ArrayList<Bitmap> bitmaps){
        String result = "null";
        try {
            String loadSevlet = sevlet+"?method=uploadfile";
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
            JSONObject uploadImage = new JSONObject();
            uploadImage.put("check","classcircle-android");
            uploadImage.put("userAccount",userData.getUserAccount());
            JSONArray Images = new JSONArray();
            for (int i = 0;i<bitmaps.size();i++){
                JSONObject Image = new JSONObject();
                Image.put("fileType","JPEG");
                Image.put("file",convertIconToString(bitmaps.get(i)));
                Images.put(Image);
            }
            uploadImage.put("file",Images);
            OutputStream os = urlConnection.getOutputStream();
            os.write(uploadImage.toString().getBytes());
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
    public String uploadHeadImg(Bitmap bitmap){
        String result = "null";
        try {
            String loadSevlet = sevlet+"?method=uploadHead";
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
            JSONObject uploadHeadImage = new JSONObject();
            uploadHeadImage.put("check","classcircle-android");
            uploadHeadImage.put("userAccount",userData.getUserAccount());
            uploadHeadImage.put("uploadHead",convertIconToString(bitmap));
            OutputStream os = urlConnection.getOutputStream();
            os.write(uploadHeadImage.toString().getBytes());
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
    public String uploadBgImg(Bitmap bitmap){
        String result = "null";
        try {
            String loadSevlet = sevlet+"?method=uploadCover";
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
            JSONObject uploadBgImage = new JSONObject();
            uploadBgImage.put("check","classcircle-android");
            uploadBgImage.put("userAccount",userData.getUserAccount());
            uploadBgImage.put("uploadCover",convertIconToString(bitmap));
            OutputStream os = urlConnection.getOutputStream();
            os.write(uploadBgImage.toString().getBytes());
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

    public static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        String result = baos.toString();
        is.close();
        baos.close();
        return result;
    }
    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }
}
