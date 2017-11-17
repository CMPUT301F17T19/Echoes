/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import static com.example.cmput301f17t19.echoes.LoginActivity.LOGIN_USERNAME;

public class UserMessageActivity extends AppCompatActivity {

    private String login_userName;
    private UserProfile login_userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        this.setTitle(R.string.userMessage);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get the login username and user Profile
        Intent intent = getIntent();
        if (intent.getStringExtra(LOGIN_USERNAME) == null) {
            // For test
            login_userName = "dummy3";
        } else {
            login_userName = intent.getStringExtra(LOGIN_USERNAME);
        }

        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_userName);


    }
}
