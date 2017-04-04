package com.example.a82173.friendcircle.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 82173 on 2016/12/4.
 */
public class UserDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    public static final int VERSION = 1;
    public UserDBHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table \"user\" (\n" +
                "username             VARCHAR(20)                    not null,\n" +
                "userimage            INTEGER,\n" +
                "primary key (username)\n" +
                ");";
        sqLiteDatabase.execSQL(sql);//执行sql语句
        sql = "CREATE TABLE friendcircle (\n" +
                "    megnumber INTEGER      PRIMARY KEY AUTOINCREMENT\n" +
                "                           NOT NULL,\n" +
                "    username  VARCHAR (20),\n" +
                "    megstring VARCHAR (50),\n" +
                "    megimage1 INTEGER,\n" +
                "    megimage2 INTEGER,\n" +
                "    megimage3 INTEGER,\n" +
                "    megimage4 INTEGER,\n" +
                "    megimage5 INTEGER,\n" +
                "    megimage6 INTEGER,\n" +
                "    megimage7 INTEGER,\n" +
                "    megimage8 INTEGER,\n" +
                "    megimage9 INTEGER,\n" +
                "    thumbup   INTEGER,\n" +
                "    FOREIGN KEY (\n" +
                "        username\n" +
                "    )\n" +
                "    REFERENCES user (username) \n" +
                ");\n";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE comments (\n" +
                "    commentsnum    INTEGER      PRIMARY KEY AUTOINCREMENT\n" +
                "                                NOT NULL,\n" +
                "    megnumber      INTEGER,\n" +
                "    commentsstring VARCHAR (50),\n" +
                "    FOREIGN KEY (\n" +
                "        megnumber\n" +
                "    )\n" +
                "    REFERENCES friendcircle (megnumber) \n" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息
        Log.i(TAG, "update Database------------->");
    }

}