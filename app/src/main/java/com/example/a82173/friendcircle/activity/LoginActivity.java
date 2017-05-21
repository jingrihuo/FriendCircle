package com.example.a82173.friendcircle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.databean.UserData;
import com.example.a82173.friendcircle.http.HttpLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    public static UserData userData = new UserData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.userpwd);
        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        final String user = username.getText().toString();
                        final String pwd = password.getText().toString();
                        final HttpLogin httpLogin = new HttpLogin();
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String result = httpLogin.login(user,pwd);
                                JSONObject user = null;
                                try {
                                    user = new JSONObject(result);
                                    if (!user.getString("check").equals("classcircle-server")){
                                        Toast.makeText(LoginActivity.this,"网络传输故障，请稍候尝试",Toast.LENGTH_SHORT).show();
                                    }else if (!user.getString("error").isEmpty()){
                                        Toast.makeText(LoginActivity.this,user.getString("error"),Toast.LENGTH_SHORT).show();
                                    }else {
                                        JSONArray appUserData = user.getJSONArray("user");
                                        JSONObject appUserInfo = appUserData.getJSONObject(0);
                                        userData.setUserAccount(appUserInfo.getString("userAccount"));
                                        userData.setUserName(appUserInfo.getString("userName"));
                                        userData.setUserType(appUserInfo.getString("userType"));
                                        userData.setClassId(appUserInfo.getString("classId"));
                                        userData.setSchoolId(appUserInfo.getString("schoolId"));
                                        if (!appUserInfo.getString("userCoverSrc").equals("")){
                                            userData.setUserBG(appUserInfo.getString("userCoverSrc"));
                                        }
                                        if (!appUserInfo.getString("userHeadSrc").equals("")){
                                            userData.setUserHeadBg(appUserInfo.getString("userHeadSrc"));
                                        }
                                        Intent intent = new Intent();
                                        intent.setClass(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
