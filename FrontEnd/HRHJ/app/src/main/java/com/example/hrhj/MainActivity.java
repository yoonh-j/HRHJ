package com.example.hrhj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hrhj.Add.AddFragment;
import com.example.hrhj.Calendar.CalendarFragment;
import com.example.hrhj.Home.HomeFragment;
import com.example.hrhj.Search.SearchFragment;
import com.example.hrhj.domain.Post.Post;
import com.example.hrhj.httpConnect.HttpConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnListFragmentInteractionListener {

    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private AddFragment addFragment = new AddFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private PreferenceFragment preferenceFragment = new PreferenceFragment();
    private FragmentTransaction transaction;

    private HttpConnection httpConn = HttpConnection.getInstance();
    public ArrayList<Post> postList = new ArrayList<>();

    public BottomNavigationView bottomNavigation;

    final int REQUEST_PERMISSION_CAMERA = 0;
    final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2;
    final int REQUEST_MULTIPLE_PERMISSIONS = 3;

    public static int USER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //권한 획득
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용해주세요.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERNET) .check();

        getPostList();

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
        transaction.replace(R.id.frameLayout, fragment).commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onListFragmentInteraction(Post item) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            //bottomNavigation.setVisibility(View.GONE);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, addFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(Build.VERSION.SDK_INT >= 23) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, addFragment).addToBackStack(null).commit();
            }
        }
    }

    public void setBottomNavigationVisibility(boolean visibility) {
        if (visibility) {
            bottomNavigation.setVisibility(View.VISIBLE);
        } else {
            bottomNavigation.setVisibility(View.GONE);
        }
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "권한이 승인되지 않은 경우, 예기치 않은 오류가 발생할 수 있습니다.", Toast.LENGTH_LONG).show(); } };



    public ArrayList<Post> getPost() {
        return postList;
    }


    public void getPostList() {
        new Thread() {
            public void run() {
                httpConn.getPostList(MainActivity.USER_ID,postListCallback);
            }
        }.start();
    }

    public void updatePostList(){
        new Thread() {
            public void run() {
                httpConn.getPostList(MainActivity.USER_ID,updatePostListCallback);
            }
        }.start();
    }

    public final Callback postListCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            postList = objectMapper.readValue(responseBytes,new TypeReference<List<Post>>(){});
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, homeFragment).addToBackStack(null).commit();

        }
    };

    public final Callback updatePostListCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            postList = objectMapper.readValue(responseBytes,new TypeReference<List<Post>>(){});
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            setBottomNavigationVisibility(true);
                            bottomNavigation.setSelectedItemId(R.id.homeMenu);
                        }
                    });
                }
            }).start();


            //replaceFragment(homeFragment);

        }
    };
}
