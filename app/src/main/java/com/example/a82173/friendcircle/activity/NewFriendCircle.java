package com.example.a82173.friendcircle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a82173.friendcircle.R;


public class NewFriendCircle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_circle);
        Button SetMegs = (Button) findViewById(R.id.btn_setMegs);
        Button BackFriendCircle = (Button) findViewById(R.id.btn_BackFriendCircle);
        final EditText Message = (EditText) findViewById(R.id.edt_megs);
        SetMegs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Megs = Message.getText().toString();

            }
        });
        BackFriendCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewFriendCircle.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
