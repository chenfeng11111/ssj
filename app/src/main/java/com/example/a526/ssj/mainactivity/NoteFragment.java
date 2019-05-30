package com.example.a526.ssj.mainactivity;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.Note;

import java.util.ArrayList;

/**
 * Created by 10902 on 2019/5/28.
 */

public class NoteFragment extends Fragment {
    private TextView edit;
    private ImageView plus;
    private RecyclerView noteListView;
    private ArrayList<Note> noteList;
    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.clockTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View rootView = localInflater.inflate(R.layout.note_fragment, container, false);
        edit=rootView.findViewById(R.id.note_edit);
        plus=rootView.findViewById(R.id.note_plus);
        noteListView = rootView.findViewById(R.id.note_list);
        setListener();
        return rootView;
    }
    private void setListener() {
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到创建笔记界面
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到创建笔记界面
            }
        });
    }
}