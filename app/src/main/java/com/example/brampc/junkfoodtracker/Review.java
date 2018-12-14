package com.example.brampc.junkfoodtracker;

import java.util.Date;

public class Review {
    private int rating;
    private String user;
    private String description;
    private String placeId;
    private String date;

    public Review(int rating, String user, String description, String placeId, String date) {
        this.rating = rating;
        this.user = user;
        this.description = description;
        this.placeId = placeId;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
