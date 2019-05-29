package com.example.a526.ssj.welcomeactivity;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 仲夏丶我们一起 on 2019/5/28.
 */

public class GuidePagerAdapter extends PagerAdapter{
    private ArrayList<View> viewLists;

    public GuidePagerAdapter()
    {
        super();
    }
    public GuidePagerAdapter(ArrayList<View> viewLists)
    {
        super();
        this.viewLists = viewLists;
    }

    @Override
    public int getCount()
    {
        return viewLists.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view,@NonNull Object object)
    {
        return view==((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position)
    {
        ((ViewPager)container).addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
    {
        ((ViewPager)container).removeView(viewLists.get(position));
    }
}
