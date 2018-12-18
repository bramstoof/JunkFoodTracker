package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.Viewholder>{

    private static final String TAG = "HueAdapter";

    private ArrayList<Places> allHueLamps;
    private Context context;
    private onItemClickListener mListener;

    public PlacesAdapter(ArrayList<Places> allHueLamps, Context context) {
        this.allHueLamps = allHueLamps;
        this.context = context;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener clickListener){
        this.mListener = clickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reciclelistview,viewGroup,false);
        Viewholder holder = new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewholder, final int i){
        Log.d(TAG, "onBlindViewHolder: called.");
        Places hueLamp = allHueLamps.get(i);
        viewholder.name.setText(hueLamp.getName());
        viewholder.street.setText(hueLamp.getStreet());
        viewholder.rating.setText(hueLamp.getRating()+ "");
    }

    @Override
    public int getItemCount() {
        return allHueLamps.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        TextView street;
        TextView rating;
        RelativeLayout parentLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_name);
            parentLayout = itemView.findViewById(R.id.main_list);
            street = itemView.findViewById(R.id.list_straat);
            rating = itemView.findViewById(R.id.list_Rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(i);
                        }
                    }
                }
            });
        }
    }
}

