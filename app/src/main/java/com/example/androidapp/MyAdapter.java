package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Route> listItems;
    private Context context;


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
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Route listItem = listItems.get(position);

        holder.startCall.setText("จุดรับ: "+listItem.getStart());
        holder.destCall.setText("จุดส่ง: "+listItem.getDest());



    }

    @Override
    public int getItemCount() {
        if(listItems == null) {
            return 0;
        }
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstnameCall,startCall,destCall,telCall;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstnameCall = (TextView) itemView.findViewById(R.id.firstnameCall);
            startCall = (TextView) itemView.findViewById(R.id.startCall);
            destCall = (TextView) itemView.findViewById(R.id.destCall);
            telCall= (TextView) itemView.findViewById(R.id.telCall);

            linearLayout = itemView.findViewById(R.id.LinearLayout);

        }
    }
}
