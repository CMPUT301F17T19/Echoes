/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class main_menu extends AppCompatActivity {

    // The user profile of the login user
    private static UserProfile login_UserProfile;

    private Button myHabitsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
      
        myHabitsButton = (Button) findViewById(R.id.View_My_Habits);

        Intent intent = getIntent();

        String login_Username = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);

        setLogin_UserProfile(login_Username);
    }


    public void viewMyHabits(View view) {
        Intent intent = new Intent(this, MyHabitsActivity.class);
        startActivity(intent);
    }

    public void Following(View view) {
        Intent intent = new Intent(this, Following.class);
        startActivity(intent);
    }

//    public void Habits_todo_today (View view) {
//        Intent intent = new Intent(this, AddNewActivity.class);
//        startActivity(intent);
//    }


    public void Habits_history(View view) {
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        startActivity(intent);
    }

//    public void Habits_events_map(View view) {
//        Intent intent = new Intent(this, AddNewActivity.class);
//        startActivity(intent);
//    }
  
    public void Logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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

    /**
     * Get the logined User Profile
     *
     * @return login_UserProfile: UserProfile, the user profile of the logged-in user
     */
    public static UserProfile getLogin_UserProfile() {
        return login_UserProfile;
    }

    /**
     * Update the User Profile of the logged-in user
     *
     * @param updated_UserProfile: UserProfile, the Updated User Profile of the logged-in user
     */
    public static void setLogin_UserProfile(UserProfile updated_UserProfile) {
        login_UserProfile = updated_UserProfile;
    }
}
