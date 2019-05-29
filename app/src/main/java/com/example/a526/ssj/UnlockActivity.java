package com.example.a526.ssj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a526.ssj.mainactivity.MainActivity;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

import java.io.FileInputStream;
import java.util.Properties;

public class UnlockActivity extends AppCompatActivity {
    private GestureLockView mGestureLockView;
    private TextView textTip;
    private Button btnForget;
    private String configFile = "lockconfig.properties";
    private int curTryCount =1;
    private int maxTryCount = 5;
    private String password = "012345678";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        //从xml视图中获取GestureLockView
        mGestureLockView = (GestureLockView)findViewById(R.id.glv_lock);
        //获取提示框
        textTip = (TextView) findViewById(R.id.text_unlock_tip);
         btnForget= (Button)findViewById(R.id.btn_unlock_forget);
        //每次创建时，读取配置文件
        readConfig();
        //设置手势锁监听器
        mGestureLockView.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {
            }
            @Override
            public void onProgress(String progress) {}

            @Override
            public void onComplete(String result) {
             //   Toast.makeText(UnlockActivity.this,result,Toast.LENGTH_SHORT).show();
                //没有点击
                if (TextUtils.isEmpty(result)){
                    return;
                }
                //密码正确
                if(password.equalsIgnoreCase(result)){
                    Toast.makeText(UnlockActivity.this,"解锁成功",Toast.LENGTH_SHORT).show();
                    //结束当前Activity 返回到上一页面
                    /*********************
                     */
                    finish();
                }
                //密码错误
                else{
                    //如果尝试次数到达最大值，返回登录页面
                    if(curTryCount==maxTryCount){
                        //返回到登录页面
                        /******
                         *
                         *
                         * *******/
                        Intent intent = new Intent(UnlockActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(UnlockActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                        String count = String.valueOf(maxTryCount-curTryCount);
                        textTip.setText("密码错误，剩余解锁机会"+count+"次");
                        curTryCount ++;
                        //显示600毫秒错误状态后还原至初始状态
                        mGestureLockView.showErrorStatus(800);
                    }
                }
            }
        });
        //设置忘记手势锁监听
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到登录页面
                /*
                Intent intent = new Intent();
                startActivity(intent);
                */
            }
        });
    }
    //禁用返回键退出锁屏界面
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
    private void readConfig(){
        String[] fileList = this.fileList();
        boolean hasFile = false;
        for(String fileName:fileList){
            if(fileName.equalsIgnoreCase(configFile)){
                hasFile = true;
                break;
            }
        }
        //配置文件已经存在
        if (hasFile){
            try {
                Properties pro =new Properties();
                FileInputStream inputStream=this.openFileInput(configFile);
                pro.load(inputStream);
                maxTryCount = Integer.parseInt(pro.getProperty("lock.maxtrycount"));
                password = pro.getProperty("lock.password");
                Log.d(String.valueOf(maxTryCount),password);
                inputStream.close();
            } catch (Exception e) {
                Log.d(e.getMessage(),"");
            }
        }
    }
}