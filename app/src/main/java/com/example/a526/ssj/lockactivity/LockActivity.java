package com.example.a526.ssj.lockactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

import java.io.FileOutputStream;
import java.util.Properties;

public class LockActivity extends AppCompatActivity {

    private GestureLockView mGestureLockView;
    private String configFile = "lockconfig.properties";
    private boolean isFirstInput = true;
    private String firstPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);


        //获取解锁面板
        mGestureLockView = (mGestureLockView)=findViewById(R.id.glv_lock);
        //设置监听
        mGestureLockView.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onProgress(String progress) {

            }
            //监听完成方法
            @Override
            public void onComplete(String result) {
                if(TextUtils.isEmpty(result)){
                    return;
                }
                else{
                    //第一次输入密码
                   // Toast.makeText(LockActivity.this,result,Toast.LENGTH_SHORT).show();
                    if(isFirstInput){
                        firstPassword=result;
                       // Toast.makeText(LockActivity.this,"第一次密码"+firstPassword,Toast.LENGTH_SHORT).show();
                        isFirstInput=false;
                        Toast.makeText(LockActivity.this,"请再次确认手势密码",Toast.LENGTH_SHORT).show();
                        //恢复原状态
                        //显示600毫秒错误状态后还原至初始状态
                        mGestureLockView.showErrorStatus(800);
                    }
                    //第二次输入密码
                    else{
                        isFirstInput=true;
                        //如果第二次输入和第一次输入不同
                        if(!result.equalsIgnoreCase(firstPassword)){
                            Toast.makeText(LockActivity.this,"两次输入密码不一致，请重新设置!",Toast.LENGTH_LONG).show();
                            firstPassword="";
                        }
                        //两次输入密码相同
                        else{
                            Toast.makeText(LockActivity.this,"设置成功！",Toast.LENGTH_SHORT).show();
                            //修改配置文件设置
                            wirteConfigration(firstPassword);
                            firstPassword="";
                            //此处应该返回到设置页面
                            /******************************
                             * **************************
                             *
                             */
                            finish();

                        }
                    }
                }
            }
        });
    }
    private void wirteConfigration(String password){
        Log.d("Writing...","");
        try {
            Properties pro =new Properties();
            pro.setProperty("lock.maxtrycount","5");
            pro.setProperty("lock.password",password);
            //打开文件 写入
            FileOutputStream outputStream = this.openFileOutput(configFile,MODE_PRIVATE);
            pro.store(outputStream,"store lock config");
            outputStream.close();
        } catch (Exception e) {
            Log.d(e.toString(),"");
        }
    }
}
