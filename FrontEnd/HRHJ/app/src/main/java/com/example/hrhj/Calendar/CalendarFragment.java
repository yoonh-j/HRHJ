package com.example.hrhj.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hrhj.MainActivity;
import com.example.hrhj.R;
import com.example.hrhj.domain.Post.Post;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;


public class CalendarFragment extends Fragment {

    private TextView currentMonth;
    private LinearLayout calendarHeader;
    private GridView calendarGrid;
    private Context context;
    private int DAYS_COUNT = 42;
    private Calendar today;
    private final HashSet<Date> events = new HashSet<>();
    private ArrayList<Post> postList = new ArrayList<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        today = new GregorianCalendar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 액션바 숨기기
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setShowHideAnimationEnabled(false);
            actionBar.hide();
        }

        context = getContext();

        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);

        currentMonth = view.findViewById(R.id.currentMonth);
        calendarHeader = view.findViewById(R.id.calendarHeader);
        calendarGrid = view.findViewById(R.id.calendarGrid);
        postList = ((MainActivity)getActivity()).postList;

        events.add(new Date());

        setCalendar(events);

        final TextView currentMonth = view.findViewById(R.id.currentMonth);

        currentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearMonthDatePicker().show();
            }
        });

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

    // 데이트피커에 연도와 월만 표시
    private DatePickerDialog yearMonthDatePicker() {
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                today.set(Calendar.YEAR, year);
                today.set(Calendar.MONTH, month);
                currentMonth.setText(dateFormat.format(today.getTime()));
                setCalendar(events);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, listener,
                today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));

        try {
            Field[] datePickerDialogFields = datePickerDialog.getClass().getDeclaredFields();

            for(Field dateField : datePickerDialogFields) {
                if(dateField.getName().equals("mDatePicker")) {
                    dateField.setAccessible(true);

                    DatePicker datePicker = (DatePicker) dateField.get(datePickerDialog);

                    Field datePickerFields[] = dateField.getType().getDeclaredFields();

                    for(Field datePickerField : datePickerFields) {
                        int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                        if(daySpinnerId != 0) {
                            View daySpinner = datePicker.findViewById(daySpinnerId);
                            if(daySpinner != null) {
                                daySpinner.setVisibility(View.GONE);
                            }
                        } else {
                            if ("mDayPicker".equals(datePickerField.getName())
                                    || "mDaySpinner".equals(datePickerField.getName())) {
                                datePickerField.setAccessible(true);
                                Object dayPicker = new Object();
                                dayPicker = datePickerField.get(datePicker);
                                ((View)dayPicker).setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return datePickerDialog;
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

        calendarGrid.setAdapter(new CalendarAdapter(context, cells, events, today,postList));
        currentMonth.setText(dateFormat.format(today.getTime()));
    }
}

