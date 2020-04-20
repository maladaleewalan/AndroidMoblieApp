package com.example.androidapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Route> listItems;
    private Context context;
    LayoutInflater inflater;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();




    public MyAdapter(List<Route> routes, Context context) {
        this.listItems = routes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Route listItem = listItems.get(position);

        if(listItem.getPicpassenger()!=null) {
            Picasso.with(this.context).load(listItem.getPicpassenger()).into(holder.imageView);
        }

        holder.startCall.setText("จุดรับ: "+listItem.getStart());
        holder.destCall.setText("จุดส่ง: "+listItem.getDest());
        holder.firstnameCall.setText(listItem.getNamepassenger());
        holder.telCall.setText(listItem.getTelpassenger());

        holder.buttonPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if(listItem.isWait == false) {
                    Toast.makeText(context, "มีการ Picked up แล้ว!", Toast.LENGTH_SHORT).show();
                    return;
                }
                builder.setMessage("คุณจะ pick up?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        holder.buttonPickup.setText("Pick up");
                        holder.buttonPickup.setTextColor(Color.parseColor("#d84315"));

                        listItem.setWait(false);
                        listItem.setDriver(firebaseAuth.getCurrentUser().getUid());

                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = database.getReference("routes");

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int set = 0;

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String key = ds.getKey();

                                    String start = dataSnapshot.child(key).child("start").getValue(String.class);
                                    String dest = dataSnapshot.child(key).child("dest").getValue(String.class);
                                    String passenger = dataSnapshot.child(key).child("passenger").getValue(String.class);
                                    if (start.equals(listItem.getStart()) && dest.equals(listItem.getDest()) && passenger.equals(listItem.getPassenger())) {

                                       // myRef.child(key).setValue(listItem);
                                        set++;
                                    }
                                }


                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String key = ds.getKey();

                                    String start = dataSnapshot.child(key).child("start").getValue(String.class);
                                    String dest = dataSnapshot.child(key).child("dest").getValue(String.class);
                                    String passenger = dataSnapshot.child(key).child("passenger").getValue(String.class);
                                    if (start.equals(listItem.getStart()) && dest.equals(listItem.getDest()) && passenger.equals(listItem.getPassenger())) {
                                        set--;
                                    }
                                    if(set == 0) {
                                        myRef.child(key).setValue(listItem);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }).setNegativeButton("Cancel",null);

                AlertDialog alert = builder.create();
                alert.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        if(listItems == null) {
            return 0;
        }
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstnameCall,telCall;
        public TextView startCall,destCall;
        public ImageView imageView;
        public Button buttonPickup;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstnameCall = (TextView) itemView.findViewById(R.id.firstnameCall);
            startCall = (TextView) itemView.findViewById(R.id.startCall);
            destCall = (TextView) itemView.findViewById(R.id.destCall);
            telCall= (TextView) itemView.findViewById(R.id.telCall);
            imageView = itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.LinearLayout);
            buttonPickup = (Button) itemView.findViewById(R.id.buttonPickup);


        }
    }

}
