package com.example.a82173.friendcircle.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.a82173.friendcircle.databean.LikeData;
import com.example.a82173.friendcircle.http.HttpDynamic;
import com.example.a82173.friendcircle.http.HttpImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

import static com.example.a82173.friendcircle.activity.LoginActivity.userData;


public class NewFriendCircle extends Activity {
    private GridView classcircleimgs;
    private TextView returnclasscircle;
    private TextView newcircle;
    private TextView dynamicText;
    private HttpImage httpImage;
    private HttpDynamic httpDynamic;

    private ArrayList<String> photos;
    private ArrayList<Bitmap> dynamicPhotos;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_circle);
        mActivity = this;
        httpImage = new HttpImage();
        httpDynamic = new HttpDynamic();
        classcircleimgs = (GridView) findViewById(R.id.classcircleimgs);
        returnclasscircle = (TextView) findViewById(R.id.returnclasscircle);
        newcircle = (TextView) findViewById(R.id.newcircle);
        dynamicText = (TextView) findViewById(R.id.dynamicText);
        photos = new ArrayList<String>();
        classcircleimgs.setAdapter(new NewClasscircleGridImageAdapter(this, photos));

        returnclasscircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NewFriendCircle.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicPhotos = new ArrayList<Bitmap>();
                for (int i = 0; i < photos.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(photos.get(i));
                    dynamicPhotos.add(bitmap);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NewFriendCircle.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (photos.size() != 0) {
                                        String result = httpImage.uploadImgs(dynamicPhotos);
                                        JSONObject imgsResult = new JSONObject(result);
                                        if (!imgsResult.getString("check").equals("classcircle-server")) {
                                            Toast.makeText(NewFriendCircle.this, "网络传输故障，请稍候尝试", Toast.LENGTH_SHORT).show();
                                        } else if (!imgsResult.getString("error").isEmpty()) {
                                            Toast.makeText(NewFriendCircle.this, imgsResult.getString("error"), Toast.LENGTH_SHORT).show();
                                        } else {
                                            result = httpDynamic.addDynamic(userData.getUserAccount(), userData.getSchoolId(), userData.getClassId(), dynamicText.getText().toString().trim(), imgsResult.getString("dynamicSrc"));
                                            JSONObject dynamicResult = new JSONObject(result);
                                            if (!dynamicResult.getString("check").equals("classcircle-server")) {
                                                Toast.makeText(NewFriendCircle.this, "网络传输故障，请稍候尝试", Toast.LENGTH_SHORT).show();
                                            } else if (!dynamicResult.getString("error").isEmpty()) {
                                                Toast.makeText(NewFriendCircle.this, dynamicResult.getString("error"), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Intent intent = new Intent();
                                                intent.setClass(NewFriendCircle.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    } else {
                                        if (TextUtils.isEmpty(dynamicText.getText().toString().trim())) {
                                            Toast.makeText(NewFriendCircle.this, "图片和文字不可全空", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String result = httpDynamic.addDynamic(userData.getUserAccount(), userData.getSchoolId(), userData.getClassId(), dynamicText.getText().toString().trim(),"");
                                            JSONObject dynamicResult = new JSONObject(result);
                                            if (!dynamicResult.getString("check").equals("classcircle-server")) {
                                                Toast.makeText(NewFriendCircle.this, "网络传输故障，请稍候尝试", Toast.LENGTH_SHORT).show();
                                            } else if (!dynamicResult.getString("error").isEmpty()) {
                                                Toast.makeText(NewFriendCircle.this, dynamicResult.getString("error"), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Intent intent = new Intent();
                                                intent.setClass(NewFriendCircle.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                classcircleimgs.setAdapter(new NewClasscircleGridImageAdapter(this, photos));
            }
        }
    }
}
