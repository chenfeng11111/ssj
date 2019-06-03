package com.example.a526.ssj.mainactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a526.ssj.R;

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
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String original_psw = et_original_psw.getText().toString().trim();
                String new_psw = et_new_psw.getText().toString().trim();
                String new_psw_again = et_new_psw_again.getText().toString().trim();
                if(!new_psw.equals(new_psw_again)){
                    Toast.makeText(getApplicationContext(),"两次输入密码不同，请重新输入！",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
