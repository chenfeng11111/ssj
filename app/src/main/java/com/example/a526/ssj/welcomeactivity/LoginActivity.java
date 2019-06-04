package com.example.a526.ssj.welcomeactivity;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.mainactivity.MainActivity;
import com.example.a526.ssj.util.FastBlurUtil;
import com.example.a526.ssj.util.LogUtil;



public class LoginActivity extends BaseActivity {

    private EditText account;
    private EditText password;
    private Button login;
    private ImageView account_line;
    private ImageView password_line;
    private TextView find_password;
    private TextView sign_up;
    private LinearLayout loginLayout;

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
        loginLayout = (LinearLayout)findViewById(R.id.login_layout);

        makeStatusBarTransparent();

        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.instructthree);

        //        scaledBitmap为目标图像，15是缩放的倍数（越大模糊效果越高）
        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 15);
        loginLayout.setBackground(new BitmapDrawable(blurBitmap));
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
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
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
                        loginUtil.login(account.getText().toString(), password.getText().toString(), data);
                        boolean successful = data.get("state").equals("success");

                                data.putString("successful", successful? "1" : "0");
                        // data.putString("successful", "1");  //实际使用的时候使用上一行代码
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                }
                ).start();
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
    //使用Handler更新主线程（UI线程)
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            //从data中拿出存的数据
            String val = data.getString("value");
            //将数据进行显示到界面等操作
            boolean successful = data.getString("successful").equals("1");
            System.out.println(successful);
            if (successful) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
//                GlobalVariable.setCurrentUserId(Integer.parseInt(data.getStringArrayList("user").get(0)));
                GlobalVariable.setCurrentUserId(1);
            } else {
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
            }
        }
    };
}
