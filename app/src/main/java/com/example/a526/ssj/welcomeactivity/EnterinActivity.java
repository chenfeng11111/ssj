package com.example.a526.ssj.welcomeactivity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.a526.ssj.R;


public class EnterinActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    TextView top;
    TextView bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enterin);
        makeStatusBarTransparent();
        top=(TextView)findViewById(R.id.top_text);
        bottom=(TextView)findViewById(R.id.bottom_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.imageRecyclerView);
        Typeface topTypeface = Typeface.createFromAsset(getAssets(),"Yahoo.ttf");
        top.setTypeface(Typeface.create(topTypeface,Typeface.BOLD));
        top.setText("With Notes");
        Typeface bottomTypeface = Typeface.createFromAsset(getAssets(),"Zeuty Demo.ttf");
        bottom.setTypeface(Typeface.create(bottomTypeface,Typeface.BOLD));
        bottom.setText("Enter In");
        mRecyclerView.setAdapter(new SplashAdapter(EnterinActivity.this));
        mRecyclerView.setLayoutManager(new ScrollLinearLayoutManger(EnterinActivity.this));
        mRecyclerView.smoothScrollToPosition(Integer.MAX_VALUE/2);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterinActivity.this,InstructActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
