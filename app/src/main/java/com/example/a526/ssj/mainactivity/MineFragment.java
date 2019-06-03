package com.example.a526.ssj.mainactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a526.ssj.R;

/**
 * Created by 10902 on 2019/5/28.
 */

public class MineFragment extends Fragment {

    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.mine_fragment, container, false);
        TextView txtViewMy = (TextView)rootView.findViewById(R.id.myinformation);
        TextView txtViewchange = (TextView)rootView.findViewById(R.id.psdchange);
        txtViewMy.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent();
                //intent.setClass(getContext(),myinformationActiqvity.class);
                startActivity(intent);
            }
        });
        txtViewchange.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent();
                //intent.setClass(getContext(),passwordchangeActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}