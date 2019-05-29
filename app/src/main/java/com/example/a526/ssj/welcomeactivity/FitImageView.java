package com.example.a526.ssj.welcomeactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 仲夏丶我们一起 on 2019/5/28.
 */
@SuppressLint("AppCompatCustomView")
public class FitImageView extends ImageView {
    public FitImageView(Context context)
    {
        super(context);
    }
    public FitImageView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
    {
        Drawable drawable = getDrawable();
        if(drawable!=null)
        {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int)Math.ceil((float)width*(float)drawable.getIntrinsicHeight()/(float)drawable.getIntrinsicWidth());
            setMeasuredDimension(width,height);
        }
        else
        {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }
    }
}
