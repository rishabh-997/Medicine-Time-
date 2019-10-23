package com.gautam.medicinetime.Leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gautam.medicinetime.R;


import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.Viewholder> {

    List<LeaderboardModel> list;
    Context context;


    public LeaderboardAdapter(List<LeaderboardModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leader_card,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.Viewholder holder, int position) {
        LeaderboardModel leaderboardModel=list.get(position);
        String a=""+(position+1)+" . "+leaderboardModel.getName();
        String b=""+(position+1)+" . "+leaderboardModel.getPoints();

        holder.name.setText(a);
        holder.points.setText(b);
    }

    @Override
    public int getItemCount() {
       return  list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView name,points;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.leaader_card_name);
            points=itemView.findViewById(R.id.leader_card_points);
        }
    }
}
