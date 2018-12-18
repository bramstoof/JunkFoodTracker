package com.example.brampc.junkfoodtracker;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Places {
    private String name;
    private String placeID;
    private String icon;
    private String Street;
    private LatLng location;
    private float Rating;

    public Places(String name, String placeID, String icon, String street, LatLng location, float rating) {
        this.name = name;
        this.placeID = placeID;
        this.icon = icon;
        Street = street;
        this.location = location;
        Rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

}