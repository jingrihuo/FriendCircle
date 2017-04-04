package com.example.a82173.friendcircle.json;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.a82173.friendcircle.sqlite.UserDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 82173 on 2016/12/5.
 */
public class TestJson {

    public static String testJson(Context context){
        HttpHandler httpHandler = new HttpHandler();
        httpHandler.SetHttpJson();
        String jsonString = httpHandler.json;
        Toast.makeText(context,jsonString, Toast.LENGTH_SHORT).show();
        if (jsonString!=null) {
            try {
                JSONArray contacts = new JSONArray(jsonString);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject jsonObj = contacts.getJSONObject(i);
                    int megnumber = jsonObj.getInt("megnumber");
                    String username = jsonObj.getString("username");
                    String megstring = jsonObj.getString("megstring");
                    int megimage1 = jsonObj.getInt("megimage1");
                    UserDBHelper dbHelper = new UserDBHelper(context, "user_db", null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();//得到一个可写的数据库
                    db.execSQL("insert into friendcircle(megnumber,username,megstring,megimage1) values(?,?,?,?)",
                            new Object[]{megnumber, username, megstring, megimage1});
                    db.close();
                }
                return "1234";
            } catch (final JSONException e) {

            }
        }
        return "123";
    }
}
