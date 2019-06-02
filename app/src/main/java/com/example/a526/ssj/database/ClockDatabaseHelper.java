package com.example.a526.ssj.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 10902 on 2019/6/2.
 */

/*
  表结构
  clockId integer 自增变量
  time varchar 设定的时间
  relatedNoteID integer  与该闹钟关联的笔记的ID
 */
public class ClockDatabaseHelper extends SQLiteOpenHelper{

    public ClockDatabaseHelper(Context context, String name, int version) {
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS clock (" +
                "clockId integer primary key autoincrement, " +
                "time varchar(100), " +
                "relatedNoteID integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)"); //往表中增加一列
    }
}
