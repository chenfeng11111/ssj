package com.example.a526.ssj.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a526.ssj.entity.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 10902 on 2019/6/2.
 */
//数据库的持有类，调用该类的方法来对数据库进行操作
public class NoteDatabaseHolder {
    private NoteDatabaseHelper noteDatabaseHelper;
    private SQLiteDatabase noteDatabase;
    private final String noteDatabaseName = "note";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");

    public NoteDatabaseHolder(Context context) {
        noteDatabaseHelper = new NoteDatabaseHelper(context, noteDatabaseName, 1);
        noteDatabase = noteDatabaseHelper.getWritableDatabase();
    }

    public void insertNote(Note note) {
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("isUpload", note.getUpload() ? 1 : 0);
        values.put("isShare", note.getShare() ? 1 : 0);
        String time = simpleDateFormat.format(note.getSaveTime());
        values.put("saveTime", time);
        values.put("userId", note.getUserId());
        //数据库执行插入命令
        noteDatabase.insert(noteDatabaseName, null, values);
    }

    public void deleteNote(int noteId) {
        //删除条件
        String whereClause = "noteId=?";
        //删除条件参数
        String[] whereArgs = {String.valueOf(noteId)};
        //执行删除
        noteDatabase.delete(noteDatabaseName, whereClause, whereArgs);
    }

    public void searchNote() {

    }

    //第一个参数传入需要查询的数量，第二个参数传入从第几个开始查询，返回值按时间由晚到早排列
    public List<Note> searchNote(int number, int offest) {
        Cursor cursor = noteDatabase.query(noteDatabaseName, null, null,
                null, null, null, "noteId desc", null);
        List<Note> list = new ArrayList<>();
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            cursor.move(offest);
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setUpload(cursor.getInt(3) == 1);
                note.setShare(cursor.getInt(4) == 1);
                String str = cursor.getString(5);
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                note.setSaveTime(date);
                note.setUserId(cursor.getInt(6));
                list.add(note);
                number--;
            } while (cursor.moveToNext() && number > 0);
        }
        return list;
    }

    public void updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("isUpload", note.getUpload() ? 1 : 0);
        values.put("isShare", note.getShare() ? 1 : 0);
        String time = simpleDateFormat.format(note.getSaveTime());
        values.put("saveTime", time);
        values.put("userId", note.getUserId());
        //数据库执行插入命令
        String whereClause = "noteId=?";
        String[] whereArgs = {String.valueOf(note.getId())};
        noteDatabase.update(noteDatabaseName, values, whereClause, whereArgs);
    }
}
