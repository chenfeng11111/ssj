package com.example.a526.ssj.mainactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
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

public class SquareFragment extends Fragment {

    private RecyclerView noteListView;
    private ArrayList<Note> noteList;
    public SquareFragment() {
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
        View rootView = localInflater.inflate(R.layout.square_fragment, container, false);
        noteListView = rootView.findViewById(R.id.square_list);
        setListener();
        return rootView;
    }
    private void setListener() {

    }
}