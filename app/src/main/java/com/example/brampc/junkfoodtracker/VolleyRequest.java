package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyRequest {

    private final String TAG = "VOLLEY";
    private static final String SPECIAL_KEY = "AIzaSyA2uDGgfyhp_dSzLg4d2vf8jfjBODps0Gg";
    private String result;

    public void getPlaces(Context context, final double lat, double lng, int radius, String type, String keyword)
    {

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location="+lat +"," + lng +
                "&radius="+ radius +
                "&type="+ type +
                "&keyword="+ keyword +
                "&key=" + SPECIAL_KEY;
        Log.d(TAG, url);
        final RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                ArrayList<Places> places = new ArrayList<>();
                try {
                    int id = 0;
                    JSONArray list = new JSONObject(response).getJSONArray("results");
                    while(true){
                        JSONObject object = list.getJSONObject(id++);
                        if(object != null){
                            String name = object.getString("name");
                            String placeID = object.getString("id");
                            String icon = object.getString("icon");
                            String street = object.getString("vicinity");
                            float rating = 0;
                            if (object.has("rating"))
                            {
                                rating = (float) object.getDouble("rating");
                            }
                            JSONObject loc = object.getJSONObject("geometry").getJSONObject("location");
                            Double lat = loc.getDouble("lat");
                            double lng = loc.getDouble("lng");
                            LatLng location = new LatLng(lat,lng);
                            Places place = new Places(name,placeID,icon,street,location,rating);
                            places.add(place);
                        }
                        else {
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.toString());
            }
        });
        queue.add(stringRequest);
    }




}