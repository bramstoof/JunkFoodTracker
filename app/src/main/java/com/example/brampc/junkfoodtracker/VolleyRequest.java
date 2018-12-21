package com.example.brampc.junkfoodtracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyRequest implements PlacesAdapter.onItemClickListener{

    private final String TAG = "VOLLEY";
    private static final String SPECIAL_KEY = "AIzaSyA2uDGgfyhp_dSzLg4d2vf8jfjBODps0Gg";
    private String result;
    private RecyclerView recyclerView;
    Context context;
    ArrayList<Places> places = new ArrayList<>();
    GoogleMap mMap;

    public void getPlaces(final Context context, final RecyclerView recyclerView, final GoogleMap mMap, final double lat, double lng, int radius, String type, String keyword)
    {
        this.mMap = mMap;
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
                try {
                    int id = 0;
                    JSONArray list = new JSONObject(response).getJSONArray("results");
                    while(list.length() < id){
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
                            imageRequest(place, queue);
                            places.add(place);
                        }
                        PlacesAdapter adapter = new PlacesAdapter(places,context);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        adapter.setOnItemClickListener(VolleyRequest.this);
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

    public void getPlacesLocalTest(final Context context, final RecyclerView recyclerView, final GoogleMap mMap)
    {
        this.mMap = mMap;
        String JSON= "{\n" +
                "   \"html_attributions\" : [],\n" +
                "   \"next_page_token\" : \"CuQEXQIAADWKG0c4ZjzS84UgKusjbT7h4l4AELa8TRa_B-x3_2y4jGhjJijdfmJRPZVFI_wl6cNG64TvSSwsxfosNKa87Vbm__y6uYzdv1D6VwCVmCynYCcXM9rzdMaQPCdm_p8mEDC5IoGgd3V-HajnqlJN5ovyqbxx1yee-kVixtwx32-GrEWaNyvs1USGlnVIwxP71GUr78eif5tBOEsi2HwA3s20gdEPB_IJRQwAKZd_G0iYSpIt0w44Mk9ZUVVLkCWUbrXaZq1v3ix-iaPJmXvF526gCO6YaJPITSJnCBd0PpsYakv1Hq5uvZ-hz5P2RgSu0Xhq1FFL8O0RzzU1lvZqeeWLrT_93JZK2SeLsC5TkDRAYc4BZwjO8w_FkJ5J9t8VzMt-P0qcTCP-rr3gec9pdSLsBy0iIjgHNcnS1HmSrXEXQW2xuq1rqoUwYshRKPUnbzDZwSYtYTIgpp-Aa23NCdedxB9GxbtqWz5kmEIBMF-SkboqLKxnGz-Z8aK7qKv2VEG0mz3A-fngeHc6YpQAwLIO2aRPKQZ5jmoDo481JUJcEs1hnreuLmOE7rap_Ya3qLZIbiMSzmIGNJEeR6Wn3qeipsy0fSafcj3AgFSJySyOU2BRCRDUuWzMbOjbq3yMtx6ZzcI9VxhKLR6ySRZhzJJt82-Equ3P-hptL_1pCY810ym1xZ2yOaV-bFrRk69z4Xgm_cE-dc_CLjXKkUJLuZG0ifGmLbrBzfFvNJu78d9ml_L8HFMFr-EXFJXdOKux-66KVzTPNg9IWN9ua4Tjk60rNzxyQpUXJrjDMc6RF5BgEhC6N8d3eazOzj1KbCANM9BEGhQLEET4wXOvxOdd1F_kkWBIr3S-JQ\",\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5824033,\n" +
                "               \"lng\" : 4.730377799999999\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5836210302915,\n" +
                "                  \"lng\" : 4.731877280291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5809230697085,\n" +
                "                  \"lng\" : 4.729179319708497\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"ee993f4df6f2a444a159362791a7c09c8ccdad35\",\n" +
                "         \"name\" : \"McDonald's Breda Woonboulevard\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 4640,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/116485563304824224916/photos\\\"\\u003eTim Nieuwenhuijs\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAALhNgIDWkcedK6_jv5cGZ403Wq5SnC5gh-xLDTOEkSFo1EFdd_6XOdUSfqkJwTnJv6P2U-ptfRWw2ahDrWi6ef2QPn9yVV6GgSlodTILBoPDdUCS26llRKfDGj_Zbap4tEhB1zfIjE5OsnjjPkN7Pf66FGhRZnsWjfO2OoCnnhoUJWfvcbUIAJg\",\n" +
                "               \"width\" : 2610\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJW4Vohf0fxEcRuhIA1BfgL60\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HPJJ+X5 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HPJJ+X5\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 3.9,\n" +
                "         \"reference\" : \"ChIJW4Vohf0fxEcRuhIA1BfgL60\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_takeaway\",\n" +
                "            \"store\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Kruisvoort 101, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.588728,\n" +
                "               \"lng\" : 4.775764000000001\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5900910302915,\n" +
                "                  \"lng\" : 4.777181280291503\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5873930697085,\n" +
                "                  \"lng\" : 4.774483319708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"879012eeed93c5a24259785c1954c2ad4efeafdf\",\n" +
                "         \"name\" : \"Sumo Breda\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 1365,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/105336992124902258864/photos\\\"\\u003eSumo Breda\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRZAAAAQNdt8fSzYZUv_r0wYN11xvPB8OAyfskrRwvwffzElIT_kDDlk2z-ogJaKIE-k2v2FIRuRqgX3PcZeqgKPOc2Tj_F3QdOrJU846_b-oCmRiQpNmvvSnB6j3q5bkcRqBtSEhAnsxkJLPPY1BoJ0mV223qIGhSld3iV1t_kTc2Hg38KEtwtFTxhRA\",\n" +
                "               \"width\" : 2048\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJV6IPBIefxkcRTyv1FEZlouU\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQQG+F8 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQQG+F8\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 4.1,\n" +
                "         \"reference\" : \"ChIJV6IPBIefxkcRTyv1FEZlouU\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Grote Markt 45, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5855521,\n" +
                "               \"lng\" : 4.777241699999999\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5868944802915,\n" +
                "                  \"lng\" : 4.778593230291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5841965197085,\n" +
                "                  \"lng\" : 4.775895269708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"a145f2b28b244dd1d2ad309ab9d5f510107d8cb3\",\n" +
                "         \"name\" : \"Dolce e Salato\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 3264,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/105149019170772021717/photos\\\"\\u003eDolce e Salato\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAjpArlGOi3g0bTGiouLTmsOccTVsnArHFJdyBlDUpQ4BS1xBbaVF9he83asGfhSjGQI6MEUs_HFIczMHjz3coR6YX1zzGYqrbWgiao7CDoS21iTcRTZHVxkTlGscQ1fQmEhCyzwc5jw1BMcUSjpHO4AljGhQIjRnSxvQwK5RBjoppT087olTUww\",\n" +
                "               \"width\" : 4928\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJJwER7imgxkcRqY3hmFmwXZQ\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQPG+6V Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQPG+6V\"\n" +
                "         },\n" +
                "         \"price_level\" : 1,\n" +
                "         \"rating\" : 4.4,\n" +
                "         \"reference\" : \"ChIJJwER7imgxkcRqY3hmFmwXZQ\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"store\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Akkerstraat 9, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5983885,\n" +
                "               \"lng\" : 4.773276699999999\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5997203302915,\n" +
                "                  \"lng\" : 4.774670980291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5970223697085,\n" +
                "                  \"lng\" : 4.771973019708497\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"96cdb36b734dddc53b7f50a6c5175fe8db9504b6\",\n" +
                "         \"name\" : \"Bela's Eethuis\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : false\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 2304,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/109015514848805829897/photos\\\"\\u003eBusiness View Experts dot Com\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAARHjQjv_XPUEoJL3nRT31lkUEWX4KS2D_62qDOhvpobFAZF3CNeIbSCnkbrYmHVNpsKCuUbBMUtMpUHU-ZBFi-jVTbGI8-wwlZ7U7tYqKncmZS_aagQqOujZjGr_fBTSqEhA30M-xZHZnj-BeX9XJbXoKGhSzw7bzRymKF_ih_lD5LGVCwHyk0Q\",\n" +
                "               \"width\" : 3456\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJ_w7ADJCfxkcR9n0cdO6ZLtQ\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQXF+98 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQXF+98\"\n" +
                "         },\n" +
                "         \"rating\" : 4.2,\n" +
                "         \"reference\" : \"ChIJ_w7ADJCfxkcR9n0cdO6ZLtQ\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"restaurant\",\n" +
                "            \"bakery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"store\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Van Voorst tot Voorststraat 69, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5858767,\n" +
                "               \"lng\" : 4.7758614\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5873240802915,\n" +
                "                  \"lng\" : 4.777195530291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5846261197085,\n" +
                "                  \"lng\" : 4.774497569708497\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"c5235111e955149c71d64df5262cb4835f90109d\",\n" +
                "         \"name\" : \"McDonald's Breda Karnemelkstraat\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 2988,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/102833387175133943945/photos\\\"\\u003eLeginem Selosetiko\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAWkZgQ_DX6mZOkM5TyCE8gXTEmMIHmZHPwQhaSKD7wXZ1ZNOYJBYJQSPDP0DH-TIDrm8ttUy2By6Cqh0tr8yLEvIJoLzI6oMPOwMGVw2N2O9hPIM_DCDg9_QJxDKLyQj3EhCDJI1iBCMmzgQL9rMDfjFZGhROoadx2AlGWV-h6CtAGPP_cIJyGg\",\n" +
                "               \"width\" : 5312\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJoeVV2imgxkcRIyj8uJD0sPo\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQPG+98 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQPG+98\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 3.7,\n" +
                "         \"reference\" : \"ChIJoeVV2imgxkcRIyj8uJD0sPo\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_takeaway\",\n" +
                "            \"store\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Karnemelkstraat 5, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.58529679999999,\n" +
                "               \"lng\" : 4.7618132\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.58662358029149,\n" +
                "                  \"lng\" : 4.763189930291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.58392561970849,\n" +
                "                  \"lng\" : 4.760491969708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
                "         \"id\" : \"1e4a814f5fa8ea2fe91c752326a56059ded58b4e\",\n" +
                "         \"name\" : \"Pom Lai\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJKTgF9iCgxkcRJm1-gAMvRYI\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQP6+4P Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQP6+4P\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 3.7,\n" +
                "         \"reference\" : \"ChIJKTgF9iCgxkcRJm1-gAMvRYI\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Haagweg 18, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.57696439999999,\n" +
                "               \"lng\" : 4.7710722\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5782997802915,\n" +
                "                  \"lng\" : 4.772529330291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5756018197085,\n" +
                "                  \"lng\" : 4.769831369708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"6b78324b2785793a6c812c0d5cdde62e6e247853\",\n" +
                "         \"name\" : \"Domino's Pizza Breda - Graaf Hendrik ||| Plein\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 2268,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/110019148191080379816/photos\\\"\\u003eRichard Hunold\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAACnG383EnxaE4lmmF3Z-u9WrjXMpG1dB4i-n0my23QP4QgzBhX9SlUumQY22rfWNg6bjjqxmEAC-Fh3GgtV6uY_AM1ZoSF2CiqES0yYatiHl_UlTf3mBDqzxQ3tdwXdhFEhAGDbQs9P8Rpwh0nklupZzjGhT5esPJWWJvXnUDap5UNEB6ZzccGQ\",\n" +
                "               \"width\" : 4032\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJNcPMZtOhxkcRTlJ4Qn-yHZo\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQGC+QC Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQGC+QC\"\n" +
                "         },\n" +
                "         \"rating\" : 3.8,\n" +
                "         \"reference\" : \"ChIJNcPMZtOhxkcRTlJ4Qn-yHZo\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Graaf Hendrik III Plein 51, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5740467,\n" +
                "               \"lng\" : 4.7778651\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5753855802915,\n" +
                "                  \"lng\" : 4.779222980291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5726876197085,\n" +
                "                  \"lng\" : 4.776525019708497\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
                "         \"id\" : \"f0108062eec9c8ae078239783c9bb0b42a95f47d\",\n" +
                "         \"name\" : \"Antonio's Pizzalijn Breda\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 3000,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/111782139632025779324/photos\\\"\\u003eAntonio&#39;s Pizzalijn Breda\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAcHYdsDXqNA34hduzeGsX3vObehTc-H3iy43AYJGIG8CLVSFcty_eiTD6ew5pa1w7eUq6zBsC8wtDM67POZtIDvCcJx9R5ccB-2OsMuozURtuIH5Lcrilstu3lpf0i7gZEhCXcEVNgYmWxLviwBRCDKrHGhSZUTK85QOa-zay5m0TVZJNYP3H7g\",\n" +
                "               \"width\" : 4000\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJ04ipRzKgxkcRrdPcmTWi5kg\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQFH+J4 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQFH+J4\"\n" +
                "         },\n" +
                "         \"rating\" : 3.9,\n" +
                "         \"reference\" : \"ChIJ04ipRzKgxkcRrdPcmTWi5kg\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Grazendonkstraat 1, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.57667199999999,\n" +
                "               \"lng\" : 4.781198999999999\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.57806058029149,\n" +
                "                  \"lng\" : 4.782623830291501\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.57536261970849,\n" +
                "                  \"lng\" : 4.779925869708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"bd60d59596b617bdec84d520784c5dcb662145e1\",\n" +
                "         \"name\" : \"Il Bandito\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 3036,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/104869671943032093292/photos\\\"\\u003eAmanda Iseri\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAz16AHktghKQQccf06n4BQo9zzTbx1opUCBKFtLdu2uG1drqCF7HZlntjjc2EUodmnfKoAIfllykwXHuRJUXX-AOsg0Yh1Tq5qyzR79e9TKyuP-gmf8WdMv-yDuxZTtiXEhAt7Aln4PRTd0JNKhBe5hxcGhRRvY-rTRIK3zAWxjTrAtURotQccw\",\n" +
                "               \"width\" : 4048\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJKSwTZymgxkcRLPDDWJ-SsAQ\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQGJ+MF Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQGJ+MF\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 4.4,\n" +
                "         \"reference\" : \"ChIJKSwTZymgxkcRLPDDWJ-SsAQ\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"restaurant\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Ginnekenweg 68, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.60615850000001,\n" +
                "               \"lng\" : 4.8178825\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.6074662802915,\n" +
                "                  \"lng\" : 4.819340130291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.6047683197085,\n" +
                "                  \"lng\" : 4.816642169708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"3fe476ff9f196378a4d6a011a2f445e95527af93\",\n" +
                "         \"name\" : \"Restaurant De Chinese Muur te Teteringen\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 4160,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/104183400073364594979/photos\\\"\\u003eNjoy Bass\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAA7XDECcNuCMcnp-ngUtcnQiSv06lNf7RTXaPm6DHRgNCQP_BdWfnPl1clWuppQWyQf4TkbOIBRZ4RmX73aZh58au9Oy1F_w2u2nW2PTZwwm6T4Q_8DrdhGqqWeEc1bItjEhBiQJvr6NrkHhG0CIDTa7FEGhRtRJI9cP-1mpHMU6h6kPKQeEKpRA\",\n" +
                "               \"width\" : 3120\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJ62GqfD-fxkcRa-AAfPr8bOs\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"JR49+F5 Teteringen, Nederland\",\n" +
                "            \"global_code\" : \"9F36JR49+F5\"\n" +
                "         },\n" +
                "         \"rating\" : 3.3,\n" +
                "         \"reference\" : \"ChIJ62GqfD-fxkcRa-AAfPr8bOs\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Oosterhoutseweg 69, Teteringen\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5987409,\n" +
                "               \"lng\" : 4.7943648\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.60008988029149,\n" +
                "                  \"lng\" : 4.795713780291503\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.59739191970849,\n" +
                "                  \"lng\" : 4.793015819708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
                "         \"id\" : \"2ee4ce1229f8131d3f367c4bc51e5585e4943cd2\",\n" +
                "         \"name\" : \"Spare Rib Express\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 1536,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/115080337933663006348/photos\\\"\\u003eSpare Rib Express\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAypw-czHVU5V0CxeGtPDf9ses4EOsYLhqJMgAzO2HobIHcnBkAacT_cJZnXIuWTqEBZXuMFk3M0Ua5IwWKwL7_B7h9piP6qyCM4inOZbEQO648jVnDClTcTWFH-3MX8-HEhCQSFp9dlpwImCppov3HM8bGhRhq_ywXUyH7zioMEQ7rnO6KG-_hA\",\n" +
                "               \"width\" : 1540\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJ9SeXJ3GfxkcRnZgq6-gv5Xg\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQXV+FP Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQXV+FP\"\n" +
                "         },\n" +
                "         \"rating\" : 4.3,\n" +
                "         \"reference\" : \"ChIJ9SeXJ3GfxkcRnZgq6-gv5Xg\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Baliëndijk 44, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5938193,\n" +
                "               \"lng\" : 4.818369199999999\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5952633802915,\n" +
                "                  \"lng\" : 4.819744580291501\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5925654197085,\n" +
                "                  \"lng\" : 4.817046619708496\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"ca805a35ac151445ec3630deb637d120389e610e\",\n" +
                "         \"name\" : \"Palace\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 3024,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/101444225735239492349/photos\\\"\\u003eKristin Doyle\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAk7yP6Fl-FMo54L7ssauvPqM0OExu4pvseIIu0dAs43KbGOwni7i69EZ4pKlKSoTvqtujcLzwU_pH91VSSCmZTvectiTHUbcYeCz18X711TGtj878mgCuzu5fT8oUX0IuEhBVAiedV0RmNfpstqCUpg33GhRvWeGKs8lYCpqom-1Nb6BZwjsIQg\",\n" +
                "               \"width\" : 4032\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJq2NXYFufxkcRggsSTR0pCds\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HRV9+G8 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HRV9+G8\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 3.9,\n" +
                "         \"reference\" : \"ChIJq2NXYFufxkcRggsSTR0pCds\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Tilburgseweg 222, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.574766,\n" +
                "               \"lng\" : 4.795325\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.57611498029149,\n" +
                "                  \"lng\" : 4.796673980291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.57341701970849,\n" +
                "                  \"lng\" : 4.793976019708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"7bab536332b28a87cc1e66c7b97e24793561558b\",\n" +
                "         \"name\" : \"Ho Wan Loi\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 350,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/116705432758027148707/photos\\\"\\u003eHo Wan Loi\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAA1t44uDtrndIvKD2UGGnCGqe-bnqHhIKB7X3ZJYGcXrRPULc9GCvKStl1ToZ5FzEfRhb1FvkQgZQ6-7_9xcbrBwMtPjJ6V--7knELSPphP4yqQ7bY-FoKnFUsCr_fUMRJEhAG6tFuwK7XKY1Dpn8sQKmYGhRwSYefeNccDPix_oludjmt15Ue3Q\",\n" +
                "               \"width\" : 350\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJOWrU-M-hxkcRfAqcM4AwIQA\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQFW+W4 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQFW+W4\"\n" +
                "         },\n" +
                "         \"rating\" : 3.2,\n" +
                "         \"reference\" : \"ChIJOWrU-M-hxkcRfAqcM4AwIQA\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Erasmusplein 10, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5772369,\n" +
                "               \"lng\" : 4.7809364\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.57854878029149,\n" +
                "                  \"lng\" : 4.782214380291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.57585081970849,\n" +
                "                  \"lng\" : 4.779516419708497\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
                "         \"id\" : \"6dec69038f3daecb36b1e984228364ee1396c37d\",\n" +
                "         \"name\" : \"Papaya Ginneken Bezorgservice\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 4128,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/111650874667928747150/photos\\\"\\u003eJan Van Asseldonk\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAA2PpPbkEP_TN_43YtPlccDD8SjYZW9j5BS7nqjo7QFYDaaDckUGQqEyS1SHwuU-SaRKMAQQXqTHorZBAwbHtVeqznqD1DI1reBE8O-h4L0UITHPbtSWoL4h9tXPnboYnOEhBgPixmnm9GYxeUCdwVe43rGhSH57mfrHwdjA6TmyHt8Ml-uTFk9A\",\n" +
                "               \"width\" : 3096\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJ6R2lEy2gxkcR0KwOZ99McJo\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQGJ+V9 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQGJ+V9\"\n" +
                "         },\n" +
                "         \"rating\" : 4.2,\n" +
                "         \"reference\" : \"ChIJ6R2lEy2gxkcR0KwOZ99McJo\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Ginnekenweg 61, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.5986598,\n" +
                "               \"lng\" : 4.792184299999999\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.6000662302915,\n" +
                "                  \"lng\" : 4.793510280291501\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5973682697085,\n" +
                "                  \"lng\" : 4.790812319708496\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"982f2d01bcae6c106c44d0bf6c4dc5280176dda1\",\n" +
                "         \"name\" : \"Bangkok Take Away\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 3024,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/110567526921187948016/photos\\\"\\u003eEric Mulder\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAADL9_JASGWozdO7cAHk58nD9RAF0pQ9YwOwpilk6rGLHtPr0hVMnnvMB7lzamM3Fgrha5BLtyp29-JvjX_W2EEh1i6Cc2FmHIbTW6k3D75SAywHStL8mVxx6WyySqMjP7EhDOq0a-QdZ0Oh8tV1csQEofGhSR8yEKqUEr7owi_fwRvvXxR-cTSA\",\n" +
                "               \"width\" : 4032\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJSV15JnefxkcR8lWVzG8CBZY\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQXR+FV Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQXR+FV\"\n" +
                "         },\n" +
                "         \"rating\" : 4.3,\n" +
                "         \"reference\" : \"ChIJSV15JnefxkcR8lWVzG8CBZY\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Baliëndijk 2, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.57736810000001,\n" +
                "               \"lng\" : 4.739676200000001\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5787678302915,\n" +
                "                  \"lng\" : 4.740983880291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5760698697085,\n" +
                "                  \"lng\" : 4.738285919708497\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"052f25a9b887dbafcd0e2284cd3f0deb9d836e93\",\n" +
                "         \"name\" : \"Bakkerij Pol van den Bogaert\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : false\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 3456,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/109318511955389686857/photos\\\"\\u003eViewmaker\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAm9860yDKgpV5E78QHw717jcY-tnzTqcXvqeM5APEHnEYHz-W6cctOTkQbElLnvif61Nd-InpU90_vzqOJopGR84s708bYTmihnIlAA4L2fDuRZVu_8Qks-GpDs2i_oiKEhB4JrhFMDETJkDQ4qMFso7NGhRnp7IH4IlTeHmCjfbM7FcW2Medaw\",\n" +
                "               \"width\" : 5184\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJ1Zc2YA-gxkcRv9vu2k7VFqE\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HPGQ+WV Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HPGQ+WV\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 4.7,\n" +
                "         \"reference\" : \"ChIJ1Zc2YA-gxkcRv9vu2k7VFqE\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"bakery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"store\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Haagse Markt 33, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.59186,\n" +
                "               \"lng\" : 4.7860694\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5932447802915,\n" +
                "                  \"lng\" : 4.787372530291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5905468197085,\n" +
                "                  \"lng\" : 4.784674569708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"55311c2f13c92e0d59914731745ec92efc4da560\",\n" +
                "         \"name\" : \"De Jong DELI | bakkerij, winkel & restaurant\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : false\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 1653,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/105502921258408445127/photos\\\"\\u003eDe Jong DELI | bakkerij, winkel &amp; restaurant\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAfzfl-Etzbx0Y7rQ3Z2Yjxfp9AAZQMFYKZ_eRt7i40OYoKvDMINl63qBHA3sU1E7iMXIPhHrK-xgWPkAKp2vVRTaAuj6xFAVvnFoKJ4hI1lR5kkoiOvo4_pIWVhLdCRQcEhD__Q1Bip77MW372qVXHJiUGhSIveR4G3G-_yLzcN0M77_OjcwBeQ\",\n" +
                "               \"width\" : 2048\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJrUTVrnifxkcRIRbO4_SqKgg\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQRP+PC Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQRP+PC\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 4.4,\n" +
                "         \"reference\" : \"ChIJrUTVrnifxkcRIRbO4_SqKgg\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"bakery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"store\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Nieuwe Boschstraat 2, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.58044400000001,\n" +
                "               \"lng\" : 4.785423499999999\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.5818278802915,\n" +
                "                  \"lng\" : 4.786792380291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5791299197085,\n" +
                "                  \"lng\" : 4.784094419708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
                "         \"id\" : \"fe4f0fa939897117f2e6471710aae970f983a8ee\",\n" +
                "         \"name\" : \"Beleg Van Breda\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : false\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 250,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/109415916411442821643/photos\\\"\\u003eBeleg Van Breda\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAA8HwoMqE1pLpd3tcH4BoauyuZIbcTrgbX3qvwdfhRKhhqet8WTHSwT_JfPUbbuJbIYJP6DzLNHzUsoeUC5BWCkHtsAh4tdJNVWeXbLtaIKJ41-2Bzg2IJ_K0Gm7jfvlrCEhAflD3Mu-w_CWrudsQprK_CGhQ6AC3wMEjtK-DfjtRwNbZwfkeVww\",\n" +
                "               \"width\" : 365\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJ0-l-LYafxkcRlHWln8s8uus\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQJP+55 Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQJP+55\"\n" +
                "         },\n" +
                "         \"rating\" : 3.4,\n" +
                "         \"reference\" : \"ChIJ0-l-LYafxkcRlHWln8s8uus\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_delivery\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Generaal Maczekstraat 4, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.583051,\n" +
                "               \"lng\" : 4.7773107\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.58444498029149,\n" +
                "                  \"lng\" : 4.778631880291502\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.58174701970849,\n" +
                "                  \"lng\" : 4.775933919708497\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"4b657cdd75a311bfa073c63c3b2bf1b8030c649a\",\n" +
                "         \"name\" : \"Taste The Original\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : false\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 1050,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/106063268256812213945/photos\\\"\\u003eIrma Goeijers\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAAc1reeN51ejvCVTr44i39n1Ai_y3yJnrYWfkqm91RTo5-tcPrTEX7uFvCmIweUS1gNzvu7TjyGc11ZU6RYn6N60_qWpxYvvXLPWnFCDjtnQ3t03fRqemeGzJGYjBCnQM2EhA8S8nP02bjlYFKow50omw-GhTq0mvfznJCtHTie91MIVIwv-fneA\",\n" +
                "               \"width\" : 701\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJyW_fYimgxkcRFNPhVx6KFOI\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"HQMG+6W Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36HQMG+6W\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 4.5,\n" +
                "         \"reference\" : \"ChIJyW_fYimgxkcRFNPhVx6KFOI\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"restaurant\",\n" +
                "            \"meal_takeaway\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Van Coothplein 27, Breda\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 51.60025,\n" +
                "               \"lng\" : 4.73856\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 51.6015800802915,\n" +
                "                  \"lng\" : 4.739997930291501\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 51.5988821197085,\n" +
                "                  \"lng\" : 4.737299969708498\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
                "         \"id\" : \"3566fa427003d0a65c21f2cd3612f31af9554868\",\n" +
                "         \"name\" : \"McDonald's\",\n" +
                "         \"opening_hours\" : {\n" +
                "            \"open_now\" : true\n" +
                "         },\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 4128,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/101030328739420746610/photos\\\"\\u003eDitIsTijn\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"CmRaAAAA-MLsN7MZgjh93YEpujStEOl8v5Fu2omTaRIYpAanPBRKoaT6c6VE-zQ3mb0ihltCpHXqa8l5wuLlbf53WhKYd1_60mb4Jxhko_cxFRvvYvmURB04AXzm16gQaNI4ej1GEhBWjw9Uzgq2sAdh1JN5nh9MGhTlNOnvfuOV7qz1hBzyVYBasthmBg\",\n" +
                "               \"width\" : 3096\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJRbCKHeOfxkcRzk4Dg7hBZ3w\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"JP2Q+3C Breda, Nederland\",\n" +
                "            \"global_code\" : \"9F36JP2Q+3C\"\n" +
                "         },\n" +
                "         \"price_level\" : 2,\n" +
                "         \"rating\" : 3.7,\n" +
                "         \"reference\" : \"ChIJRbCKHeOfxkcRzk4Dg7hBZ3w\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"meal_takeaway\",\n" +
                "            \"store\",\n" +
                "            \"restaurant\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"food\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"vicinity\" : \"Veldsteen 4, Breda\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}";
        final RequestQueue queue = Volley.newRequestQueue(context);
        try {
            int id = 0;
            JSONArray list = new JSONObject(JSON).getJSONArray("results");
            while(id != 20){
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
                    imageRequest(place, queue);
                    places.add(place);
                }
                PlacesAdapter adapter = new PlacesAdapter(places,context);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter.setOnItemClickListener(VolleyRequest.this);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void imageRequest(final Places place, RequestQueue queue){
        final ImageRequest request = new ImageRequest(place.getIcon(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                MarkerOptions options = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(response)).position(place.getLocation()).title(place.getName());
                mMap.addMarker(options);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
            }
        });
        queue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Places place = places.get(position);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLocation(), 18));
    }
}