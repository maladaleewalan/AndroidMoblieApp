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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AllCallActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Route> listItems,setItem;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCar = database.getReference("cars");
    DatabaseReference myRefRoute = database.getReference("routes");

//    private StorageReferencence mStorageRef;


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
        Intent intent;
        switch(item.getItemId()){
            case R.id.nav_home:
                intent = new Intent(AllCallActivity.this,AllCallActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                return true;

            case R.id.nav_logout:   //this item has your app icon
                firebaseAuth.signOut();
                intent = new Intent(AllCallActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_call);

        Log.i("stay", "onCreate: AllCallActivity");
        setItem = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        textShowPlace = (TextView) findViewById(R.id.textShowPlace);

       // mStorageRef = FirebaseStorage.getInstance().getReference("ImageFolder");

        if(firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(AllCallActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }


        myRefRoute.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //  Log.i("allroute", "onChildAdded: "+dataSnapshot.getValue());
                String start = dataSnapshot.child("start").getValue(String.class);
                String dest = dataSnapshot.child("dest").getValue(String.class);
                String passenger = dataSnapshot.child("passenger").getValue(String.class);
                String place = dataSnapshot.child("passenger").getValue(String.class);

                Route routeShow = new Route("",passenger,place,start,dest);
                routeShow.setPicpassenger(dataSnapshot.child("picpassenger").getValue(String.class));
                routeShow.setNamepassenger(dataSnapshot.child("namepassenger").getValue(String.class));
                routeShow.setTelpassenger(dataSnapshot.child("telpassenger").getValue(String.class));

                setItem.add(routeShow);

                listItems = new ArrayList<>();

                for (Route r : setItem) {
                    listItems.add(r);
                }

                addItems();
                adapter = new MyAdapter(listItems,AllCallActivity.this);
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


    }




    public void addItems() {

    }


}
