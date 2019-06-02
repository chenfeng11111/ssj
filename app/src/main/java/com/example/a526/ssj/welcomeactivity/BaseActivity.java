package com.example.a526.ssj.welcomeactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a526.ssj.database.ClockDatabaseHolder;
import com.example.a526.ssj.database.NoteDatabaseHolder;
import com.example.a526.ssj.entity.GlobalVariable;

/**
 * Created by 仲夏丶我们一起 on 2019/5/28.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalVariable.setNoteDatabaseHolder(new NoteDatabaseHolder(this));
        GlobalVariable.setClockDatabaseHolder(new ClockDatabaseHolder(this));
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
