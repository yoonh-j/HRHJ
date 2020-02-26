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
        ((MainActivity)getContext()).setBottomNavigationVisibility(false);

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

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity)getContext()).setBottomNavigationVisibility(true);
        ((MainActivity)getContext()).replaceFragment(new HomeFragment());
        ((MainActivity)getContext()).bottomNavigation.setSelectedItemId(R.id.homeMenu);
    }

}
