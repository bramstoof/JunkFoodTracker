package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.Viewholder>{

    private static final String TAG = "JFT";

    private ArrayList<Places> allPlaces;
    private Context context;
    private onItemClickListener mListener;

    public PlacesAdapter(ArrayList<Places> allPlaces, Context context) {
        this.allPlaces = allPlaces;
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
        final Places place = allPlaces.get(i);
        viewholder.name.setText(place.getName());
        viewholder.street.setText(place.getStreet());
        viewholder.rating.setText(place.getRating()+ "");
        viewholder.information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, restaurant_informatioon.class);
                intent.putExtra("place", (Serializable) allPlaces.get(i));
                context.startActivity(intent);
            }
        });
        int priceRating = place.getPriceRating();
        if(priceRating == 1) {
            viewholder.priceLevel1.setVisibility(View.VISIBLE);
            viewholder.priceLevel2.setVisibility(View.INVISIBLE);
            viewholder.priceLevel3.setVisibility(View.INVISIBLE);
        }else if(priceRating == 2){
            viewholder.priceLevel1.setVisibility(View.VISIBLE);
            viewholder.priceLevel2.setVisibility(View.VISIBLE);
            viewholder.priceLevel3.setVisibility(View.INVISIBLE);
        }else if(priceRating == 3){
            viewholder.priceLevel1.setVisibility(View.VISIBLE);
            viewholder.priceLevel2.setVisibility(View.VISIBLE);
            viewholder.priceLevel3.setVisibility(View.VISIBLE);
        }else{
            viewholder.priceLevel1.setVisibility(View.INVISIBLE);
            viewholder.priceLevel2.setVisibility(View.INVISIBLE);
            viewholder.priceLevel3.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return allPlaces.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        TextView street;
        TextView rating;
        Button information;
        RelativeLayout parentLayout;
        ImageView priceLevel1;
        ImageView priceLevel2;
        ImageView priceLevel3;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_name);
            parentLayout = itemView.findViewById(R.id.main_list);
            street = itemView.findViewById(R.id.list_straat);
            rating = itemView.findViewById(R.id.list_Rating);
            information = itemView.findViewById(R.id.information_button);
            priceLevel1 = itemView.findViewById(R.id.priceLevel1);
            priceLevel2 = itemView.findViewById(R.id.priceLevel2);
            priceLevel3 = itemView.findViewById(R.id.priceLevel3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION){
                            mListener.onItemClick(i);
                        }
                    }

                }
            });
        }
    }
    
}

