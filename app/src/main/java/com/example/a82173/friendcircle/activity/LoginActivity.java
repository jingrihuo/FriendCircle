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

public class LoginActivity extends Activity {
    public static UserData userData = new UserData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = (EditText)findViewById(R.id.username2);
        final EditText password = (EditText)findViewById(R.id.password2);
        Button login = (Button)findViewById(R.id.login2);
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
                                Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
                                if (result.equals("success")){
                                    userData.setUserName(user);
                                    userData.setUserType("班主任");
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this,MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                }else if(result.equals("faild")){
                                    Toast.makeText(LoginActivity.this,"账号密码有误",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LoginActivity.this,"当前网络状态不好或服务器故障请稍后再试",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
