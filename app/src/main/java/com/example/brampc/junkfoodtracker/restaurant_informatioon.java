package com.example.brampc.junkfoodtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class restaurant_informatioon extends AppCompatActivity{

    private Button writeReview;
    private Places place;
    private Button refesh;
    private String TAG = "INFO";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DataBase dataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView restaurantName;
        TextView restaurantAdres;
        TextView restaurantRating;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_informatioon);
        final Bundle extras = getIntent().getExtras();
        writeReview = findViewById(R.id.Restaurant_writeReview);
        refesh = findViewById(R.id.refresh);

        if(extras != null) {
            place = (Places) extras.getSerializable("place");
            restaurantName = findViewById(R.id.restaurant_name);
            restaurantAdres = findViewById(R.id.restaurant_adres);
            restaurantRating = findViewById(R.id.restaurant_rating);
            dataBase = new DataBase();
            getData();
            refesh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBase.readData(place.getPlaceID(),(RecyclerView) findViewById(R.id.review_scrollview),getBaseContext());
                }
            });
            restaurantName.setText(place.getName());
            restaurantAdres.setText(place.getStreet());
            float rating = place.getRating();
            restaurantRating.setText(Float.toString(rating));
        }
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review_fragment review_fragment = new review_fragment();
                review_fragment.show(getSupportFragmentManager(),"Schrijf een Review:", place.getPlaceID());
            }
        });



    }
    private void getData(){
        dataBase.readData(place.getPlaceID(),(RecyclerView) findViewById(R.id.review_scrollview),getBaseContext());
    }

}
