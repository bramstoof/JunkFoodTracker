package com.example.brampc.junkfoodtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class restaurant_informatioon extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView restaurantName;
        TextView restaurantAdres;
        TextView restaurantRating;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_informatioon);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final Places hue = (Places) extras.getSerializable("hue");
            restaurantName = findViewById(R.id.restaurant_name);
            restaurantAdres = findViewById(R.id.restaurant_adres);
            restaurantRating = findViewById(R.id.restaurant_rating);

            restaurantName.setText(hue.getName());
            restaurantAdres.setText(hue.getStreet());
            restaurantRating.setText(Integer.toString((int) hue.getRating()));
        }

    }
}
