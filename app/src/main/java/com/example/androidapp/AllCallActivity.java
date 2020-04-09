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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AllCallActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Route> listItems;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCar = database.getReference("cars");
    DatabaseReference myRefRoute = database.getReference("routes");


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    TextView textShowPlace;


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
                startActivity(new Intent(AllCallActivity.this,AllCallActivity.class));
                return true;

            case R.id.nav_profile:
                return true;

            case R.id.nav_logout:   //this item has your app icon
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(AllCallActivity.this,LoginActivity.class));
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_call);

        listItems = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(listItems,AllCallActivity.this);
        recyclerView.setAdapter(adapter);

        textShowPlace = (TextView) findViewById(R.id.textShowPlace);

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        myRefRoute.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //  Log.i("allroute", "onChildAdded: "+dataSnapshot.getValue());
                String start = dataSnapshot.child("start").getValue(String.class);
                String dest = dataSnapshot.child("dest").getValue(String.class);
                String passenger = dataSnapshot.child("passenger").getValue(String.class);
                String place = dataSnapshot.child("passenger").getValue(String.class);

                Route routeAdd = new Route("",passenger,place,start,dest);
                listItems.add(routeAdd);

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



        myRefCar.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String Userid = firebaseAuth.getCurrentUser().getUid();
                if(dataSnapshot.child("user_id").getValue(String.class).equals(Userid)) {
                    textShowPlace.setText(dataSnapshot.child("place").getValue(String.class));
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

        for(int i = 0;i<=10;i++) {
            Route item = new Route("","idpassenger","science","SC45","toofase");

            listItems.add(item);
        }
    }

}
