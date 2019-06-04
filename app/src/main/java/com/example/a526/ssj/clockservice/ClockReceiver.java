package com.example.a526.ssj.clockservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 10902 on 2019/6/3.
 */

public class ClockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("alarm")) {
            //获取传递的信息
            String msg = intent.getStringExtra("title");
            intent = new Intent(context, ClockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("title", msg);
            context.startActivity(intent);
        }
    }
}
