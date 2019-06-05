package com.example.a526.ssj.mainactivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.example.a526.ssj.createactivity.CreateNoteActivity;
import com.example.a526.ssj.database.NoteDatabaseHolder;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.entity.Note;
import com.example.a526.ssj.notehelper.NoteHelper;
import com.example.a526.ssj.notehelper.NoteUtils;
import com.example.a526.ssj.util.UploadUtil;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 10902 on 2019/5/28.
 */

public class NoteFragment extends Fragment implements View.OnClickListener{
    private ImageView plus;
    private ImageView note_sync;
    private RecyclerView noteListView;
    private List<Note> noteList=new ArrayList<Note>();
    private NoteListAdapter adapter;
    private LinearLayout menu;
    private Button note_del;
    private Button note_update;
    private Button note_share;
    private Button note_edit;
    private NoteDatabaseHolder databaseHolder;
    private boolean started = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            //从data中拿出存的数据
            String val = data.getString("value");
            //将数据进行显示到界面等操作
            String successful = data.getString("message");
            Toast.makeText(getActivity(),successful,Toast.LENGTH_LONG).show();
            noteList.clear();
            noteList.addAll(GlobalVariable.getNoteDatabaseHolder().searchNote(0,0));
            for (int i = 0; i < noteList.size(); i++) {
                if(!(adapter.getMap().containsKey(i)))
                {
                    adapter.getMap().put(i,false);
                }

            }
            System.out.println("handle 同步方法返回:"+noteList);
            adapter.notifyDataSetChanged();
        }
    };
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
        started = true;
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.clockTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View rootView = localInflater.inflate(R.layout.note_fragment, container, false);
        plus=(ImageView) rootView.findViewById(R.id.note_plus);
        note_sync = (ImageView) rootView.findViewById(R.id.note_sync);
        noteListView = (RecyclerView) rootView.findViewById(R.id.note_list);
        menu = (LinearLayout)rootView.findViewById(R.id.menu_layout);
        note_del = (Button) rootView.findViewById(R.id.menu_del);
        note_update = (Button)rootView.findViewById(R.id.menu_update);
        note_share = (Button)rootView.findViewById(R.id.menu_share);
        note_edit = (Button)rootView.findViewById(R.id.menu_edit);
        databaseHolder = new NoteDatabaseHolder(getContext());
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
                adapter.setisshowBox(false);
                adapter.notifyDataSetChanged();
                System.out.println("删除文件");
                Map<Integer,Boolean> selectState = adapter.getMap();
                ArrayList<Integer> noteid = new ArrayList<>();
                for(int i:selectState.keySet())
                {
                    if(selectState.get(i))
                    {
                        noteid.add(noteList.get(i).getId());
                    }
                }

                for(int i=0;i<noteid.size();i++)
                {
                    int n = noteList.size();
                    for(int j=0;j<n;j++)
                    {
                        Note note=noteList.get(j);
                        System.out.println(note);
                        if(note.getId()==noteid.get(i))
                        {
                            System.out.println("删除笔记");
                            NoteUtils.deleteFile(getContext(),note.getTitle());
                            adapter.removeData(j);
                            break;
                        }
                    }
                    Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

        note_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("......................update");
                menu.setVisibility(View.GONE);
                adapter.setisshowBox(false);
                adapter.notifyDataSetChanged();
                Map<Integer,Boolean> selectState = adapter.getMap();
                for(int i=0;i<selectState.size();i++) {
                    if (selectState.get(i))
                    {
                        final Note note = noteList.get(i);
                        note.setUpload(true);
                        //调用上传接口
                        new Thread(new Runnable(){
                            @Override
                            public void run(){
                                //进行访问网络操作
                                Message msg = Message.obtain();
                                Bundle data = new Bundle();
                                String successful = NoteHelper.uploadFileToServer(note,0);
//                         data.putString("successful", successful? "1" : "0");
                                data.putString("message", successful);  //实际使用的时候使用上一行代码
                                msg.setData(data);
                                handler.sendMessage(msg);
                            }
                        }
                        ).start();
                    }
                }
            }
        });
        note_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.GONE);
                adapter.setisshowBox(false);
                adapter.notifyDataSetChanged();
                Map<Integer,Boolean> selectState = adapter.getMap();
                for(int i=0;i<selectState.size();i++) {
                if (selectState.get(i))
                {
                    final Note note = noteList.get(i);
                    if(!note.getShare()){
                        //首先进行上传
                    }
                    note.setShare(true);
                    //调用上传接口
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //进行访问网络操作
                            Message msg = Message.obtain();
                            Bundle data = new Bundle();
                            String successful= NoteHelper.uploadFileToServer(note, 1);
//                         data.putString("successful", successful? "1" : "0");
                            data.putString("message", successful);  //实际使用的时候使用上一行代码
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }
                    }
                    ).start();
                }
            }
        }
        });

        note_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setVisibility(View.GONE);
                adapter.setisshowBox(false);
                adapter.notifyDataSetChanged();
                Map<Integer,Boolean> selectState = adapter.getMap();
                for(int i=0;i<selectState.size();i++) {
                    if (selectState.get(i))
                    {
                        //获取当前note
                        Note note = noteList.get(i);
                        //跳转到编辑页面
                        Intent intent=new Intent(getActivity(),CreateNoteActivity.class);
                        intent.putExtra("operation","edit");
                        intent.putExtra("note",note);
                        startActivity(intent);
                        break;
                    }
                }
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
        note_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("同步点击监听");
                //同步方法 调用同步接口
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        //进行访问网络操作
                        Message msg = Message.obtain();
                        Bundle data = new Bundle();
                        String successful = NoteHelper.syncFileToLocal();
//                         data.putString("successful", successful? "1" : "0");
                        data.putString("message", successful);  //实际使用的时候使用上一行代码
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                }
                ).start();
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
    }



    /**
     * 为列表添加测试数据
     */
    private void initData() {
        noteList = GlobalVariable.getNoteDatabaseHolder().searchNote(0,0);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(started)
        {
            noteList.clear();
            noteList.addAll(GlobalVariable.getNoteDatabaseHolder().searchNote(0,0));
            for (int i = 0; i < noteList.size(); i++) {
                if(!(adapter.getMap().containsKey(i)))
                {
                    adapter.getMap().put(i,false);
                }

            }
            adapter.notifyDataSetChanged();
        }
        //Log.d("121211211","onresume");

    }

    @Override
    public void onStart()
    {
        super.onStart();
        //Log.d("121211211","onstart");
    }
}