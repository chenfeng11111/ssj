package com.example.a526.ssj.welcomeactivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.a526.ssj.R;

import java.util.ArrayList;

public class InstructActivity extends BaseActivity implements View.OnClickListener{

    private ViewPager vpGuiding;
    private GuidePagerAdapter guidePagerAdapter;
    private ArrayList<View> viewArrayList;
    private ImageView[] dots;
    private int currentIndex;
    private static final int[] pics = {R.layout.instruct_view_one,R.layout.instruct_view_two,
            R.layout.instruct_view_three};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruct);
        makeStatusBarTransparent();
        viewPagerInit();
    }

    public void viewPagerInit() {
        viewArrayList = new ArrayList<View>();
        LayoutInflater layoutInflater = LayoutInflater.from(InstructActivity.this);
        for(int i=0;i<pics.length;i++)
        {
            View view = layoutInflater.inflate(pics[i],null);
            if(i == pics.length-1)
            {
                TextView enter = (TextView)view.findViewById(R.id.instruct_enter);
                Typeface bottomTypeface = Typeface.createFromAsset(getAssets(),"Zeuty Demo.ttf");
                enter.setTypeface(Typeface.create(bottomTypeface,Typeface.BOLD));
                enter.setText("Enter");
                enter.setTag("enter");
                enter.setOnClickListener(this);
            }
            viewArrayList.add(view);
        }
        vpGuiding = (ViewPager)findViewById(R.id.vpGuiding);
        guidePagerAdapter = new GuidePagerAdapter(viewArrayList);
        vpGuiding.setAdapter(guidePagerAdapter);
        vpGuiding.addOnPageChangeListener(new PageChangeListener());

        initDots();
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

    private void initDots()
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        dots = new ImageView[pics.length];
        for(int i=0;i<pics.length;i++)
        {
            dots[i] = (ImageView)ll.getChildAt(i);
            dots[i].setEnabled(false);
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(true);
    }
    @Override
    public void onClick(View v)
    {
        if(v.getTag().equals("enter"))
        {
            enterMainActivity();
            return;
        }
        int position = (Integer)v.getTag();
        setCurView(position);
        setCurDot(position);
    }
    private void setCurView(int position)
    {
        if(position<0||position>=pics.length)
        {
            return;
        }
        vpGuiding.setCurrentItem(position);
    }
    private void setCurDot(int position)
    {
        if(position<0||position>=pics.length||currentIndex==position)
        {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }
    private void enterMainActivity()
    {
        Log.d("123123113","45646546565465456465456465");
        Intent intent = new Intent(InstructActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private class PageChangeListener implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrollStateChanged(int position)
        {

        }
        @Override
        public void onPageScrolled(int position,float arg1,int arg2)
        {

        }
        @Override
        public void onPageSelected(int position)
        {
            setCurDot(position);
        }
    }
}
