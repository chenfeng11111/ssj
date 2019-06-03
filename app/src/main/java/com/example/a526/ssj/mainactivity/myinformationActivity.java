package com.example.a526.ssj.mainactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a526.ssj.R;

public class myinformationActivity extends AppCompatActivity {

    private TextView name;
    private TextView gender;
    private TextView birth;
    private TextView quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_layout);
        name = (TextView)findViewById(R.id.name);
        gender = (TextView)findViewById(R.id.gender);
        birth = (TextView)findViewById(R.id.birth);
        quit = (TextView)findViewById(R.id.quit);
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

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
