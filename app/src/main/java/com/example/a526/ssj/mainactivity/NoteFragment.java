package com.example.a526.ssj.mainactivity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a526.ssj.R;

import org.w3c.dom.Text;

/**
 * Created by 10902 on 2019/5/28.
 */

public class NoteFragment extends Fragment {

    private Button noteFragmentButton;
    TextView noteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, container, false);
        noteFragmentButton = (Button) view.findViewById(R.id.id_fragment_one_btn);
        noteTextView = (TextView) view.findViewById(R.id.note_fragment_textview);
        noteFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteTextView.setText("submitted");
            }
        });
        return view;
    }



}