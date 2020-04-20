package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    final DatabaseReference myRefRoute = database.getReference("routes");




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()){
            case R.id.nav_home:
                myRefRoute.child(idMyRoute).setValue(null);
                back();
                return true;

            case R.id.nav_logout:   //this item has your app icon
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRefRoute = database.getReference("routes");
                myRefRoute.child(idMyRoute).setValue(null);
                Toast.makeText(WaitActivity.this, "การ call driver ถูกยกเลิก!", Toast.LENGTH_SHORT).show();


                firebaseAuth.signOut();
                intent = new Intent(WaitActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        myRefRoute.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String idDriver = dataSnapshot.child("driver").getValue(String.class);
                boolean wait = dataSnapshot.child("wait").getValue(boolean.class);
                boolean save = dataSnapshot.child("save").getValue(boolean.class);


                if(key.equals(idMyRoute) && !(idDriver.equals(""))
                        && (wait == false)
                        && (save == false)) {
                    goToProceed(idMyRoute,idDriver);
                }
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
        Toast.makeText(WaitActivity.this, "การ call driver ถูกยกเลิก!", Toast.LENGTH_SHORT).show();
    }

    public void goToProceed(String idMyRoute,String idDriver) {

        Intent intent = new Intent(WaitActivity.this, ProceedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("idMyRoute",idMyRoute);
        intent.putExtra("idDriver",idDriver);

        finish();
        startActivity(intent);
    }
}
