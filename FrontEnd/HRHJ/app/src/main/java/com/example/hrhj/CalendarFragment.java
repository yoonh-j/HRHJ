package com.example.hrhj;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.zip.Inflater;


public class CalendarFragment extends Fragment {

    private TextView currentMonth;
    private LinearLayout calendarHeader;
    private GridView calendarGrid;
    private Context context;
    private int DAYS_COUNT = 42;
    private Calendar today;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 액션바 숨기기
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setShowHideAnimationEnabled(false);
        actionBar.hide();

        today = new GregorianCalendar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();

        final ViewGroup view = (ViewGroup) inflater .inflate(R.layout.fragment_calendar, container, false);

        currentMonth = view.findViewById(R.id.currentMonth);
        calendarHeader = view.findViewById(R.id.calendarHeader);
        calendarGrid = view.findViewById(R.id.calendarGrid);

        final HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        setCalendar(events);

        ImageButton nextMonth = view.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.set(Calendar.MONTH, today.get(Calendar.MONTH)+1);
                setCalendar(events);
            }
        });
        ImageButton lastMonth = view.findViewById(R.id.lastMonth);
        lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.add(Calendar.MONTH, -1);
                setCalendar(events);
            }
        });
        return view;

    }

    private void setCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)today.clone();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while(cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendarGrid.setAdapter(new CalendarAdapter(context, cells, events, today));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);
        currentMonth.setText(dateFormat.format(today.getTime()));
    }
}

