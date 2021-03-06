package com.example.brampc.junkfoodtracker;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, PlaceDetectionApi {

    private GoogleMap mMap;
    private final String TAG = "MAINMAP";


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COUSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15;
    private static final int RC_SIGN_IN = 123;
    private Location currentLocation;
    DataBase dataBase;

    private TextView other_user;

    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;

    private Button zoeken;
    private Button filter;
    private Button settings;
    SettingsInfo info;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        zoeken = findViewById(R.id.zoek);
        filter = findViewById(R.id.main_filter);
        settings = findViewById(R.id.main_settings);
        info = new SettingsInfo(getBaseContext());
        String taal = info.loadLanguage();
        Locale.setDefault(new Locale(taal));
        Resources res = getBaseContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(taal)); // API 17+ only.
        res.updateConfiguration(conf, dm);
        createSignInIntent();
        getLocationPromission();
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentFilter fragmentFilter = new FragmentFilter();
                fragmentFilter.show(getSupportFragmentManager(),"filter");
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSettings fragmentSettings = new FragmentSettings();
                fragmentSettings.show(getSupportFragmentManager(),"settings");
            }
        });


        zoeken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VolleyRequest volleyRequest = new VolleyRequest();
                String dataLocation = info.loadDataLocation();
                if(dataLocation == "online") {
                    volleyRequest.getPlaces(getBaseContext(), (RecyclerView) findViewById(R.id.main_list), mMap, currentLocation.getLatitude(), currentLocation.getLongitude(), info.loadRadius(), "meal_takeaway", info.loadKeyword());
                }
                else{
                    volleyRequest.getPlacesLocalTest(getBaseContext(), (RecyclerView) findViewById(R.id.main_list), mMap);
                }


            }

        });







    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void intentDatabase(){
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    public void getLocationPromission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COUSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    void showNotification(String title, String content) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setDefaults(Notification.DEFAULT_SOUND | DEFAULT_VIBRATE)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                //.setBadgeIconType(R.drawable.logo) Hier moet ons logo komen
                .setContentText(content)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        //mGoogleApiClient.
        //callPlaceDetectionApi();
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();

                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, "OnMapReady: map is ready");
        Toast.makeText(MapsActivity.this, "Map is ready", Toast.LENGTH_SHORT).show();

        if (mLocationPermissionGranted) {
            getLocationDevice();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    //De code voor een mooi inlogscherm, automatisch gegenereerd door google
    private void createSignInIntent() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.AnonymousBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setAlwaysShowSignInMethodScreen(false)
                        .setLogo(R.drawable.logo_jft)
                        .build(), RC_SIGN_IN);
    }

    //de username ophalen van de ingelogde user
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            System.out.println("HIJ KOMT IN RC SIGN IN");
            IdpResponse response = IdpResponse.fromResultIntent(data);
        }
        if (resultCode == RESULT_OK) {
            System.out.println("HIJ KOMT IN RESULT OK");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            SettingsInfo info = new SettingsInfo(getBaseContext());
            info.saveNaam(user.getDisplayName());
        } else {
            System.out.println("HIJ KOMT IN DE ELSE");
            createSignInIntent();
        }
    }

    public void getLocationDevice() {
        Log.d(TAG, "getDeviceLocation: getting the curent location");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {

            final Task location = mFusedLocationClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: fond Location!");
                        currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "my location");


                    } else {
                        Log.d(TAG, "onComplete: location is null");
                        Toast.makeText(MapsActivity.this, "location nog found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: securetyExeption:" +
                    e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the cammera ot" +
                "");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (title != "my location") {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
    }

    @Override
    public PendingResult<PlaceLikelihoodBuffer> getCurrentPlace(GoogleApiClient googleApiClient, PlaceFilter placeFilter) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        return null;
    }

    @Override
    public PendingResult<Status> reportDeviceAtPlace(GoogleApiClient googleApiClient, PlaceReport placeReport) {
        return null;
    }


}
