package com.example.androidapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyadapterHistory extends RecyclerView.Adapter<MyadapterHistory.ViewHolderHistory> {

    private List<Route> listItems;
    private Context context;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("routes");

    public MyadapterHistory(List<Route> routes,Context context) {
        this.listItems = routes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history,parent,false);
        return new ViewHolderHistory(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistory holder, int position) {
        Log.i("stay", "onBindViewHolder: in MyadapterHistory");
        final Route listItem = listItems.get(position);

        holder.historyStart.setText("จุดรับ: "+listItem.getStart());
        holder.historyDest.setText("จุดส่ง: "+listItem.getDest());
        holder.historyPlace.setText(listItem.getPlace());
        Log.i("stay", "onBindViewHolder: last in MyadapterHistory");


        holder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), WaitActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Route newRoute = new Route("",listItem.getPassenger(),listItem.getPlace(),listItem.getStart(),listItem.getDest());
                newRoute.setPicpassenger(listItem.getPicpassenger());
                newRoute.setTelpassenger(listItem.getTelpassenger());
                newRoute.setNamepassenger(listItem.getNamepassenger());

                Log.i("stay", "onClick: setvalue in MyadapterHistory");
                final String id = myRef.push().getKey();
                myRef.child(id).setValue(newRoute);
                intent.putExtra("idMyRoute",id);

                Log.i("stay idmyroute", "onChildChanged: "+id);


                context.startActivity(intent);
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

    public class ViewHolderHistory extends RecyclerView.ViewHolder {

        public TextView historyStart,historyDest,historyPlace;
        public Button buttonCall;
//        LinearLayout linearLayout;


        public ViewHolderHistory(@NonNull View itemView) {
            super(itemView);

            historyStart = (TextView) itemView.findViewById(R.id.historyStart);
            historyDest = (TextView) itemView.findViewById(R.id.historyDest);
            historyPlace = (TextView) itemView.findViewById(R.id.historyPlace);

//            linearLayout = itemView.findViewById(R.id.LinearLayout);
            buttonCall = (Button) itemView.findViewById(R.id.buttonCall);


        }
    }
}
