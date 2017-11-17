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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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

    private EditText searchUser_EditText;
    private Button searchUser_Button;

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

        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_UserName);

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        searchUser_EditText.setText("");
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
     * Return the user profile of the login user
     *
     * @return UserProfile: the user profile of the login user
     */
    public static UserProfile getLogin_userProfile() {
        return login_userProfile;
    }
}
