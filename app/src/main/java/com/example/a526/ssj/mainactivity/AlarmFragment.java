package com.example.a526.ssj.mainactivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 10902 on 2019/5/28.
 */

public class AlarmFragment extends BaseFragment  {

    private TextView textView;

    @Override
    protected View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        super.initData();
        textView.setText("alarm_fragment");
    }

}