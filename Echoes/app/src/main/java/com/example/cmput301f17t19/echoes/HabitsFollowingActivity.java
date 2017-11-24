/*
 * Class Name: HabitsFollowingActivity
 *
 * Version: Version 1.0
 *
 * Date: November 16th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.cmput301f17t19.echoes.LoginActivity.LOGIN_USERNAME;

/**
 * Following user's habits status UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitsFollowingActivity extends AppCompatActivity {

    // The user name of the login user
    private static String login_UserName;
    // The user profile of the login user
    private static UserProfile login_userProfile;

    private RecyclerView habitStatus_RecyclerView;
    private static HabitStatusAdapter habitStatusAdapter;

    // ArrayList of followings habits statuses of the login user
    private ArrayList<Following> myFollowings;
    // array list of FollowingHabitsStatus, containint all habits statuses of my followings
    private static ArrayList<FollowingHabitsStatus> myFollowingHabitsStatuses;

    private EditText searchUser_EditText;
    private Button searchUser_Button;

    private Button recentHabitEventsMap_Button;

    private Activity mActivity;

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
        setContentView(R.layout.activity_habits_following);
        this.setTitle(R.string.habits_following);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mActivity = this;

        Intent intent = getIntent();
        if (intent.getStringExtra(LOGIN_USERNAME) != null) {
            login_UserName = intent.getStringExtra(LOGIN_USERNAME);
        } else {
            // For test
            login_UserName = "dummy3";
        }

        searchUser_EditText = (EditText) findViewById(R.id.search_user_edittext);
        searchUser_Button = (Button) findViewById(R.id.search_user_button);

        searchUser_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the intent to show a list of users with username contains the entered word (if not empty)
                String searchedWord = searchUser_EditText.getText().toString().trim();
                if (searchedWord.length() != 0) {
                    Intent searchUser_Intent = new Intent(mActivity, SearchedUserActivity.class);
                    // Send the searched word to the activity
                    searchUser_Intent.putExtra(SearchedUserActivity.SEARCHED_USER, searchedWord);

                    startActivity(searchUser_Intent);
                }
            }
        });

        recentHabitEventsMap_Button = (Button) findViewById(R.id.recentEventsMap_Button);

        recentHabitEventsMap_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the map to show the my followings' most recent habit events that have location
                // The most recent habit events of my followings
                ArrayList<HabitEvent> recentFollowingHabitEvents = FollowingSharingController.createFollowingRecentHabitEvents(myFollowings);

                // Send my followings most recent habit events of each habit to map intent
                Intent map_intent = new Intent(mActivity, MapsActivity.class);
                map_intent.putParcelableArrayListExtra(MapsActivity.HABIT_EVENT_SHOW_LOCATION_TAG, recentFollowingHabitEvents);

                startActivity(map_intent);
            }
        });

        // Set up recycler view
        habitStatus_RecyclerView = (RecyclerView) findViewById(R.id.habitstatus_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitStatus_RecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(habitStatus_RecyclerView.getContext(),
                layoutManager.getOrientation());
        habitStatus_RecyclerView.addItemDecoration(mDividerItemDecoration);

        habitStatus_RecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_UserName);

        // Get the following list
        ElasticSearchController.GetUserFollowingListTask getUserFollowingListTask = new ElasticSearchController.GetUserFollowingListTask();
        getUserFollowingListTask.execute(login_UserName);

        try {
            UserFollowingList userFollowingList = getUserFollowingListTask.get();

            if (userFollowingList != null) {
                ((TextView) findViewById(R.id.habitsFollowing_NetworkError)).setVisibility(View.GONE);

                myFollowings = userFollowingList.getFollowings();

                // Update following list of the login user in userprofile
                login_userProfile.setFollowing(myFollowings);
                // Update offline and sync with online
                OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_UserName);
                offlineStorageController.saveInFile(login_userProfile);
                ElasticSearchController.syncOnlineWithOffline(login_userProfile);

                // Create the array list of FollowingHabitsStatus, containint all habits statuses of my followings
                myFollowingHabitsStatuses = FollowingSharingController.createFollowingHabitsStatuses(myFollowings);

                habitStatusAdapter = new HabitStatusAdapter(this);
                habitStatus_RecyclerView.setAdapter(habitStatusAdapter);

            } else {
                // Offline, show offline error
                ((LinearLayout) findViewById(R.id.habitsFollowing_Layout)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.habitsFollowing_NetworkError)).setVisibility(View.VISIBLE);
            }



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        searchUser_EditText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        // Check if the user received following request
        ElasticSearchController.GetUserReceivedRequestsTask getUserReceivedRequestsTask = new ElasticSearchController.GetUserReceivedRequestsTask();
        getUserReceivedRequestsTask.execute(login_UserName);

        try {
            UserReceivedRequestsList userReceivedRequestsList = getUserReceivedRequestsTask.get();

            if (userReceivedRequestsList != null) {
                if (userReceivedRequestsList.getReceivedRequests().size() != 0) {
                    inflater.inflate(R.menu.nonempty_message_appbar, menu);
                } else {
                    inflater.inflate(R.menu.mapp_bar, menu);
                }

            } else {
                inflater.inflate(R.menu.mapp_bar, menu);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
     * Return the user profile of the login user
     *
     * @return UserProfile: the user profile of the login user
     */
    public static UserProfile getLogin_userProfile() {
        return login_userProfile;
    }

    /**
     * Get array list of FollowingHabitsStatus
     */
    public static ArrayList<FollowingHabitsStatus> getMyFollowingHabitsStatuses() {
        return myFollowingHabitsStatuses;
    }
}
