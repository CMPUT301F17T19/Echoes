/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Models.Following;
import com.example.cmput301f17t19.echoes.Models.FollowingHabitsStatus;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.Adapters.HabitStatusAdapter;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.R;
import com.example.cmput301f17t19.echoes.Models.UserFollowingList;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.example.cmput301f17t19.echoes.Models.UserReceivedRequestsList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    private com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.redPink));
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_following);
        this.setTitle(R.string.habits_following);


        Intent intent = getIntent();
        if (intent.getStringExtra(LoginActivity.LOGIN_USERNAME) != null) {
            login_UserName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);
        }

        bottomNavigationViewEx = findViewById(R.id.btm5);

        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);


        bottomNavigationViewEx.enableAnimation(false);


        //set the selected activity icon state true
        Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(4);

        menuItem.setChecked(true);


        //set up bottom navigation bar

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                switch (item.getItemId()){

                    case R.id.td:

                        Intent intent_td = new Intent(HabitsFollowingActivity.this, ToDoActivity.class);
                        intent_td.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);
                        startActivity(intent_td);
                        finish();

                        break;


                    case R.id.myhabit:

                        // Pass the login User Name to the MyHabits Activity
                        Intent intent = new Intent(HabitsFollowingActivity.this, MyHabitsActivity.class);
                        intent.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);

                        startActivity(intent);

                        finish();

                        break;


                    case R.id.history:

                        // Pass the login User Name to the HabitHistory Activity
                        Intent intent_his = new Intent(HabitsFollowingActivity.this, HabitHistoryActivity.class);
                        intent_his.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);

                        startActivity(intent_his);

                        finish();

                        break;



                    case R.id.maps:

                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            // Show my habit events and my followings' most recent habit events for each habit on map
                            // My habit events in habit history
                            ArrayList<HabitEvent> shownHabitEvents_Map = login_userProfile.getHabit_event_list().getHabitEvents();
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

                                Intent map_intent = new Intent(HabitsFollowingActivity.this, MapsActivity.class);
                                map_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);
                                map_intent.putParcelableArrayListExtra(MapsActivity.HABIT_EVENT_SHOW_LOCATION_TAG, shownHabitEvents_Map);

                                startActivity(map_intent);

                            } catch (InterruptedException e) {
                                Toast.makeText(HabitsFollowingActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                Toast.makeText(HabitsFollowingActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                        }



                        break;



                    case R.id.following:

                        /*

                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            Intent intent_fol = new Intent(HabitsFollowingActivity.this, HabitsFollowingActivity.class);
                            intent_fol.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);

                            startActivity(intent_fol);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                        }


                        */

                        break;


                }


                return false;
            }
        });






        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mActivity = this;



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

                habitStatusAdapter = new HabitStatusAdapter(this, login_UserName);
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
                Intent mainMenu_intent = new Intent(this, MainMenuActivity.class);
                mainMenu_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);
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
                userMessage_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);
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
