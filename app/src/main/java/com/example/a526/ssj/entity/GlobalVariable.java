package com.example.a526.ssj.entity;

import android.app.Application;

import com.example.a526.ssj.database.ClockDatabaseHolder;
import com.example.a526.ssj.database.NoteDatabaseHolder;

import java.io.File;

/**
 * Created by 10902 on 2019/6/2.
 */
//保存全局变量
public class GlobalVariable extends Application {

    private static int currentUserId;//当前使用的用户ID
    private static NoteDatabaseHolder noteDatabaseHolder;//笔记数据库的持有类，调用该对象的方法来对笔记数据库进行操作
    private static ClockDatabaseHolder clockDatabaseHolder;//闹钟数据库的持有类，调用该对象的方法来对闹钟数据库进行操作
    private static File fileStorePath;// 文件存储路径

    public static void setNoteDatabaseHolder(NoteDatabaseHolder noteDatabaseHolder) {
        GlobalVariable.noteDatabaseHolder = noteDatabaseHolder;
    }

    public static NoteDatabaseHolder getNoteDatabaseHolder() {
        return noteDatabaseHolder;
    }

    public static void setClockDatabaseHolder(ClockDatabaseHolder clockDatabaseHolder) {
        GlobalVariable.clockDatabaseHolder = clockDatabaseHolder;
    }

    public static ClockDatabaseHolder getClockDatabaseHolder() {
        return clockDatabaseHolder;
    }
    public static void setCurrentUserId(int currentUserId) {
        GlobalVariable.currentUserId = currentUserId;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static File getFileStorePath() {
        return fileStorePath;
    }

    public static void setFileStorePath(File fileStorePath) {
        GlobalVariable.fileStorePath = fileStorePath;
    }
}
