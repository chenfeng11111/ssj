package com.example.a526.ssj.mainactivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.a526.ssj.R;
import com.example.a526.ssj.clockservice.ClockUtil;
import com.example.a526.ssj.entity.Clock;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.lockactivity.UnlockActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements AlarmFragment.OnFragmentInteractionListener {
    private RadioGroup mainRadioGroup;//主界面的radio group
    private List<Fragment> mainFragmentList; //主界面的fragment集合
    private RadioButton radioButtonNote;
    private RadioButton radioButtonClock;
    private RadioButton radioButtonSquare;
    private RadioButton radioButtonMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取环境外部存储路径
        GlobalVariable.setFileStorePath(Environment.getExternalStorageDirectory());
        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
        //设置控件在最上层
        mainRadioGroup.bringToFront();
        //读取数据库中的所有闹钟并创建，
        setAllAlarm();
        makeStatusBarTransparent();
    }

    private void setAllAlarm() {
        List<Clock> clocks = GlobalVariable.getClockDatabaseHolder().searchClock(0, 0);
        for (int i = 0; i < clocks.size(); i++) {
            Clock clock = clocks.get(i);
            new ClockUtil().setAlarm(MainActivity.this, clock);
        }
    }

    private void setListener() {
        mainRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //添加手势锁监听
       // mainRadioGroup.setOnTouchListener(new OnTouchListenerAdapter(this, 0));
        mainRadioGroup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,UnlockActivity.class);
                startActivity(intent);
                return false;
            }
        });
        radioButtonNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,UnlockActivity.class);
                startActivity(intent);
                return false;
            }
        });
        radioButtonClock.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,UnlockActivity.class);
                startActivity(intent);
                return false;
            }
        });
        radioButtonSquare.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,UnlockActivity.class);
                startActivity(intent);
                return false;
            }
        });
        radioButtonMine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,UnlockActivity.class);
                startActivity(intent);
                return false;
            }
        });
        //首次启动默认显示笔记界面
        switchFragment(0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int position;
            switch (checkedId) {
                case R.id.radio_button_note:
                    position = 0;
                    break;
                case R.id.radio_button_alarm:
                    position = 1;
                    break;
                case R.id.radio_button_square:
                    position = 2;
                    break;
                case R.id.radio_button_mine:
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }
            //切换对应的界面
            switchFragment(position);
        }
    }


    public void switchFragment(int position) {
        //开启事务
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //遍历集合
        for (int i = 0; i < mainFragmentList.size(); i++) {
            Fragment fragment = mainFragmentList.get(i);
            if (i == position) {
                //显示fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,显示
                    fragmentTransaction.show(fragment);
                } else {
                    //如果这个fragment没有被事务添加过,添加
                    fragmentTransaction.add(R.id.main_frame_layout, fragment);
                }
            } else {
                //隐藏fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,隐藏
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        //提交事务
        fragmentTransaction.commit();
    }


    private void initFragment() {
        mainFragmentList = new ArrayList<>();
        mainFragmentList.add(new NoteFragment());
        mainFragmentList.add(new AlarmFragment());
        mainFragmentList.add(new SquareFragment());
        mainFragmentList.add(new MineFragment());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mainRadioGroup = findViewById(R.id.main_radio_group);
        radioButtonNote=(RadioButton)findViewById(R.id.radio_button_note);
        radioButtonClock=(RadioButton)findViewById(R.id.radio_button_alarm);
        radioButtonSquare=(RadioButton)findViewById(R.id.radio_button_square);
        radioButtonMine=(RadioButton)findViewById(R.id.radio_button_mine);
    }
    private void makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}