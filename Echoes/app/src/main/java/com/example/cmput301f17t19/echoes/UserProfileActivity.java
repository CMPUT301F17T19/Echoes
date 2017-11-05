/*
 * Class Name: UserProfileActivity
 *
 * Version: Version 1.0
 *
 * Date: November 4th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * User Profile UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserProfileActivity extends AppCompatActivity {

    public static final String USERPROFILE_TAG = "USERPROFILE_TAG";

    private String profile_username;

    private ImageButton profile_ImageButton;

    private TextView profile_username_TextView;
    private TextView profile_userComment_TextView;
    private TextView profile_userEmail_TextView;
    private TextView profile_userPhone_TextView;
    private TextView profile_userFollower_TextView;
    private TextView profile_userFollowing_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profile_ImageButton = (ImageButton) findViewById(R.id.profile_photo);

        profile_username_TextView = (TextView) findViewById(R.id.profile_username);
        profile_userComment_TextView = (TextView) findViewById(R.id.profile_comment);
        profile_userEmail_TextView = (TextView) findViewById(R.id.profile_email);
        profile_userPhone_TextView = (TextView) findViewById(R.id.profile_phone_number);
        profile_userFollower_TextView = (TextView) findViewById(R.id.follower_num);
        profile_userFollowing_TextView = (TextView) findViewById(R.id.following_num);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Get the username passed from other activity
            profile_username = bundle.getString(USERPROFILE_TAG);

            // Get the UserProfile object with the given username
            OfflineStorageController offlineStorageController = new OfflineStorageController(getApplicationContext(), profile_username);

            UserProfile userProfile = offlineStorageController.readFromFile();

            // Set User Profile Photo, Username, UserBioComment, Email, PhoneNumber, Num of Followers and Following
            profile_username_TextView.setText(userProfile.getUserName());
            profile_userComment_TextView.setText(userProfile.getComment());
            profile_userEmail_TextView.setText(userProfile.getEmailAddress());
            profile_userPhone_TextView.setText(userProfile.getPhoneNumber());
            profile_userFollower_TextView.setText(Integer.toString(userProfile.getFollower_list().size()));
            profile_userFollowing_TextView.setText(Integer.toString(userProfile.getFollowing().size()));
        }
    }
}
