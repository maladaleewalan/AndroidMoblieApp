package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();



    }


    public void loginClick(View view) {
//        if(firebaseAuth.getCurrentUser() != null) {    //3บรรทัด
//            checkRole();
//        }
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);


    }


    public void signupClick(View view) {
//        if(firebaseAuth.getCurrentUser() != null) {   //3บรรทัด
//            checkRole();
//        }
        Intent intent = new Intent(this,SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

//    public void checkRole() {
//        myRef.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    String key = dataSnapshot.getKey();
//                    if(key.equals(firebaseAuth.getCurrentUser().getUid())) {
//                        String role = dataSnapshot.child("role").getValue(String.class);
//
//                        if(role.equals("Driver")) {
//                            Toast.makeText(MainActivity.this, "กำลัง login!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this,AllCallActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            finish();
//                            startActivity(intent);
//                        }
//                        else {
//                            Toast.makeText(MainActivity.this, "กำลัง login!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this,AppActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            finish();
//                            startActivity(intent);
//                        }
//                    }
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//    }
}
