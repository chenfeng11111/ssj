package com.example.a526.ssj.mainactivity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.a526.ssj.R;
import com.example.a526.ssj.database.ClockDatabaseHolder;
import com.example.a526.ssj.entity.Clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 仲夏丶我们一起 on 2019/5/29.
 */

public class clockListAdapter extends RecyclerView.Adapter<clockListAdapter.clockListViewHolder>{
    private Context context;
    private List<Clock> clockList;
    private TimePickerView pvTime1;
    private int mPosition;
    private ClockDatabaseHolder databaseHolder;

    public clockListAdapter(Context context,List<Clock> clockList)
    {
        this.context = context;
        this.clockList = clockList;
    }
    @Override
    public clockListViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        databaseHolder = new ClockDatabaseHolder(context);
        initTimePicker1();
        clockListViewHolder holder = new clockListViewHolder(LayoutInflater.from(context).inflate(R.layout.clock_list_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(clockListViewHolder holder,final int position)
    {
        holder.itemView.apply();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        holder.clockText.setText(dateFormat.format(clockList.get(position).getTime().toString()));
        holder.delItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData(position);
            }
        });
        holder.clockText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mPosition = position;
                pvTime1.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return clockList.size();
    }

    public void addData(int position,Clock clock)
    {
        clockList.add(clock);
        notifyItemInserted(position);
    }

    public void removeData(int position)
    {
        clockList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    private void initTimePicker1() {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        formatter_year.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        formatter_mouth.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        formatter_day.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);


        Calendar selectedDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int + 1, mouth_int - 1, day_int);

        //时间选择器
        pvTime1 = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                //clockList.add(getTime(date));
                //SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");\
                clockList = databaseHolder.searchClock(0,0);
                Clock clock = clockList.get(mPosition);
                removeData(mPosition);
                date = new Date(date.getTime() - 8 * 60 * 60 * 1000);//转换为东八区时间
                clock.setTime(date);
                clock.setRelatedNoteId(-1);
                addData(mPosition,clock);
                databaseHolder.updateClock(clock);
            }
        })

                .setType(new boolean[]{ true, true, true,true,true,false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "时", "分", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(Color.RED)
                .setTextColorCenter(Color.RED)//设置选中项的颜色
                .setTextColorOut(Color.BLUE)//设置没有被选中项的颜色
                .setSubmitText("存储")
                .setCancelText("取消")
                .setTitleText("添加闹钟")
                .setContentSize(21)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, -5,0, 5, 10, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
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
