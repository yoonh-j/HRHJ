package com.example.hrhj;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.zip.Inflater;


public class CalendarFragment extends Fragment {

    private Context context;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();

//        final ViewGroup view = (ViewGroup) inflater .inflate(R.layout.fragment_calendar, container, false);
//
//        CalendarView calendarView = view.findViewById(R.id.calendarView);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                Toast.makeText(context, year+"/"+(month+1)+"/"+dayOfMonth, Toast.LENGTH_LONG).show();
//            }
//        });
//        calendarView.onDrawForeground();
//        return view;
//    }

        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);

        RecyclerView calendar = view.findViewById(R.id.calendar);
        calendar.setLayoutManager(new GridLayoutManager(context, 7));

        RecyclerView.Adapter adapter = new CalendarRecyclerViewAdapter();
        adapter.notifyDataSetChanged();
        calendar.setAdapter(adapter);

        return view;
    }
}
