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
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.mainactivity.MainActivity;
import com.example.a526.ssj.util.FastBlurUtil;
import com.example.a526.ssj.util.LogUtil;

/**
 * Created by 10902 on 2019/6/4.
 */

public class RegisterActivity extends BaseActivity {
    private EditText account;
    private EditText password;
    private EditText confirmPassword;
    private EditText name;
    private Button register;
    private ImageView accountLine;
    private ImageView passwordLine;
    private ImageView confirmPasswordLine;
    private ImageView nameLine;
    private LinearLayout register_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = (EditText) findViewById(R.id.register_account);
        password = (EditText) findViewById(R.id.register_password);
        register = (Button) findViewById(R.id.register);
        name = findViewById(R.id.register_name);
        accountLine = (ImageView) findViewById(R.id.register_account_line);
        passwordLine = (ImageView) findViewById(R.id.register_password_line);
        confirmPassword = findViewById(R.id.register_confirm);
        confirmPasswordLine = findViewById(R.id.register_confirm_line);
        nameLine = findViewById(R.id.register_name_line);
        register_layout = (LinearLayout)findViewById(R.id.register_layout);

        makeStatusBarTransparent();
        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.instructthree);

        //        scaledBitmap为目标图像，15是缩放的倍数（越大模糊效果越高）
        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 15);
        register_layout.setBackground(new BitmapDrawable(blurBitmap));

        account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    accountLine.setEnabled(true);
                    passwordLine.setEnabled(false);
                    confirmPasswordLine.setEnabled(false);
                    nameLine.setEnabled(false);
                } else {
                    accountLine.setEnabled(false);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    passwordLine.setEnabled(true);
                    accountLine.setEnabled(false);
                    confirmPasswordLine.setEnabled(false);
                    nameLine.setEnabled(false);
                } else {
                    passwordLine.setEnabled(false);
                }
            }
        });
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    confirmPasswordLine.setEnabled(true);
                    passwordLine.setEnabled(false);
                    accountLine.setEnabled(false);
                    nameLine.setEnabled(false);
                } else {
                    passwordLine.setEnabled(false);
                }
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    confirmPasswordLine.setEnabled(false);
                    passwordLine.setEnabled(false);
                    accountLine.setEnabled(false);
                    nameLine.setEnabled(true);
                } else {
                    nameLine.setEnabled(false);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LogUtil loginUtil = new LogUtil();
                if(password.getText().toString().equals(confirmPassword.getText().toString())){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //进行访问网络操作
                            Message msg = Message.obtain();
                            Bundle data = new Bundle();
                            boolean successful = loginUtil.register(name.getText().toString(), account.getText().toString(), password.getText().toString(), data);
                            data.putString("successful", successful ? "1" : "0");
                            data.putString("successful", "1");  //实际使用的时候使用上一行代码
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }
                    }
                    ).start();
                }else{
                    Toast.makeText(RegisterActivity.this, "输入的密码不一致", Toast.LENGTH_LONG).show();
                }
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            //从data中拿出存的数据
            String val = data.getString("value");
            //将数据进行显示到界面等操作
            boolean successful = data.getString("successful").equals("1");
            if (successful) {
                finish();
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
            }
        }
    };
}
