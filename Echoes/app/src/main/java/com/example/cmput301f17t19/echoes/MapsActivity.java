/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.cmput301f17t19.echoes.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String HABIT_EVENT_SHOW_LOCATION_TAG = "HABIT_EVENT_SHOW_LOCATION";

    private GoogleMap mMap;

    private ArrayList<HabitEvent> shown_HabitEvents;

    private ArrayList<Marker> location_Markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        location_Markers = new ArrayList<Marker>();

        Intent intent = getIntent();
        if (intent.getParcelableArrayListExtra(HABIT_EVENT_SHOW_LOCATION_TAG) != null) {
            shown_HabitEvents = intent.getParcelableArrayListExtra(HABIT_EVENT_SHOW_LOCATION_TAG);
        }

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

        LatLngBounds.Builder boundsbuilder = new LatLngBounds.Builder();

        // Add markers to the habit event that has location
        for (HabitEvent habitEvent : shown_HabitEvents) {
            if (habitEvent.getLocation() != null) {
                LatLng latLng = new LatLng(habitEvent.getLocation().getLatitude(), habitEvent.getLocation().getLongitude());
                boundsbuilder.include(latLng);
                // Add Marker
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .anchor(0.5f, 0.5f)
                        .title(habitEvent.getLocation().getProvider()));

                if (!habitEvent.getComments().equals("")) {
                    marker.setSnippet(habitEvent.getComments());
                }

                location_Markers.add(marker);
            }
        }

        // Show all markers on the map with proper zoom level
        LatLngBounds bounds = boundsbuilder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
    }
}
