package com.example.a526.ssj.mainactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.a526.ssj.R;
import com.example.a526.ssj.entity.Note;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 仲夏丶我们一起 on 2019/5/29.
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.noteListViewHolder> implements View.OnClickListener,View.OnLongClickListener{
    private Context context;

    private List<Note> noteList;

    private boolean isshowBox = true;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    //接口实例
    private RecyclerViewOnItemClickListener onItemClickListener;

    NoteListAdapter(List<Note> noteList,Context context)
    {
        this.noteList = noteList;
        this.context = context;
    }

    //初始化map集合,默认为不选中
    private void initMap() {
        for (int i = 0; i < noteList.size(); i++) {
            map.put(i, false);
        }
    }

    @Override
    public noteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        noteListViewHolder vh = new noteListViewHolder(root);
        //为Item设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(noteListViewHolder holder, final int position) {
        holder.noteTitle.setText(noteList.get(position).getTitle());
        holder.noteContent.setText(noteList.get(position).getContent());
        //长按显示/隐藏
        if (isshowBox) {
            holder.checkbox.setVisibility(View.VISIBLE);
        } else {
            holder.checkbox.setVisibility(View.GONE);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.note_list_anim);
        //设置checkBox显示的动画
        if (isshowBox)
            holder.checkbox.startAnimation(animation);
        //设置Tag
        holder.rootView.setTag(position);
        //设置checkBox改变监听
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用map集合保存
                map.put(position, isChecked);
            }
        });
        // 设置CheckBox的状态
        if (map.get(position) == null) {
            map.put(position, false);
        }
        holder.checkbox.setChecked(map.get(position));
    }

    @Override
    public int getItemCount()
    {
        return noteList.size();
    }


    public void addData(int position,Note note)
    {
        noteList.add(position,note);
        notifyItemInserted(position);
    }

    public void removeData(int postion)
    {
        noteList.remove(postion);
        notifyItemRemoved(postion);
        notifyDataSetChanged();
    }

    class noteListViewHolder extends RecyclerView.ViewHolder
    {
        TextView noteContent;
        TextView noteTitle;
        View rootView;
        CheckBox checkbox;
        public noteListViewHolder(View view) {
            super(view);
            this.rootView = view;
            this.noteContent = (TextView)rootView.findViewById(R.id.note_content);
            this.noteTitle = (TextView)rootView.findViewById(R.id.note_title);
            this.checkbox = (CheckBox)rootView.findViewById(R.id.checkbox);
        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onItemClickListener.onItemClickListener(v, (Integer) v.getTag());
        }
    }

    //长按事件
    @Override
    public boolean onLongClick(View v) {
        //不管显示隐藏，清空状态
        initMap();
        return onItemClickListener != null && onItemClickListener.onItemLongClickListener(v, (Integer) v.getTag());
    }

    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        isshowBox = !isshowBox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }

    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }
}
