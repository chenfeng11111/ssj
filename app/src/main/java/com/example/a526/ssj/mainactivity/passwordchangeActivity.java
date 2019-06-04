package com.example.a526.ssj.mainactivity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.util.LogUtil;

public class passwordchangeActivity extends AppCompatActivity {
    private EditText et_original_psw;
    private EditText et_new_psw;
    private EditText et_new_psw_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordchange_layout);
        et_original_psw = (EditText) findViewById(R.id.et_original_psw);
        et_new_psw = (EditText)findViewById(R.id.et_new_psw);
        et_new_psw_again = (EditText)findViewById(R.id.et_new_psw_again);
        Button btn_save = (Button)findViewById(R.id.btn_save);

        makeStatusBarTransparent();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String original_psw = et_original_psw.getText().toString().trim();
                String new_psw = et_new_psw.getText().toString().trim();
                String new_psw_again = et_new_psw_again.getText().toString().trim();
                if(!new_psw.equals(new_psw_again)){
                    Toast.makeText(getApplicationContext(),"两次输入密码不同，请重新输入！",Toast.LENGTH_LONG).show();
                }
                else{
                    Bundle bundle = new Bundle();
                    new LogUtil().updataPassword(GlobalVariable.getCurrentUserId(),original_psw,new_psw,bundle);
                    if(bundle.getString("state").equalsIgnoreCase("success")){
                        Toast.makeText(passwordchangeActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(passwordchangeActivity.this,bundle.getString("message"),Toast.LENGTH_SHORT).show();
                    }
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
}
