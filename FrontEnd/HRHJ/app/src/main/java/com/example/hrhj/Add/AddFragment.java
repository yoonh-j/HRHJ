package com.example.hrhj.Add;

import android.content.Context;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.hrhj.Home.HomeFragment;
import com.example.hrhj.MainActivity;
import com.example.hrhj.R;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddFragment extends Fragment {
    private AddTabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    public static Bitmap bitmap;

    private final Calendar today = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.", Locale.KOREA);

    public static AddFragment newInstance(Bitmap bm) {
        AddFragment addFragment = new AddFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("Bitmap", bm);
//        addFragment.setArguments(bundle);
        return addFragment;
    }

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_add_action_bar, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 액션바 보이기
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
            actionBar.setShowHideAnimationEnabled(false);
            actionBar.show();
            setHasOptionsMenu(true);
        }
        context = getContext();
        final ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_add, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.add_viewPager);

        tabAdapter = new AddTabAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        DatePicker datePicker = view.findViewById(R.id.datePicker);
//        datePicker.setVerticalScrollBarEnabled(false); // 스크롤바 안 보이게 설정
//        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                monthOfYear += 1;
//                Toast.makeText(context, year+"."+monthOfYear+"."+dayOfMonth+".", Toast.LENGTH_SHORT).show();
//            }
//        });

//        // 게시물 작성일 설정
//        final TextView addDate = view.findViewById(R.id.addDate);
//        addDate.setText(dateFormat.format(today.getTime()));
//
//        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                today.set(Calendar.YEAR, year);
//                today.set(Calendar.MONTH, month);
//                today.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                addDate.setText(dateFormat.format(today.getTime()));
//            }
//        };
//
//        addDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener,
//                        today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
//            }
//        });

//        // 추가 버튼 클릭 시 팝업 메뉴
//        ImageButton addButton = view.findViewById(R.id.addButton);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(context, v);
//                popupMenu.getMenuInflater().inflate(R.menu.add_fragment_popup_menu, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch(item.getItemId()) {
//                            case R.id.takePicMenu:
//                                // TODO: takePic()
//                                break;
//                            case R.id.selectPicMenu:
//                                // TODO: selectPic()
//                                break;
//                            case R.id.basicPicMenu:
//                                // TODO: basicPic()
//                                break;
//                        }
//                        return true;
//                    }
//                });
//                popupMenu.show();
//                // 팝업메뉴
//                PopupWindow popupWindow = new PopupWindow(context);
//                popupWindow.setContentView(getLayoutInflater().inflate(R.layout.fragment_add_popup_menu, null));
//                popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                popupWindow.setFocusable(true);
//                popupWindow.showAsDropDown(v);
//            }
//        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
