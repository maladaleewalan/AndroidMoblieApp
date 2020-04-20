package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Route> listItems,setItem;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("routes");

    TextView textDontHave;
    Button buttonBack;

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
                intent = new Intent(HistoryActivity.this,AppActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;

            case R.id.nav_logout:   //this item has your app icon
                firebaseAuth.signOut();
                intent = new Intent(HistoryActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        setItem = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonBack = findViewById(R.id.buttonBack);

        if(firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(HistoryActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }

        textDontHave = (TextView) findViewById(R.id.textDontHaveHistory);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.child("save").getValue(boolean.class)) {
                    String place = dataSnapshot.child("place").getValue(String.class);
                    String start = dataSnapshot.child("start").getValue(String.class);
                    String dest = dataSnapshot.child("dest").getValue(String.class);
                    String passenger = dataSnapshot.child("passenger").getValue(String.class);

                    String Userid = firebaseAuth.getCurrentUser().getUid();
                    if(passenger.equals(Userid)) {
                        Route routeHistory = new Route("",passenger,place,start,dest);
                        routeHistory.setPicpassenger(dataSnapshot.child("picpassenger").getValue(String.class));
                        routeHistory.setNamepassenger(dataSnapshot.child("namepassenger").getValue(String.class));
                        routeHistory.setTelpassenger(dataSnapshot.child("telpassenger").getValue(String.class));
                        setItem.add(routeHistory);

                        listItems = new ArrayList<>();

                        for(int i = setItem.size()-1;i>=0;i--) {
                            listItems.add(setItem.get(i));

                        }

                    }
                }
                adapter = new MyadapterHistory(listItems,HistoryActivity.this);
                recyclerView.setAdapter(adapter);

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

        if(setItem.size() == 0) {
            textDontHave.setText("ยังไม่มีประวัติการเรียกรถ");
        } else {
            textDontHave.setText("");
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this,AppActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            }
        });


    }
}
