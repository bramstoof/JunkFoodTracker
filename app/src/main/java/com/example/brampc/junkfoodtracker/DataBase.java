package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBase  {
    private final String TAG = "DATABASESS";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("rev");
    private DocumentReference noteRef = db.document("Notebook/My First Note");





    public void addData(Review review){

        Map<String, Object> ratings = new HashMap<>();
        ratings.put("rating", review.getRating());
        ratings.put("user", review.getUser());
        ratings.put("description", review.getDescription());
        ratings.put("placeId",review.getPlaceId());
        ratings.put("date",review.getDate());

        db.collection("review")
                .add(ratings)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }



    public void readData(final String currentPlaceId, final RecyclerView recyclerView, final Context context){
        final ArrayList<Review> reviews = new ArrayList<>();
        db.collection("review")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> object = document.getData();
                            Log.d(TAG, object.toString());
                            String id = object.get("placeId").toString();
                            if (id.equals(currentPlaceId)) {
                                try {
                                    int rating = Integer.valueOf(object.get("rating").toString());
                                    String user = object.get("user").toString();
                                    String description = object.get("description").toString();
                                    String date = object.get("date").toString();

                                    Review review = new Review(rating, user, description, id, date);
                                    reviews.add(review);
                                } catch (NullPointerException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }
                        Log.d(TAG,"done recovering data");
                        ReviewAdapther adapter = new ReviewAdapther(reviews,context);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
}


}
