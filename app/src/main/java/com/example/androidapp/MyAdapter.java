package com.example.androidapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import static android.provider.CalendarContract.CalendarCache.URI;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Route> listItems;
    private Context context;
    LayoutInflater inflater;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ProgressDialog progressDialog;


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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Route listItem = listItems.get(position);

        if(listItem.getPicpassenger()!=null) {
            Picasso.with(this.context).setLoggingEnabled(true);
            Picasso.with(this.context).load(listItem.getPicpassenger()).into(holder.imageView);
        }
        else {

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
                    Toast.makeText(context, "Already Picked up!", Toast.LENGTH_SHORT).show();
                    return;
                }
                builder.setMessage("Are you pick up").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            holder.buttonPickup.setText("You Pick up");
                            holder.buttonPickup.setTextColor(Color.parseColor("#d84315"));
                            listItem.setWait(false);

                            //set driver,iswait == false ลงใน database

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("routes");


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
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.LinearLayout);
            buttonPickup = (Button) itemView.findViewById(R.id.buttonPickup);


        }
    }

}
