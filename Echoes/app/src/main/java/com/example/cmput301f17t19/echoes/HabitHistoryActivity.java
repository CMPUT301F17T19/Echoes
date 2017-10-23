/*
 * Class Name: HabitHistoryActivity
 *
 * Version: Version 1.0
 *
 * Date: October 19th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Habit History UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitHistoryActivity extends AppCompatActivity {

    // The arrayList of HabitEvent objects displayed in Habit History
    private static ArrayList<HabitEvent> habitEvents_HabitHistory;

    private RecyclerView habitEventsRecyclerView;
    private HabitEventOverviewAdapter habitEventOverviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history);
        this.setTitle(R.string.habithistory);

        // Set up recycler view for habit event overview in the Habit History
        habitEventsRecyclerView = (RecyclerView) findViewById(R.id.habitevents_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitEventsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(habitEventsRecyclerView.getContext(),
                layoutManager.getOrientation());
        habitEventsRecyclerView.addItemDecoration(mDividerItemDecoration);

        habitEventsRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Dummy arrayList of habitEvnets in Habit History
        habitEvents_HabitHistory = new ArrayList<HabitEvent>();
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

        HabitEvent habitEvent1 = new HabitEvent("DummyHabitEvent", "dummy", date1);
        HabitEvent habitEvent2 = new HabitEvent("DummyHabitEvent", "dummy", date2);

        try {
            habitEvent1.setComments("Dummy Habit Event 1");
            habitEvent2.setComments("Dummy Habit Event 2");
        } catch (ArgTooLongException e) {
            e.printStackTrace();
        }

        habitEvents_HabitHistory.add(habitEvent1);
        habitEvents_HabitHistory.add(habitEvent2);

        habitEventOverviewAdapter = new HabitEventOverviewAdapter(getApplicationContext());

        habitEventsRecyclerView.setAdapter(habitEventOverviewAdapter);
    }

    // Reference: https://developer.android.com/training/search/setup.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.habithistory_app_bar, menu);

        // Setting up the Search Interface
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                if (query != "" || query != null){
                    // Search the given words in the HabitEventList
                    Log.d("Search words", query);
                }

                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.clearFocus();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_menu:
                finish();

                // Go back to main menu
                Intent mainMenu_intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainMenu_intent);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Get the arrayList of HabitEvent displayed in Habit History
     */
    public static ArrayList<HabitEvent> getHabitEvents_HabitHistory(){
        return habitEvents_HabitHistory;
    }

}