package com.example.a526.ssj.mainactivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a526.ssj.R;
import com.example.a526.ssj.createactivity.CreateNoteActivity;
import com.example.a526.ssj.entity.Note;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 10902 on 2019/5/28.
 */

public class NoteFragment extends Fragment implements View.OnClickListener{
    private ImageView plus;
    private RecyclerView noteListView;
    private ArrayList<Note> noteList;
    private NoteListAdapter adapter;
    private LinearLayout menu;
    private Button note_del;
    private Button note_update;
    private Button note_share;
    private Button note_sync;
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
        plus=(ImageView) rootView.findViewById(R.id.note_plus);
        noteListView = (RecyclerView) rootView.findViewById(R.id.note_list);
        menu = (LinearLayout)rootView.findViewById(R.id.menu_layout);
        note_del = (Button) rootView.findViewById(R.id.menu_del);
        note_update = (Button)rootView.findViewById(R.id.menu_update);
        note_share = (Button)rootView.findViewById(R.id.menu_share);
        note_sync = (Button)rootView.findViewById(R.id.menu_sync);
        initData();
        //布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        noteListView.setLayoutManager(manager);
        noteListView.setHasFixedSize(true);
        adapter = new NoteListAdapter(noteList,getContext());
        noteListView.setAdapter(adapter);
        //添加分割线
        noteListView.addItemDecoration(new NoteListItemDecoration(getContext(), NoteListItemDecoration.VERTICAL_LIST));
        adapter.setRecyclerViewOnItemClickListener(new NoteListAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                //设置选中的项
                adapter.setSelectItem(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                adapter.setShowBox();
                //设置选中的项
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
                menu.setVisibility(View.VISIBLE);
                return true;
            }
        });
        note_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.GONE);
                Map<Integer,Boolean> selectState = adapter.getMap();
                for(int i=0;i<selectState.size();i++) {
                    if (selectState.get(i))
                    {
                        adapter.removeData(i);
                    }
                }
            }
        });

        note_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.GONE);
            }
        });

        note_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.GONE);
            }
        });

        note_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.GONE);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
                intent.putExtra("operation","create");
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        //获取你选中的item
        Map<Integer, Boolean> map = adapter.getMap();
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)) {
                Log.d("TAG", "你选了第：" + i + "项");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.note_list_menu, menu);
        super.onCreateOptionsMenu(menu,menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //全选
            case R.id.all:
                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, true);
                    adapter.notifyDataSetChanged();
                }
                break;
            //全不选
            case R.id.no_all:
                Map<Integer, Boolean> m = adapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    m.put(i, false);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 为列表添加测试数据
     */
    private void initData() {
        noteList=new ArrayList<>();
        for (int i=0;i<100;i++) {
            //noteList.add("当前条目是"+i);
        }
    }


}