package com.example.brampc.junkfoodtracker;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class places {
    private String name;
    private String Street;
    private LatLng location;
    private List<Integer> placeType;
    private float Rating;
    private Uri url;

    public places(String name, String street, LatLng location, List<Integer> placeType, float rating, Uri url) {
        this.name = name;
        Street = street;
        this.location = location;
        this.placeType = placeType;
        Rating = rating;
        this.url = url;
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

    public List<Integer> getPlaceType() {
        return placeType;
    }

    public void setPlaceType(List<Integer> placeType) {
        this.placeType = placeType;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }
}
