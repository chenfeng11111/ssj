package com.example.a526.ssj.clockservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.welcomeactivity.BaseActivity;

/**
 * Created by 10902 on 2019/6/3.
 */

public class ClockActivity extends BaseActivity {


    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_remind_activity);
        mediaPlayer = mediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        final Intent intent = getIntent();
        new AlertDialog.Builder(ClockActivity.this).setTitle("闹钟").setMessage(intent.getStringExtra("title"))
                .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                        GlobalVariable.getClockDatabaseHolder().deleteClock(intent.getIntExtra("ID", 1));//删除闹钟
                        ClockActivity.this.finish();
                    }
                }).show();
    }

}
