package com.example.hrhj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.hrhj.Calendar.CalendarFragment;
import com.example.hrhj.Home.HomeFragment;
import com.example.hrhj.Search.SearchFragment;
import com.example.hrhj.dummy.DummyContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnListFragmentInteractionListener {

    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private AddFragment addFragment = new AddFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private PreferenceFragment preferenceFragment = new PreferenceFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 상태바 색상 변경
        View view = getWindow().getDecorView();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (view != null) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, homeFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeMenu: {
                    replaceFragment(homeFragment);
                    break;
                }
                case R.id.searchMenu: {
                    replaceFragment(searchFragment);
                    break;
                }
                case R.id.addMenu: {
                    replaceFragment(addFragment);
                    break;
                }
                case R.id.calendarMenu: {
                    replaceFragment(calendarFragment);
                    break;
                }
                case R.id.preferenceMenu: {
                    replaceFragment(preferenceFragment);
                    break;
                }
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameLayout, fragment).commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }
}
