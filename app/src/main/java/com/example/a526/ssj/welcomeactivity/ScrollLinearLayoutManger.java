package com.example.a526.ssj.welcomeactivity;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
/**
 * Created by 仲夏丶我们一起 on 2019/5/28.
 */

public class ScrollLinearLayoutManger extends LinearLayoutManager{
    private float SCROLL_SPEED = 25f;
    private Context context;
    public ScrollLinearLayoutManger(Context context)
    {
        super(context);
        this.context=context;
    }
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView,RecyclerView.State state,int postion)
    {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition)
            {
                return ScrollLinearLayoutManger.this.computeScrollVectorForPosition(targetPosition);
            }
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics)
            {
                return SCROLL_SPEED/displayMetrics.density;
            }
        };
        linearSmoothScroller.setTargetPosition(postion);
        startSmoothScroll(linearSmoothScroller);
    }
    public void setSpeedSlow(float x)
    {
        SCROLL_SPEED = context.getResources().getDisplayMetrics().density * 0.3f + (x);
    }
}
