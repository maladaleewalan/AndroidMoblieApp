package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ProceedActivity extends AppCompatActivity {

    private String idMyRoute = "";
    private String idDriver = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed);

        Intent intent = getIntent();
        idMyRoute = intent.getStringExtra("idMyRoute");
        idDriver = intent.getStringExtra("idDriver");
        Log.i("startroute", "onChildChanged: "+idMyRoute);
        Log.i("startdriver", "onChildChanged: "+idDriver);


    }
}
