package com.example.a526.ssj.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 10902 on 2019/6/2.
 */
/*
  数据库表结构
  noteId 笔记ID，自增量，主键
  title varchar 标题
  content text 内容
  isUpload integer 0表示未上传，1表示已上传
  isShare integer 0表示未分享，1表示已分享
  saveTime varchar 表示时间，格式是yyyy-MM-dd HH:mm:ss
  userId integer 创建者
  code varchar 笔记的唯一标识符，生成规则是userID+savetime
  version integer 笔记的版本
 */
public class NoteDatabaseHelper extends SQLiteOpenHelper {

    public NoteDatabaseHelper(Context context, String name, int version) {
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS note (" +
                "noteId integer primary key autoincrement, " +
                "title varchar(100), " +
                "content text," +
                "isUpload integer," +
                "isShare integer," +
                "saveTime vatchar(100)," +
                "userId integer," +
                "code varchar(100)," +
                "version integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)"); //往表中增加一列
    }
}