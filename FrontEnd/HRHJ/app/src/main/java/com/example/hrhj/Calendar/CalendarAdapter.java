package com.example.hrhj.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hrhj.MainActivity;
import com.example.hrhj.R;
import com.example.hrhj.domain.Post.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarAdapter extends ArrayAdapter<Date> {
    private HashSet<Date> eventDays;
    private Calendar calendar;
    private LayoutInflater inflater;
    private ArrayList<Post> postList;
    private Context context;

    public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays, Calendar calendar, ArrayList<Post> postList) {
        super(context, R.layout.item_fragment_calendar, days);
        this.eventDays = eventDays;
        this.calendar = calendar;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();
        Date today = new Date();

        if(view == null) {
            view = inflater.inflate(R.layout.item_fragment_calendar, parent, false);
        }
        LinearLayout calendarItem= view.findViewById(R.id.calendarItem);
        TextView calendarItemDay = view.findViewById(R.id.calendarItemDay);


        if(month != calendar.getTime().getMonth() || year != calendar.getTime().getYear()) {
            calendarItemDay.setTextColor(view.getResources().getColor(R.color.colorGrayedOut));
        }

        if((year == today.getYear() && month == today.getMonth() &&day == today.getDate())) {
            calendarItem.setBackgroundResource(R.drawable.item_fragment_calendar_background);
        }

        //TODO: 캘린더 부분 작성 전에 기능 정의가 안되있었어서 코드가 비효율적임, 수정방안 검토
        for(int i = 0; i< postList.size(); i++)
        {
            final Post tmpPost = postList.get(i);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String postdate = simpleDateFormat.format(tmpPost.getDate());
            String thisdate = simpleDateFormat.format(date);

            if(postdate.equals(thisdate))
            {
                //TODO: 포스트가 존재하는 일자 처리 , 일단은 그냥 글씨색 아무거로나 바꿈
                calendarItemDay.setTextColor(Color.parseColor("#FF5722"));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)context).scrollToItem(tmpPost);
                    }
                });
                break;
            }
        }

        calendarItemDay.setText(String.valueOf(date.getDate()));

        return view;
    }
}