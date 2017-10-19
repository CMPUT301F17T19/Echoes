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
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Habit History UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history);

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
}