package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
