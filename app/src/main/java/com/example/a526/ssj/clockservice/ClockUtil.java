package com.example.a526.ssj.clockservice;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.a526.ssj.entity.Clock;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by 10902 on 2019/6/3.
 */

public class ClockUtil {
    //创建闹钟
    public void setAlarm(Context context, Clock clock) {
        Date curtime = new Date();
        if (clock.getTime().after(curtime)) {//如果闹钟时间晚于当前时间则创建闹钟
            Intent intent = new Intent(context, ClockReceiver.class);
            intent.setAction("alarm");
            if (clock.getRelatedNoteId() != -1) {//如果ID大于-1，表示有笔记与之关联，获取关联笔记的标题并传递消息至闹钟处显示
                Note note = GlobalVariable.getNoteDatabaseHolder().searchNote(clock.getRelatedNoteId());
                intent.putExtra("title", note.getTitle());
            } else {//ID等于-1表示无笔记关联，无消息传递
                intent.putExtra("title", "no message");
            }
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
            Calendar c = Calendar.getInstance();
            c.setTime(clock.getTime());
            // Schedule the alarm!
            AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            am.setExact(AlarmManager.RTC, c.getTimeInMillis(), sender);//c为设置闹钟的时间的Calendar对象
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
            String time = simpleDateFormat.format(clock.getTime());
            System.out.println("set alarm " + time);
        }
    }

    //将闹钟存到数据库中并同时创建闹钟
    public void saveAlarm(Context context, Clock clock) {
        GlobalVariable.getClockDatabaseHolder().insertClock(clock);
        setAlarm(context, clock);
    }
}
