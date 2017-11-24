/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String HABIT_EVENT_SHOW_LOCATION_TAG = "HABIT_EVENT_SHOW_LOCATION";

    private GoogleMap mMap;

    private ArrayList<HabitEvent> shown_HabitEvents;

    private ArrayList<Marker> location_Markers;

    // My current location marker
    private Marker myCurrentLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;

    LatLngBounds.Builder boundsbuilder;

    private CheckBox highlight_CheckBox;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        mContext = this;

        location_Markers = new ArrayList<Marker>();

        Intent intent = getIntent();
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

        boundsbuilder = new LatLngBounds.Builder();

        // Show my current location
        // Reference: https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
        boolean isLocPermissionAllowed = LocationUtil.checkLocationPermission(this);

        // Set current location as default
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (isLocPermissionAllowed) {
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
                                LatLngBounds bounds = boundsbuilder.build();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));

                            } else {
                                Log.d("Map", "Current Location not available.");
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Access Location Permission Denied. Please allow location accession.", Toast.LENGTH_LONG).show();
        }

        boolean hasPointInclude = false;

        // Add markers to the habit event that has location
        for (HabitEvent habitEvent : shown_HabitEvents) {
            if (habitEvent.getLocation() != null) {
                LatLng latLng = new LatLng(habitEvent.getLocation().getLatitude(), habitEvent.getLocation().getLongitude());
                boundsbuilder.include(latLng);
                hasPointInclude = true;
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

        if (hasPointInclude || myCurrentLocationMarker != null) {
            // Show all markers on the map with proper zoom level
            LatLngBounds bounds = boundsbuilder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
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
}
