package com.example.a526.ssj.listener;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 姜益昭 on 2019/5/29.
 */

public class OnTouchListenerAdapter implements View.OnTouchListener{
    private int status;
    private Activity activity;
    private GestureDetector mGestureDetector;
    public OnTouchListenerAdapter(Activity activity,int status)
    {
        this.activity= activity;
        this.status=status;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (status){
            case 0:{
                mGestureDetector=new GestureDetector( new LongPressListener(activity));
                return mGestureDetector.onTouchEvent(event);
            }
            default:{
                return false;
            }
        }
    }
}
