package com.example.hrhj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.hrhj.Calendar.CalendarFragment;
import com.example.hrhj.Home.HomeFragment;
import com.example.hrhj.Search.SearchFragment;
import com.example.hrhj.dummy.DummyContent;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnListFragmentInteractionListener {

    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private AddFragment addFragment = new AddFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private PreferenceFragment preferenceFragment = new PreferenceFragment();

    public BottomNavigationView bottomNavigation;

    final int REQUEST_PERMISSION_CAMERA = 0;
    final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2;
    final int REQUEST_MULTIPLE_PERMISSIONS = 3;

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

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, homeFragment).addToBackStack(null).commit();
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
                    checkPermissions();
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
            return true;
        }
    };

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }

    // TODO: 뒤로가기 버튼 누를 시 홈 화면으로
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bottomNavigation.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA);
        } else {
            bottomNavigation.setVisibility(View.GONE);
            replaceFragment(addFragment);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(Build.VERSION.SDK_INT >= 23) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                replaceFragment(addFragment);

                bottomNavigation.setVisibility(View.GONE);
            }
        }
    }
}
