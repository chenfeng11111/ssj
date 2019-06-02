package com.example.a526.ssj.mainactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a526.ssj.R;

import java.util.List;

/**
 * Created by 仲夏丶我们一起 on 2019/5/29.
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.noteListViewHolder>{
    private Context context;
    private List<String> noteList;

    public NoteListAdapter(Context context, List<String> noteList)
    {
        this.context = context;
        this.noteList = noteList;
    }
    @Override
    public noteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        noteListViewHolder holder = new noteListViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(noteListViewHolder holder, final int position)
    {
        holder.itemView.apply();
        holder.noteText.setText(noteList.get(position));
        holder.delItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return noteList.size();
    }

    public void addData(int position,String clockText)
    {
        noteList.add(position,clockText);
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
        TextView noteText;
        TextView delItem;
        NoteListItem itemView;
        public noteListViewHolder(View view) {
            super(view);
            noteText = (TextView)view.findViewById(R.id.note_text);
            delItem = (TextView)view.findViewById(R.id.note_del_item);
            itemView = (NoteListItem) view.findViewById(R.id.note_list_item);
        }
    }
}
