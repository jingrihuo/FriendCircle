package com.example.a82173.friendcircle.http;

import android.os.StrictMode;

import com.example.a82173.friendcircle.activity.LoginActivity;

import org.json.JSONArray;
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
        String result = null;
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

    public String addDynamic(String userAccount,String schoolId,String classId,String dynamicText, String dynamicSrc){
        String result = null;
        try {
            String loadSevlet = sevlet+"?method=insertdynamic";
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
            JSONObject insertdynamic = new JSONObject();
            insertdynamic.put("check","classcircle-android");
            JSONArray dynamics = new JSONArray();
            JSONObject dynamic = new JSONObject();
            dynamic.put("userAccount",userAccount);
            dynamic.put("schoolId",schoolId);
            dynamic.put("classId",classId);
            dynamic.put("dynamicText",dynamicText);
            dynamic.put("dynamicSrc",dynamicSrc);
            dynamics.put(dynamic);
            insertdynamic.put("dynamic",dynamics);
            OutputStream os = urlConnection.getOutputStream();
            os.write(insertdynamic.toString().getBytes());
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

    public String deleteDynamic(String dynamicId){
        String result = null;
        try {
            String loadSevlet = sevlet+"?method=deletedynamic";
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
            JSONObject deletedynamic = new JSONObject();
            deletedynamic.put("check","classcircle-android");
            JSONArray dynamics = new JSONArray();
            JSONObject dynamic = new JSONObject();
            dynamic.put("dynamicId",dynamicId);
            dynamics.put(dynamic);
            deletedynamic.put("dynamic",dynamics);
            OutputStream os = urlConnection.getOutputStream();
            os.write(deletedynamic.toString().getBytes());
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

    public String replyClasscircle(int dynamicId,String userAccount,String comText,String comUserId){
        String result = null;
        try {
            String loadSevlet = sevlet+"?method=insertcommitment";
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
            JSONObject replyDynamic = new JSONObject();
            replyDynamic.put("check","classcircle-android");
            JSONArray commitments = new JSONArray();
            JSONObject commitment = new JSONObject();
            commitment.put("dynamicId",dynamicId);
            commitment.put("userAccount",userAccount);
            commitment.put("comText",comText);
            commitment.put("comUserId",comUserId);
            commitments.put(commitment);
            replyDynamic.put("commitment",commitments);
            OutputStream os = urlConnection.getOutputStream();
            os.write(replyDynamic.toString().getBytes());
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
    public String deleteDynamicComment(int comId){
        String result = null;
        try {
            String loadSevlet = sevlet+"?method=deletecommitment";
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
            JSONObject deleteComment = new JSONObject();
            deleteComment.put("check","classcircle-android");
            JSONArray commitments = new JSONArray();
            JSONObject commitment = new JSONObject();
            commitment.put("comId",comId);
            commitments.put(commitment);
            deleteComment.put("commitment",commitments);
            OutputStream os = urlConnection.getOutputStream();
            os.write(deleteComment.toString().getBytes());
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

    public String addDynamicLike(int dynamicId,String userAccount){
        String result = null;
        try {
            String loadSevlet = sevlet+"?method=dynamicup";
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
            JSONObject addLike = new JSONObject();
            addLike.put("check","classcircle-android");
            JSONArray dynamicups = new JSONArray();
            JSONObject dynamicup = new JSONObject();
            dynamicup.put("dynamicId",dynamicId);
            dynamicup.put("userAccount",userAccount);
            dynamicups.put(dynamicup);
            addLike.put("dynamicup",dynamicups);
            OutputStream os = urlConnection.getOutputStream();
            os.write(addLike.toString().getBytes());
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

    public String deleteDynamicLike(int dynamicId,String userAccount){
        String result = "null";
        try {
            String loadSevlet = sevlet+"?method=cancelUp";
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
            JSONObject deleteLike = new JSONObject();
            deleteLike.put("check","classcircle-android");
            deleteLike.put("dynamicId",dynamicId);
            deleteLike.put("userAccount",userAccount);
            OutputStream os = urlConnection.getOutputStream();
            os.write(deleteLike.toString().getBytes());
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
