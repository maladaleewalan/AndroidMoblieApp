package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ProceedActivity extends AppCompatActivity {

    private String idMyRoute = "";
    private String idDriver = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference myRefRoute = database.getReference("routes");
    DatabaseReference myRefCar = database.getReference("cars");



    TextView textDriverName;
    TextView textDriverTel;
    ImageView imageDriver;
    Button btnEnd,btnCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed);

        Log.i("stay", "onCreate: ProceedActivity");

        Intent intent = getIntent();
        idMyRoute = intent.getStringExtra("idMyRoute");
        idDriver = intent.getStringExtra("idDriver");
        textDriverName  = (TextView)findViewById(R.id.textDriverName);
        textDriverTel = (TextView)findViewById(R.id.textDriverTel);
        imageDriver  = (ImageView)findViewById(R.id.imageDriver);
        btnEnd = (Button)findViewById(R.id.btnEnd);
        btnCar = (Button)findViewById(R.id.btnCar);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                if(key.equals(idDriver)) {
                    String driverName = dataSnapshot.child("firstname").getValue(String.class);
                    String driverTel = dataSnapshot.child("tel").getValue(String.class);
                    textDriverName.setText("ชื่อคนขับ: "+driverName);
                    textDriverTel.setText("ติดต่อ: "+driverTel);

                    String driverImageUrl = dataSnapshot.child("profilePic").getValue(String.class);
                    Picasso.with(ProceedActivity.this).load(driverImageUrl).into(imageDriver);
                    Log.i("driverImageurl", "onChildAdded: "+driverImageUrl);

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

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRefCar.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String driver = dataSnapshot.child("user_id").getValue(String.class);
                        if(driver.equals(idDriver)) {
                            String imageCarUrl = dataSnapshot.child("carPic").getValue(String.class);
                            String regisCar = dataSnapshot.child("regis").getValue(String.class);
                            openDialog(imageCarUrl,regisCar);
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
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRefRoute.child(idMyRoute).child("save").setValue(true);
                myRefRoute.child(idMyRoute).child("wait").setValue(true);
                myRefRoute.child(idMyRoute).child("driver").setValue("");
                Toast.makeText(ProceedActivity.this, "ขอบคุณที่ใช้บริการ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProceedActivity.this,AppActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            }
        });
    }


    public void openDialog(String imageCarUrl,String regisCar)  {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProceedActivity.this);
        LayoutInflater inflater;
        inflater = LayoutInflater.from(ProceedActivity.this);

        final View dialogView = inflater.inflate(R.layout.layout_dialog,null);
        dialogBuilder.setView(dialogView);

        ImageView imgShowCar = dialogView.findViewById(R.id.showCar);
        TextView showRegis = dialogView.findViewById(R.id.showRegis);

        showRegis.setText("ทะเบียนรถ: "+regisCar);
        Picasso.with(ProceedActivity.this).load(imageCarUrl).into(imgShowCar);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Log.i("showdialog", "openDialog: ");

    }
}
