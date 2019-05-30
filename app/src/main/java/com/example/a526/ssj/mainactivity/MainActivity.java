package com.example.a526.ssj.mainactivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.a526.ssj.R;
import com.example.a526.ssj.listener.OnTouchListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements AlarmFragment.OnFragmentInteractionListener {
    private RadioGroup mainRadioGroup;//主界面的radio group
    private List<Fragment> mainFragmentList; //主界面的fragment集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
        //设置控件在最上层
        mainRadioGroup.bringToFront();
    }

    private void setListener() {
        mainRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //添加手势锁监听
        mainRadioGroup.setOnTouchListener(new OnTouchListenerAdapter(this, 0));
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
            //替换到Fragment
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
    }

}