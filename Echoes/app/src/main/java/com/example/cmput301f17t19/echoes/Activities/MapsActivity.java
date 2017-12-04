/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.R;
import com.example.cmput301f17t19.echoes.Utils.LocationUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import static com.example.cmput301f17t19.echoes.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    public static final String HABIT_EVENT_SHOW_LOCATION_TAG = "HABIT_EVENT_SHOW_LOCATION";

    private GoogleMap mMap;

    private ArrayList<HabitEvent> shown_HabitEvents;

    private ArrayList<Marker> location_Markers;

    // My current location marker
    private Marker myCurrentLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback;

    LatLngBounds.Builder boundsbuilder;

    boolean isPointIncluded;

    private CheckBox highlight_CheckBox;

    private Context mContext;

    private com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx;


    // The userName of the Logged-in user
    private static String login_userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Get the login username and user Profile
        Intent intent = getIntent();
        if (intent.getStringExtra(LoginActivity.LOGIN_USERNAME) != null) {
            login_userName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);
        }

        isPointIncluded = false;



        bottomNavigationViewEx = findViewById(R.id.btm4);

        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);


        bottomNavigationViewEx.enableAnimation(false);


        //set the selected activity icon state true
        Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(3);

        menuItem.setChecked(true);


        //set up bottom navigation bar

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                switch (item.getItemId()){

                    case R.id.td:

                        Intent intent_td = new Intent(MapsActivity.this, ToDoActivity.class);
                        intent_td.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);
                        startActivity(intent_td);
                        finish();

                        break;


                    case R.id.myhabit:

                        // Pass the login User Name to the MyHabits Activity
                        Intent intent = new Intent(MapsActivity.this, MyHabitsActivity.class);
                        intent.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);

                        startActivity(intent);

                        finish();

                        break;


                    case R.id.history:

                        // Pass the login User Name to the HabitHistory Activity
                        Intent intent_his = new Intent(MapsActivity.this, HabitHistoryActivity.class);
                        intent_his.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);

                        startActivity(intent_his);

                        finish();

                        break;



                    case R.id.maps:



                        break;



                    case R.id.following:


                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            Intent intent_fol = new Intent(MapsActivity.this, HabitsFollowingActivity.class);
                            intent_fol.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);

                            startActivity(intent_fol);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                        }


                        break;


                }


                return false;
            }
        });




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        mContext = this;

        location_Markers = new ArrayList<Marker>();

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    // Set my current location marker
                    myCurrentLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .anchor(0.5f, 0.5f)
                            .title("My Current Location"));
                }
            };
        };


        if (intent.getParcelableArrayListExtra(HABIT_EVENT_SHOW_LOCATION_TAG) != null) {
            shown_HabitEvents = intent.getParcelableArrayListExtra(HABIT_EVENT_SHOW_LOCATION_TAG);
        }

        highlight_CheckBox = (CheckBox) findViewById(R.id.highlight_location_checkbox);
        highlight_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mMap != null) {
                    updateMap();
                }
            }
        });
    }



    //check network
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }








    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);

        mMap.setOnMapLoadedCallback(this);

        boundsbuilder = new LatLngBounds.Builder();

        // Show my current location
        // Reference: https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
        boolean isLocPermissionAllowed = LocationUtil.checkLocationPermission(this);

        // Set current location as default
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (isLocPermissionAllowed) {
            mMap.setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Set my current location marker
                                myCurrentLocationMarker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .anchor(0.5f, 0.5f)
                                        .title("My Current Location"));

                                boundsbuilder.include(new LatLng(location.getLatitude(), location.getLongitude()));

                                isPointIncluded = true;

                            } else {
                                Toast.makeText(mContext, "Please set location in your Settings to High Accuracy", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            mFusedLocationClient.requestLocationUpdates(new LocationRequest(), mLocationCallback, null);


        } else {
            Toast.makeText(this, "Access Location Permission Denied. Please allow location accession.", Toast.LENGTH_LONG).show();
        }

        // Add markers to the habit event that has location
        for (HabitEvent habitEvent : shown_HabitEvents) {
            if (habitEvent.getLocation() != null) {
                LatLng latLng = new LatLng(habitEvent.getLocation().getLatitude(), habitEvent.getLocation().getLongitude());
                boundsbuilder.include(latLng);

                isPointIncluded = true;

                // Add Marker
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .anchor(0.5f, 0.5f)
                        .title(habitEvent.getLocation().getProvider()));

                // Show username
                marker.setSnippet("Comment: " + habitEvent.getComments() + "\n" + habitEvent.getmUserName());

                location_Markers.add(marker);
            }
        }

        // Reference: https://stackoverflow.com/questions/13904651/android-google-maps-v2-how-to-add-marker-with-multiline-snippet
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isLocPermissionAllowed = LocationUtil.checkLocationPermission(this);

        // Set current location as default
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (isLocPermissionAllowed) {
            if (mLocationCallback !=null) {
                mFusedLocationClient.requestLocationUpdates(new LocationRequest(), mLocationCallback, null);
            }
        }
    }


    @Override
    protected void onPause(){
        super.onPause();
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    /**
     * Update Map
     */
    private void updateMap() {
        if (highlight_CheckBox.isChecked()) {
            // highlight locations that are within 5km of my current location
            if (myCurrentLocationMarker != null) {
                for (Marker marker : location_Markers) {
                    if (isWithinFiveKm(marker, myCurrentLocationMarker)) {
                        // HighLight
                        marker.setAlpha((float) 1);
                    } else {
                        // Do not highlight
                        marker.setAlpha((float) 0.3);
                    }
                }
            } else {
                Toast.makeText(this, "Current location is not available.", Toast.LENGTH_LONG).show();
            }

        } else {
            // do not highlight locations that are within 5km of my current location
            for (Marker marker : location_Markers) {
                marker.setAlpha((float) 1);
            }
        }
    }

    /**
     * Check if the distance between two markers is within 5km
     *
     * @param marker: Marker
     * @param myCurrentLocMarker: Marker, the marker on my current location
     */
    private boolean isWithinFiveKm(Marker marker, Marker myCurrentLocMarker) {
        Location location = new Location("thisLoc");
        location.setLatitude(marker.getPosition().latitude);
        location.setLongitude(marker.getPosition().longitude);

        // My location
        Location myCurrentLocation = new Location("myLoc");
        myCurrentLocation.setLatitude(myCurrentLocMarker.getPosition().latitude);
        myCurrentLocation.setLongitude(myCurrentLocMarker.getPosition().longitude);

        // Check if the distance between this two location is within 5km
        double distance = location.distanceTo(myCurrentLocation);

        if (distance <= 5000) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMapLoaded() {
        if (mMap != null) {
            if (boundsbuilder != null && isPointIncluded) {
                LatLngBounds bounds = boundsbuilder.build();

                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
            }
        }
    }
}
