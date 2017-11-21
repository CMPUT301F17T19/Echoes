/*
 * Class Name: main_menu
 *
 * Version: Version 1.0
 *
 * Date: November 7th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.cmput301f17t19.echoes.LoginActivity.LOGIN_USERNAME;

/**
 * Main Menu
 *
 * @author Taijie Yang
 * @version 1.0
 * @since 1.0
 */
public class main_menu extends AppCompatActivity {

    // The user name of the login user
    private static String login_UserName;

    // The user Profile of the login user
    private UserProfile login_UserProfile;

    private Button myHabitsButton;

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
        setContentView(R.layout.activity_main_menu);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
      
        myHabitsButton = (Button) findViewById(R.id.View_My_Habits);

        Intent intent = getIntent();

        login_UserName = intent.getStringExtra(LOGIN_USERNAME);

        setLogin_UserProfile(login_UserName);
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
                // Go back to main menu
                // Pass the username of the login user to the main menu
                Intent mainMenu_intent = new Intent(this, main_menu.class);
                mainMenu_intent.putExtra(LOGIN_USERNAME, login_UserName);
                startActivity(mainMenu_intent);

                finish();

                break;

            case R.id.action_UserProfile:
                // Go to User Profile
                // Pass the username of the login user to the user profile
                Intent userProfile_intent = new Intent(this, UserProfileActivity.class);
                userProfile_intent.putExtra(UserProfileActivity.USERPROFILE_TAG, login_UserName);
                startActivity(userProfile_intent);

                break;

            case R.id.action_UserMessage:
                // Pass the username of the login user to the user message activity
                Intent userMessage_intent = new Intent(this, UserMessageActivity.class);
                userMessage_intent.putExtra(LOGIN_USERNAME, login_UserName);
                startActivity(userMessage_intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Send intent to open MyHabits
     *
     * @param view
     */
    public void viewMyHabits(View view) {
        // Pass the login User Name to the MyHabits Activity
        Intent intent = new Intent(this, MyHabitsActivity.class);
        intent.putExtra(LOGIN_USERNAME, login_UserName);

        startActivity(intent);

        finish();
    }

    /**
     * Send intent to open Following list
     *
     * @param view
     */
    public void Following(View view) {
        Intent intent = new Intent(this, HabitsFollowingActivity.class);
        intent.putExtra(LOGIN_USERNAME, login_UserName);

        startActivity(intent);
        finish();
    }

    /**
     * Send intent to open TODO_LIST
     *
     * @param view
     */
    public void Habits_todo_today (View view) {
        Intent intent = new Intent(this, ToDoActivity.class);
        intent.putExtra(LOGIN_USERNAME, login_UserName);

        startActivity(intent);
        finish();
    }

    /**
     * Send intent to open HabitHistory
     *
     * @param view
     */
    public void Habits_history(View view) {
        // Pass the login User Name to the HabitHistory Activity
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        intent.putExtra(LOGIN_USERNAME, login_UserName);

        startActivity(intent);

        finish();
    }

    /**
     * Send intent to open events map
     *
     * @param view
     */
    public void Habits_events_map(View view) {
        // Show my habit events and my followings' most recent habit events for each habit on map
        // My habit events in habit history
        ArrayList<HabitEvent> shownHabitEvents_Map = login_UserProfile.getHabit_event_list().getHabitEvents();
        // Get My followings
        ElasticSearchController.GetUserFollowingListTask getUserFollowingListTask = new ElasticSearchController.GetUserFollowingListTask();
        getUserFollowingListTask.execute(login_UserName);
        try {
            UserFollowingList userFollowingList = getUserFollowingListTask.get();

            if (userFollowingList != null) {
                ArrayList<Following> myFollowings = userFollowingList.getFollowings();

                // My followings most recent habit events for each habit
                ArrayList<HabitEvent> myFollowingRecentHabitEvents = FollowingSharingController.createFollowingRecentHabitEvents(myFollowings);

                // Add this array list to habit events shown on map
                for (HabitEvent habitEvent : myFollowingRecentHabitEvents) {
                    shownHabitEvents_Map.add(habitEvent);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent map_intent = new Intent(this, MapsActivity.class);
        map_intent.putParcelableArrayListExtra(MapsActivity.HABIT_EVENT_SHOW_LOCATION_TAG, shownHabitEvents_Map);

        startActivity(map_intent);
    }

    /**
     * Logout, go back to login screen
     *
     * @param view
     */
    public void Logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * Read the offline UserProfile data with logined name
     * Sync offline data with online storage
     *
     * @param login_Username: String, the username of the Login User
     */
    private void setLogin_UserProfile(String login_Username) {

        OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_Username);

        // Get the Offline Storage file of this user
        login_UserProfile = offlineStorageController.readFromFile();

        // Sync the offline file with online data storage
        ElasticSearchController.syncOnlineWithOffline(login_UserProfile);
    }
}
