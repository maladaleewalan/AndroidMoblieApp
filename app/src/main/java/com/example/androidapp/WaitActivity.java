package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class WaitActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    TextView textViewFirstname;
    ImageView imagePicProfile;

    Button btnCancel;


    ProgressBar progressBar;
    int count=0;
    Timer timer;

    private String idMyRoute = "";

    boolean back = false;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.nav_home:
                finish();
                startActivity(new Intent(WaitActivity.this,AppActivity.class));
                return true;

            case R.id.nav_profile:
                return true;

            case R.id.nav_logout:   //this item has your app icon
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(WaitActivity.this,LoginActivity.class));
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        Intent intent = getIntent();
        idMyRoute = intent.getStringExtra("idMyRoute");

        progressBar = findViewById(R.id.progressbar);
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                progressBar.setProgress(count);
                if(count == 100)
                    timer.cancel();
            }
        };
        timer.schedule(timerTask,0,100);

        btnCancel = (Button)findViewById(R.id.btnCancel);
        firebaseAuth = FirebaseAuth.getInstance();
        textViewFirstname = (TextView)findViewById(R.id.textViewFirstname);


        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                if(key.equals(firebaseAuth.getCurrentUser().getUid())) {
                    String name = dataSnapshot.child("firstname").getValue(String.class);

                    textViewFirstname.setText(name);

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRefRoute = database.getReference("routes");

            @Override
            public void onClick(View v) {
                myRefRoute.child(idMyRoute).setValue(null);
                back();


            }

        });


    }

    public void back() {
        startActivity(new Intent(WaitActivity.this,AppActivity.class));
    }
}
