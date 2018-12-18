package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class VolleyRequest implements PlacesAdapter.onItemClickListener{

    private final String TAG = "VOLLEY";
    private static final String SPECIAL_KEY = "AIzaSyA2uDGgfyhp_dSzLg4d2vf8jfjBODps0Gg";
    private String result;
    private RecyclerView recyclerView;
    Context context;

    public void getPlaces(final Context context, final RecyclerView recyclerView, final double lat, double lng, int radius, String type, String keyword)
    {
        this.recyclerView = recyclerView;
        this.context = context;
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
                            Log.d(TAG, "1");
                        }
                        else {
                            Log.d(TAG, "2");
                            PlacesAdapter adapter = new PlacesAdapter(places,context);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            break;
                        }
                    }


                } catch (JSONException e) {
                    Log.d(TAG, "3");
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

    public void getPlacesLocalTest(final Context context, final RecyclerView recyclerView, final double lat, double lng, int radius, String type, String keyword)
    {

        ArrayList<Places> places = new ArrayList<>();
        Places p = new Places("wsdfghbjk","","","straat 1",new LatLng(1,1),4);
        Places p2 = new Places("fdycgvuhoi","","","straat 2",new LatLng(1,1),2);
        Places p3 = new Places("tfyughvjbk","","","straat 3",new LatLng(1,1),3);

        places.add(p);
        places.add(p2);
        places.add(p3);
        places.add(p);
        places.add(p2);
        places.add(p3);
        places.add(p);
        places.add(p2);
        places.add(p3);
        PlacesAdapter adapter = new PlacesAdapter(places,context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void onItemClick(int position) {

    }
}