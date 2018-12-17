package com.example.brampc.junkfoodtracker;

import android.media.Rating;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private final String TAG = "DATABASESS";
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void addData(Review review){
        // Create a new user with a first and last name
        Map<String, Object> ratings = new HashMap<>();
        ratings.put("rating", review.getRating());
        ratings.put("user", review.getUser());
        ratings.put("description", review.getDescription());
        ratings.put("pladeId",review.getPlaceId());
        ratings.put("date",review.getDate());

// Add a new document with a generated ID
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

public void readData(){
    db.collection("review")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
}
}
