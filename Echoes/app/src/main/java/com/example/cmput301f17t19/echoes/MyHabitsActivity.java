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

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * My Habits UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class MyHabitsActivity extends AppCompatActivity {

    private RecyclerView habitsRecyclerView;
    private static HabitOverviewAdapter habitOverviewAdapter;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.primary_dark));
        }





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhabits);
        this.setTitle(R.string.my_habits);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mContext = this;

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

        habitOverviewAdapter = new HabitOverviewAdapter(getApplicationContext());

        habitsRecyclerView.setAdapter(habitOverviewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mapp_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_menu:
                finish();

                // Go back to main menu
                Intent mainMenu_intent = new Intent(getBaseContext(), main_menu.class);
                startActivity(mainMenu_intent);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Get the arrayList of Habit displayed in My Habits
     */
    public static ArrayList<Habit> getHabits_MyHabits(){
        // Get the arrayList of habits of the logged-in user
        return main_menu.getLogin_UserProfile().getHabit_list().getHabits();
    }

    /**
     * Update the HabitList of the Logged-in User
     *
     * @param updated_HabitList: ArrayList<Habit>, the update HabitList of the logged-in User
     */
    public static void updateHabitList(ArrayList<Habit> updated_HabitList) {
        main_menu.getLogin_UserProfile().getHabit_list().setHabits(updated_HabitList);
    }

    /**
     * Update My Habits Screen and User Profile file and online data of the logged-in user
     */
    public static void updateDataStorage() {
        // Update Screen
        habitOverviewAdapter.notifyDataSetChanged();

        // Update offline file
        OfflineStorageController offlineStorageController = new OfflineStorageController(mContext, main_menu.getLogin_UserProfile().getUserName());
        offlineStorageController.saveInFile(main_menu.getLogin_UserProfile());

        // Update Online data
        ElasticSearchController.syncOnlineWithOffline(main_menu.getLogin_UserProfile());
    }
}
