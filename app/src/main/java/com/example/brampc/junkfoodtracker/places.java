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
}
