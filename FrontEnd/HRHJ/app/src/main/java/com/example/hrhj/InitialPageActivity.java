package com.example.hrhj;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.hrhj.domain.User;
import com.example.hrhj.httpConnect.HttpConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InitialPageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_page);


        final SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);

        Button startButton = findViewById(R.id.IPA_Start_Button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharedPreferences.getBoolean("isNew",true))
                {

                    HttpConnection httpConnection = HttpConnection.getInstance();
                    httpConnection.createUser(new User(),createUserCallBack );
                }
                else
                {
                    Intent intent = new Intent(InitialPageActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    public final Callback createUserCallBack = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            final User newUser = objectMapper.readValue(responseBytes, User.class);

            SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("USER_ID",newUser.getUid());
            editor.putBoolean("isNew",false);
            editor.commit();

            Intent intent = new Intent(InitialPageActivity.this,MainActivity.class);
            startActivity(intent);
            finish();


        }
    };

}
