package com.example.brampc.junkfoodtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class restaurant_informatioon extends AppCompatActivity  {

    private Button writeReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView restaurantName;
        TextView restaurantAdres;
        TextView restaurantRating;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_informatioon);
        Bundle extras = getIntent().getExtras();
        writeReview = findViewById(R.id.Restaurant_writeReview);
        if(extras != null) {
            final Places hue = (Places) extras.getSerializable("hue");
            restaurantName = findViewById(R.id.restaurant_name);
            restaurantAdres = findViewById(R.id.restaurant_adres);
            restaurantRating = findViewById(R.id.restaurant_rating);

            restaurantName.setText(hue.getName());
            restaurantAdres.setText(hue.getStreet());
            float rating = hue.getRating();
            restaurantRating.setText(Float.toString(rating));
        }
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review_fragment review_fragment = new review_fragment();
                review_fragment.show(getSupportFragmentManager(),"Schrijf een review:");
            }
        });

    }
}
