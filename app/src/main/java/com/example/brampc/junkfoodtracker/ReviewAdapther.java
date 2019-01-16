package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapther extends RecyclerView.Adapter<ReviewAdapther.Viewholder>{

    private static final String TAG = "JFT";

    private ArrayList<Review> allReviews;
    private Context context;

    public ReviewAdapther(ArrayList<Review> reviews, Context context) {
        this.allReviews = reviews;
        this.context = context;
    }

    @Override
    public ReviewAdapther.Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_listvieuw,viewGroup,false);
        ReviewAdapther.Viewholder holder = new ReviewAdapther.Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapther.Viewholder viewholder, final int i){
        Log.d(TAG, "onBlindViewHolder: called.");
        final Review review = allReviews.get(i);
        viewholder.name.setText(review.getUser());
        viewholder.rating.setText(review.getRating()+ "");
        viewholder.information.setText(review.getDescription());
    }

    @Override
    public int getItemCount() {
        return allReviews.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        TextView rating;
        TextView information;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rating_list_name);
            rating = itemView.findViewById(R.id.rating_list_rating);
            information = itemView.findViewById(R.id.rating_list_description);
        }
    }

}