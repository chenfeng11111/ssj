package com.example.a526.ssj.mainactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a526.ssj.R;

public class passwordchangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordchange_layout);
        final EditText et_original_psw = (EditText) findViewById(R.id.et_original_psw);
        final EditText et_new_psw = (EditText)findViewById(R.id.et_new_psw);
        final EditText et_new_psw_again = (EditText)findViewById(R.id.et_new_psw_again);
        Button btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String original_psw = et_original_psw.getText().toString();
                String new_psw = et_new_psw.getText().toString();
                String new_psw_again = et_new_psw_again.toString();
            }
        });
    }
}
