package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyadapterHistory extends RecyclerView.Adapter<MyadapterHistory.ViewHolderHistory> {

    private List<Route> listItems;
    private Context context;

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
        final Route listItem = listItems.get(position);

        holder.historyStart.setText("จุดรับ: "+listItem.getStart());
        holder.historyDest.setText("จุดส่ง: "+listItem.getDest());
        holder.historyPlace.setText(listItem.getPlace());
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
