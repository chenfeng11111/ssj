package com.example.a526.ssj.welcomeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.Clock;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.entity.Note;
import com.example.a526.ssj.mainactivity.MainActivity;
import com.example.a526.ssj.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class LoginActivity extends BaseActivity {

    private EditText account;
    private EditText password;
    private Button login;
    private ImageView account_line;
    private ImageView password_line;
    private TextView find_password;
    private TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        account_line = (ImageView) findViewById(R.id.account_line);
        password_line = (ImageView) findViewById(R.id.password_line);
        sign_up = (TextView) findViewById(R.id.sign_up);
        find_password = (TextView) findViewById(R.id.find_password);
        account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    account_line.setEnabled(true);
                    password_line.setEnabled(false);
                } else {
                    account_line.setEnabled(false);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    password_line.setEnabled(true);
                    account_line.setEnabled(false);
                } else {
                    password_line.setEnabled(false);
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LogUtil loginUtil = new LogUtil();

                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        //进行访问网络操作
                        Message msg = Message.obtain();
                        Bundle data = new Bundle();
                        boolean successful = loginUtil.login(account.getText().toString(), password.getText().toString(), data);
                        data.putString("successful", successful? "1" : "0");
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                }
                ).start();
            }
        });
    }

    //使用Handler更新主线程（UI线程)
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            //从data中拿出存的数据
            String val = data.getString("value");
            //将数据进行显示到界面等操作
            boolean successful = data.getString("successful").equals("1");
            successful = true;  // 实际使用的时候删除
            if (successful) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                GlobalVariable.setCurrentUserId(Integer.parseInt(data.getStringArrayList("user").get(0)));
            } else {
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
            }
        }
    };
}
