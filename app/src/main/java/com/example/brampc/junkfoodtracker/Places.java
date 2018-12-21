package com.example.brampc.junkfoodtracker;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Places implements Serializable  {
    private String name;
    private String placeID;
    private String icon;
    private String Street;
    private float Rating;
    private double latitude;
    private double longditude;

    public Places(String name, String placeID, String icon, String street, LatLng location, float rating) {
        this.name = name;
        this.placeID = placeID;
        this.icon = icon;
        Street = street;
        latitude = location.latitude;
        longditude = location.longitude;
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
        return new LatLng(latitude, longditude);
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongditude() {
        return longditude;
    }

    public void setLongditude(double longditude) {
        this.longditude = longditude;
    }
}