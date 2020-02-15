package com.example.hrhj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarAdapter extends ArrayAdapter<Date> {
    private HashSet<Date> eventDays;
    private Calendar calendar;
    private LayoutInflater inflater;

    public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays, Calendar calendar) {
        super(context, R.layout.item_fragment_calendar, days);
        this.eventDays = eventDays;
        this.calendar = calendar;
        inflater = LayoutInflater.from(context);
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

        calendarItemDay.setText(String.valueOf(date.getDate()));

        return view;
    }
}