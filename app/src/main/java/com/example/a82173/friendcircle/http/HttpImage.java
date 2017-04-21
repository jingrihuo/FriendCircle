package com.example.a82173.friendcircle.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yuritian on 2017/4/11.
 */

public class HttpImage {
    private Bitmap bitmap=null;
    @Nullable
    public static void getImage() {
//        new Thread(){
//            public void run()
//            {
//                try {
//                    URL url = new URL(picURL);
//                    HttpURLConnection conn = (HttpURLConnection) url
//                            .openConnection();
//                    conn.setDoInput(true);
//                    conn.connect();
//                    InputStream inputStream = conn.getInputStream();
//                    bitmap = BitmapFactory.decodeStream(inputStream);
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            urlImageView.setImageBitmap(bitmap);
//                        }
//                    });
//                } catch (MalformedURLException e1) {
//                    e1.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }
}
