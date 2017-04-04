package com.example.a82173.friendcircle.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.http.HttpLogin;

public class LoginActivity extends Activity {
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
                                if (httpLogin.login(user,pwd).equals("successful")){
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this,MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
