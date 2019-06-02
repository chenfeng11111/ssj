package com.example.a526.ssj.mainactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 仲夏丶我们一起 on 2019/5/29.
 */

public class NoteListItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mOrientation;

    public NoteListItemDecoration(Context context, int orientation)
    {
        final TypedArray array = context.obtainStyledAttributes(ATTRS);
        mDivider = array.getDrawable(0);
        array.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation)
    {
        if(orientation!=HORIZONTAL_LIST&&orientation!=VERTICAL_LIST)
        {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent)
    {
        if(mOrientation==VERTICAL_LIST)
        {
            drawVertical(c,parent);
        }
    }

    public void drawVertical(Canvas c,RecyclerView parent)
    {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++)
        {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }
    @Override
    public void getItemOffsets(Rect outRect,int itemPosition,RecyclerView parent)
    {
        if(mOrientation == VERTICAL_LIST)
        {
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }
    }
}
