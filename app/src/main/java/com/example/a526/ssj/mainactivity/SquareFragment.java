package com.example.a526.ssj.mainactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a526.ssj.R;
import com.example.a526.ssj.createactivity.CreateNoteActivity;
import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.entity.Note;
import com.example.a526.ssj.notehelper.NoteHelper;
import com.example.a526.ssj.util.UploadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by 10902 on 2019/5/28.
 */

public class SquareFragment extends Fragment {

    private PtrFrameLayout ptrFrameLayout;
    private ListView listView;
    private ProgressBar progressBar;
    private ImageButton btnBack;
    private List<Note> data;
    private SqureListAdapter adapter;
    private StoreHouseHeader storeHouseHeader;
    private MaterialHeader materialHeader;
    private PtrClassicDefaultHeader ptrClassicDefaultHeader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.square_fragment, container, false);
        //noteListView = rootView.findViewById(R.id.square_list);
        ptrFrameLayout = ((PtrFrameLayout) rootView.findViewById(R.id.ptrFrameLayout));
        listView = ((ListView) rootView.findViewById(R.id.listView));
        progressBar = ((ProgressBar) rootView.findViewById(R.id.progressBar));
        btnBack = ((ImageButton) rootView.findViewById(R.id.btnBack));
        progressBar.setVisibility(View.GONE);
        initData();
        initPtrRefresh();
        initBtnBack();
        return rootView;
    }
    private void initPtrRefresh() {
        //第3.1步：
        initHeaders();

//        ptrFrameLayout.setHeaderView(ptrClassicDefaultHeader);
//        ptrFrameLayout.addPtrUIHandler(ptrClassicDefaultHeader);

//        ptrFrameLayout.setHeaderView(storeHouseHeader);
//        ptrFrameLayout.addPtrUIHandler(storeHouseHeader);

        ptrFrameLayout.setHeaderView(storeHouseHeader);//类似SwipeRefreshLayout
        ptrFrameLayout.addPtrUIHandler(storeHouseHeader);


        ptrFrameLayout.setPtrHandler(new PtrHandler() {

            //检查是否可以刷新
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            //开始刷新
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //第3.2步：
                        updateData(true);
                    }
                }, 3000);
            }
        });
    }

    //第3.1步：
    private void initHeaders() {

        /**
         * //第一种头部
         * StoreHouse风格的头部实现
         */
        storeHouseHeader = new StoreHouseHeader(getContext());
        storeHouseHeader.setBackgroundColor(Color.BLACK);
        storeHouseHeader.setTextColor(Color.WHITE);
        storeHouseHeader.setLineWidth(5);
        storeHouseHeader.initWithString("Updating");    //只可英文，中文不可运行(添加时间)
//"last update @" + new SimpleDateFormat("yyyy-MM-dd HH：mm:ss").format(new Date())

        /**
         * //第二种头部
         * Material Design风格的头部实现
         */
        materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(new int[]{Color.RED, Color.GREEN, Color.BLUE});//类似SwipeRefreshLayout


        /**
         * //第三种头部
         * 经典 风格的头部实现
         */
        ptrClassicDefaultHeader = new PtrClassicDefaultHeader(getContext());
    }

    //第3.2步：
    private void updateData(boolean addToTop) {
        data = GlobalVariable.getNoteDatabaseHolder().searchNote(0,0);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        ptrFrameLayout.refreshComplete();
    }

    //第四步
    private void initBtnBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listView.setSelection(0);
                listView.smoothScrollToPosition(0);
            }
        });
    }


    //第二步：
    private void initData() {
        new Thread(new Runnable(){
            @Override
            public void run(){
                //进行访问网络操作
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                Log.d("diaoyong square", "doing--------------------");
                ArrayList<Note> remoteNotes = UploadUtil.square();
//                         data.putString("successful", successful? "1" : "0");
                bundle.putSerializable("result", remoteNotes);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }
        ).start();

//        if(data == null)
//        {
//            Log.d("data:", "data is null-----------------------------");
//        }
//        adapter = new SqureListAdapter(data, getContext());
//        listView.setAdapter(adapter);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            //从data中拿出存的数据
            data = (ArrayList<Note>) bundle.getSerializable("result");
        }
    };
}