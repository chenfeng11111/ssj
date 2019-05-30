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

public class clockListAdapter extends RecyclerView.Adapter<clockListAdapter.clockListViewHolder>{
    private Context context;
    private List<String> clockList;

    public clockListAdapter(Context context,List<String> clockList)
    {
        this.context = context;
        this.clockList = clockList;
    }
    @Override
    public clockListViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        clockListViewHolder holder = new clockListViewHolder(LayoutInflater.from(context).inflate(R.layout.clock_list_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(clockListViewHolder holder,final int position)
    {
        holder.itemView.apply();
        holder.clockText.setText(clockList.get(position));
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
        return clockList.size();
    }

    public void addData(int position,String clockText)
    {
        clockList.add(position,clockText);
        notifyItemInserted(position);
    }

    public void removeData(int postion)
    {
        clockList.remove(postion);
        notifyItemRemoved(postion);
        notifyDataSetChanged();
    }
    class clockListViewHolder extends RecyclerView.ViewHolder
    {
        TextView clockText;
        TextView delItem;
        clockListItem itemView;
        public clockListViewHolder(View view) {
            super(view);
            clockText = (TextView)view.findViewById(R.id.clock_text);
            delItem = (TextView)view.findViewById(R.id.del_item);
            itemView = (clockListItem)view.findViewById(R.id.clock_list_item);
        }
    }
}
