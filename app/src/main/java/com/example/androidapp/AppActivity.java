package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private Button buttonSubmit;
    EditText editTextStart,editTextDest;

    String start,dest,place = "";



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
                intent = new Intent(AppActivity.this,AppActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;

            case R.id.nav_logout:   //this item has your app icon
                firebaseAuth.signOut();
                intent = new Intent(AppActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Log.i("stay", "onCreate: AppActivity");


        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextStart = (EditText)findViewById(R.id.editTextStart);
        editTextDest = (EditText)findViewById(R.id.editTextDest);

        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AppActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.place));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(AppActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                place = mySpinner.getSelectedItem().toString();

                if(place.equals("เลือกตำแหน่งใกล้เคียง")) {
                    Toast.makeText(AppActivity.this, "กรุณาเลือกตำแหน่งใกล้เคียง!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextStart.getText().toString())) {
                    Toast.makeText(AppActivity.this, "กรุณาใส่สถานที่ที่ให้ไปรับ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextDest.getText().toString())) {
                    Toast.makeText(AppActivity.this, "กรุณาใส่เปลายทาง!", Toast.LENGTH_SHORT).show();
                    return;
                }

                start = editTextStart.getText().toString();
                dest = editTextDest.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("routes");

                DatabaseReference myRefUser = database.getReference("users");

                final Route newRoute = new Route("",firebaseAuth.getCurrentUser().getUid(),place,start,dest);
                final String id = myRef.push().getKey();


                myRefUser.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String key = dataSnapshot.getKey();
                        if(key.equals(firebaseAuth.getCurrentUser().getUid())) {
                            String namepass = dataSnapshot.child("firstname").getValue(String.class);
                            String telpass = dataSnapshot.child("tel").getValue(String.class);
                            String uri = dataSnapshot.child("profilePic").getValue(String.class);
                            newRoute.setNamepassenger(namepass);
                            newRoute.setTelpassenger(telpass);
                            newRoute.setPicpassenger(uri);

                            myRef.child(id).setValue(newRoute);

                            editTextStart.setText("");
                            editTextDest.setText("");

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

                Intent intent = new Intent(AppActivity.this,WaitActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("idMyRoute",id);

                Log.i("test", "onClick: id= "+id);
                finish();
                startActivity(intent);



            }
        });

    }
}
