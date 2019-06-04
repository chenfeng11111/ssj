package com.example.a526.ssj.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a526.ssj.R;
import com.example.a526.ssj.createactivity.WebDataActivity;
import com.example.a526.ssj.entity.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 仲夏丶我们一起 on 2019/6/4.
 */

public class SqureListAdapter extends BaseAdapter {
    private List<Note> data; // = new ArrayList<>();
    private Context context;
    private TextView squre_title;
    private TextView squre_content;
    SqureListAdapter(List<Note> data ,Context context)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();//数目
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView==null) {
            //因为getView()返回的对象，adapter会自动赋给ListView
            view = LayoutInflater.from (context).inflate(R.layout.squre_item,parent,false);
        }else{
            view=convertView;
            Log.i("info","有缓存，不需要重新生成"+position);
        }
        squre_title = (TextView) view.findViewById(R.id.squre_title);//找到Textviewname
        squre_title.setText(data.get(position).getTitle());//设置参数

        squre_content = (TextView) view.findViewById(R.id.squre_content);//找到Textviewage
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        squre_content.setText(dateFormat.format(data.get(position).getSaveTime()));//设置参数
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = data.get(position);
                Intent intent = new Intent(context, WebDataActivity.class);
                intent.putExtra("html",note.getContent());
                intent.putExtra("preview","false");
                intent.putExtra("file",note.getTitle());
                context.startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public long getItemId(int position) {//取在列表中与指定索引对应的行id
        return 0;
    }
    @Override
    public Object getItem(int position) {//获取数据集中与指定索引对应的数据项
        return null;
    }
}
