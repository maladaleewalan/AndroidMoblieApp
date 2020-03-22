package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startActivities(Intent intent) {
    }

    public void loginClick(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


    public void signupClick(View view) {
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }
}
