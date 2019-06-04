package com.example.a526.ssj.mainactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.entity.User;
import com.example.a526.ssj.util.LogUtil;
import com.example.a526.ssj.util.UploadUtil;
import com.example.a526.ssj.welcomeactivity.LoginActivity;

public class myinformationActivity extends AppCompatActivity {

    private TextView name;
    private TextView gender;
    private TextView email;
    private TextView quit;
    private User user;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            //从data中拿出存的数据
            user =(User) data.getSerializable("user");
            //将数据进行显示到界面等操作
            if(user == null)
            {
                Toast.makeText(getApplicationContext(),"获取用户信息失败",Toast.LENGTH_SHORT).show();
            }
            else
            {
                name.setText(user.getName());
                email.setText(user.getEmail());
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_layout);


        name = (TextView)findViewById(R.id.name);
        gender = (TextView)findViewById(R.id.gender);
        email = (TextView)findViewById(R.id.txt_mine_email);
        quit = (TextView)findViewById(R.id.btn_myifo_quit);

        //获取用户信息
        new Thread(new Runnable(){
            @Override
            public void run(){
                //进行访问网络操作
                Message msg = Message.obtain();
                Bundle data = new Bundle();
                User user = new LogUtil().findUserById(GlobalVariable.getCurrentUserId(),data);
                data.putSerializable("user",user);
                // data.putString("successful", "1");  //实际使用的时候使用上一行代码
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }
        ).start();

        makeStatusBarTransparent();
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myinformationActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
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
