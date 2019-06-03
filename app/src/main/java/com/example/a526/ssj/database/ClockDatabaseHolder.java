package com.example.a526.ssj.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a526.ssj.entity.Clock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 10902 on 2019/6/2.
 */
/*
  表结构
  clockId integer 自增变量
  time varchar 设定的时间
  relatedNoteID integer  与该闹钟关联的笔记的ID
 */
public class ClockDatabaseHolder {
    private ClockDatabaseHelper clockDatabaseHelper;
    private SQLiteDatabase clockDatabase;
    private final String clockDatabaseName = "clock";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");

    public ClockDatabaseHolder(Context context) {
        clockDatabaseHelper = new ClockDatabaseHelper(context, clockDatabaseName, 1);
        clockDatabase = clockDatabaseHelper.getWritableDatabase();
    }

    public int insertClock(Clock clock) {
        ContentValues values = new ContentValues();
        values.put("relatedNoteID", clock.getRelatedNoteId());
        String time = simpleDateFormat.format(clock.getTime());
        values.put("time", time);
        //数据库执行插入命令
        return (int) clockDatabase.insert(clockDatabaseName, null, values);
    }

    public void deleteClock(int clockID) {
        //删除条件
        String whereClause = "clockId=?";
        //删除条件参数
        String[] whereArgs = {String.valueOf(clockID)};
        //执行删除
        clockDatabase.delete(clockDatabaseName, whereClause, whereArgs);
    }

    //按clockID单独查询
    public Clock searchClock(int clockId) {
        Cursor cursor = clockDatabase.query(clockDatabaseName, null, "clockId=?", new String[]{String.valueOf(clockId)},
                null, null, null, null);
        Clock clock = null;
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            clock = new Clock();
            clock.setId(cursor.getInt(0));
            String str = cursor.getString(1);
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            clock.setTime(date);
            clock.setRelatedNoteId(cursor.getInt(2));
        }
        return clock;
    }

    //第一个参数传入需要查询的数量，0为查询所有，第二个参数传入从第几个开始查询，返回值按时间由晚到早排列
    public List<Clock> searchClock(int number, int offest) {
        boolean all = number == 0;
        Cursor cursor = clockDatabase.query(clockDatabaseName, null, null,
                null, null, null, "clockId desc", null);
        List<Clock> list = new ArrayList<>();
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            cursor.move(offest);
            do {
                Clock clock = new Clock();
                clock.setId(cursor.getInt(0));
                String str = cursor.getString(1);
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                clock.setTime(date);
                clock.setRelatedNoteId(cursor.getInt(2));
                list.add(clock);
                number--;
            } while (cursor.moveToNext() && (number > 0 || all));
        }
        return list;
    }

    public void updateClock(Clock clock) {
        ContentValues values = new ContentValues();
        values.put("relatedNoteID", clock.getRelatedNoteId());
        String time = simpleDateFormat.format(clock.getTime());
        values.put("time", time);
        String whereClause = "clockId=?";
        String[] whereArgs = {String.valueOf(clock.getId())};
        clockDatabase.update(clockDatabaseName, values, whereClause, whereArgs);
    }
}
