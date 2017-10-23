/*
 * Class Name: MyHabitsActivity
 *
 * Version: Version 1.0
 *
 * Date: October 23rd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * My Habits UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class MyHabitsActivity extends AppCompatActivity {
    // The arrayList of Habit objects displayed in My Habits
    private static ArrayList<Habit> habits_myHabits;

    private RecyclerView habitsRecyclerView;
    private HabitOverviewAdapter habitOverviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhabits);
        this.setTitle(R.string.my_habits);

        // Set up recycler view for habit event overview in the Habit History
        habitsRecyclerView = (RecyclerView) findViewById(R.id.habits_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(habitsRecyclerView.getContext(),
                layoutManager.getOrientation());
        habitsRecyclerView.addItemDecoration(mDividerItemDecoration);

        habitsRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Dummy arrayList of habitEvnets in Habit History
        habits_myHabits = new ArrayList<Habit>();
        // Add two dummy HabitEvent objects into the list
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = simpleDateFormat.parse("2017-10-01");
            date2 = simpleDateFormat.parse("2017-10-02");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Habit habit1 = new Habit("DummyHabit", "dummy", date1, new Plan());
        Habit habit2 = new Habit("DummyHabit", "dummy", date2, new Plan());

        habits_myHabits.add(habit1);
        habits_myHabits.add(habit2);

        habitOverviewAdapter = new HabitOverviewAdapter(getApplicationContext());

        habitsRecyclerView.setAdapter(habitOverviewAdapter);
    }

    /**
     * Get the arrayList of Habit displayed in My Habits
     */
    public static ArrayList<Habit> getHabits_MyHabits(){
        return habits_myHabits;
    }
}
