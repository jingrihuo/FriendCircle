package com.example.a82173.friendcircle.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.adapter.NewClasscircleGridImageAdapter;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;


public class NewFriendCircle extends Activity {
    private GridView classcircleimgs;
    private TextView returnclasscircle;
    private TextView newcircle;
    private TextView dynamicText;

    private ArrayList<String> photos;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_circle);
        mActivity = this;
        classcircleimgs = (GridView) findViewById(R.id.classcircleimgs);
        returnclasscircle = (TextView) findViewById(R.id.returnclasscircle);
        newcircle = (TextView) findViewById(R.id.newcircle);
        dynamicText = (TextView) findViewById(R.id.dynamicText);
        classcircleimgs.setAdapter(new NewClasscircleGridImageAdapter(this,photos));

        returnclasscircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NewFriendCircle.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NewFriendCircle.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                classcircleimgs.setAdapter(new NewClasscircleGridImageAdapter(this,photos));
            }
        }
    }
}
