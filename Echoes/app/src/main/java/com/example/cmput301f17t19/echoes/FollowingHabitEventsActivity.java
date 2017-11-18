/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class FollowingHabitEventsActivity extends AppCompatActivity {
    public static final String FOLLOWINGHABITEVENT_TAG = "FOLLOWINGHABITEVENT";

    private RecyclerView followingHabitEventsRecyclerView;
    private HabitEventOverviewAdapter followingHabitEventOverviewAdapter;

    private static ArrayList<HabitEvent> followingHabitEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_habit_events);
        this.setTitle(R.string.followingHabitEvents);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get the array list of my following Habit Events
        Intent intent = getIntent();
        if (intent.getParcelableArrayListExtra(FOLLOWINGHABITEVENT_TAG) != null) {
            followingHabitEvents = intent.getParcelableArrayListExtra(FOLLOWINGHABITEVENT_TAG);
        }

        // Set up recycler view for habit event overview in the Habit History
        followingHabitEventsRecyclerView = (RecyclerView) findViewById(R.id.following_habitevents_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        followingHabitEventsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(followingHabitEventsRecyclerView.getContext(),
                layoutManager.getOrientation());
        followingHabitEventsRecyclerView.addItemDecoration(mDividerItemDecoration);

        followingHabitEventsRecyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        followingHabitEventOverviewAdapter = new HabitEventOverviewAdapter(this, true);
        followingHabitEventsRecyclerView.setAdapter(followingHabitEventOverviewAdapter);
    }

    /**
     * Get the array list of following habit events
     */
    public static ArrayList<HabitEvent> getFollowingHabitEvents() {
        return followingHabitEvents;
    }
}
