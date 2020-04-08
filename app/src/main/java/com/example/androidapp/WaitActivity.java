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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WaitActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    TextView textViewFirstname;

    Button btnCancel;



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
        Log.i("start", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

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
                    Log.i("name", "onChildAdded: "+name);

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
                myRefRoute.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        Log.i("route", "onChildAdded: "+dataSnapshot.getValue());
//                        String key = dataSnapshot.getKey();
//                        if(key.equals(firebaseAuth.getCurrentUser().getUid())) {
//
//                            String driver = dataSnapshot.child("driver").getValue(String.class);
//                            if(driver.equals("")) {
//                                myRef.child(key).setValue(null);
//                            }
//                        }
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
            }
        });
    }
}
