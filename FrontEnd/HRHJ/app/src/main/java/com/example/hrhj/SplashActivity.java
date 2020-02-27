package com.example.hrhj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);

        if(sharedPreferences.getBoolean("isNew",true))
        {
            Intent intent = new Intent(SplashActivity.this,InitialPageActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
