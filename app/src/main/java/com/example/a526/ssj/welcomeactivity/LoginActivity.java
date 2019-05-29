package com.example.a526.ssj.welcomeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a526.ssj.R;
import com.example.a526.ssj.mainactivity.MainActivity;


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
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
