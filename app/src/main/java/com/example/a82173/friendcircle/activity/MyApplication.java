package com.example.a82173.friendcircle.activity;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

public class MyApplication extends Application {

	private static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
	}

	public static Context getContext(){
		return mContext;
	}
	
}
