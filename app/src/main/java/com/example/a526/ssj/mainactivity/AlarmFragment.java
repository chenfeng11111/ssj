package com.example.a526.ssj.mainactivity;


import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.a526.ssj.R;
import com.example.a526.ssj.database.ClockDatabaseHolder;
import com.example.a526.ssj.entity.Clock;
import com.example.a526.ssj.entity.GlobalVariable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 10902 on 2019/5/28.
 */

public class AlarmFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    private ImageView plus;
    private RecyclerView clock_list;
    private TimePickerView pvTime1;
    private clockListAdapter adapter;
    private List<Clock> clockList = new ArrayList<Clock>();
    //ClockDatabaseHolder databaseHolder;

    public AlarmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmFragment newInstance(String param1, String param2) {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initTimePicker1();
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.clockTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View rootView = localInflater.inflate(R.layout.alarm_fragment, container, false);
        plus = (ImageView) rootView.findViewById(R.id.plus);
        clock_list = (RecyclerView) rootView.findViewById(R.id.clock_list);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime1.show();
            }
        });
        initClockList();
        return rootView;
    }

    private void initClockList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        clock_list.setLayoutManager(linearLayoutManager);
        clockList = GlobalVariable.getClockDatabaseHolder().searchClock(0, 0);
        adapter = new clockListAdapter(getContext(), clockList);
        clock_list.setAdapter(adapter);
        clock_list.setItemAnimator(new DefaultItemAnimator());
        clock_list.addItemDecoration(new clockListItemDecoration(getContext(), clockListItemDecoration.VERTICAL_LIST));
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
        pvTime1 = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                //clockList.add(getTime(date));
                Clock clock = new Clock();
                //clock.setId(clockList.size());
                clock.setRelatedNoteId(-1);
                date = new Date(date.getTime() - 8 * 60 * 60 * 1000);//转换为东八区时间
                clock.setTime(date);
                adapter.addData(clockList.size(), clock);
                GlobalVariable.getClockDatabaseHolder().insertClock(clock);
            }
        })

                .setType(new boolean[]{true, true, true, true, true, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
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
                .setTextXOffset(-10, -5, 0, -5, 10, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}