package com.example.a526.ssj.welcomeactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.a526.ssj.R;


/**
 * Created by 仲夏丶我们一起 on 2019/5/28.
 */

public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.ViewHolder> {
    private int imgWidth;
    private Context context;
    public SplashAdapter(Context context)
    {
        this.imgWidth = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_recyclerview_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position)
    {

    }
    @Override
    public int getItemCount()
    {
        return Integer.MAX_VALUE;
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public ViewHolder(final View view)
        {
            super(view);
        }
    }
}
