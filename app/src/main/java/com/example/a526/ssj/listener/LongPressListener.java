package com.example.a526.ssj.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.a526.ssj.UnlockActivity;


/**
 * Created by 姜益昭 on 2019/5/29.
 */

public class LongPressListener  implements GestureDetector.OnGestureListener{
    //存储当前屏幕的Activity
    private Activity preActivity;
    public LongPressListener(Activity activity){
        preActivity = activity;
    }
    // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

      /* 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
	     * 注意和onDown()的区别，强调的是没有松开或者拖动的状态*/
    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
    @Override
    public void onLongPress(MotionEvent e) {
        //用户长按屏幕时，进行监听，跳转到加锁页面
        Intent intent =new Intent(preActivity,UnlockActivity.class);
        preActivity.startActivityForResult(intent,100);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
