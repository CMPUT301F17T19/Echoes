/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.R;
import com.example.cmput301f17t19.echoes.Models.UserProfile;

import java.util.concurrent.ExecutionException;

/**
 * Searched User UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class SearchedUserActivity extends AppCompatActivity {

    public static final String SEARCHED_USER = "SEARCHED_USER";

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_user);
        this.setTitle(R.string.searched_user);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mActivity = this;

        LinearLayout matchedUserOverview = (LinearLayout) findViewById(R.id.matchedUser_Layout);
        TextView noUserMatched = (TextView) findViewById(R.id.no_user_matched);

        final String searched_User;

        Intent intent = getIntent();
        if (intent.getStringExtra(SEARCHED_USER) != null) {
            searched_User = intent.getStringExtra(SEARCHED_USER);
        } else {
            // For test
            searched_User = "test";
        }

        // Get info of the users whose name contains the string searched_User
        ElasticSearchController.SearchUsernameTask searchUsernameTask = new ElasticSearchController.SearchUsernameTask();
        searchUsernameTask.execute(searched_User);

        try {
            final UserProfile searchedUserProfile = searchUsernameTask.get();

            // Set UI
            if (searchedUserProfile == null) {
                // The searched user does not exist
                matchedUserOverview.setVisibility(View.INVISIBLE);
                noUserMatched.setVisibility(View.VISIBLE);

            } else {
                // The searched user exist
                matchedUserOverview.setVisibility(View.VISIBLE);
                noUserMatched.setVisibility(View.INVISIBLE);

                // Set UI
                if (searchedUserProfile.getProfilePicture() != null) {
                    ((ImageView) findViewById(R.id.searched_userProfileImage))
                            .setImageBitmap(BitmapFactory.decodeByteArray(searchedUserProfile.getProfilePicture(), 0, searchedUserProfile.getProfilePicture().length));
                }

                ((TextView) findViewById(R.id.search_user_username)).setText(searchedUserProfile.getUserName());

                // Click to open the profile of the searched user
                matchedUserOverview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent searchedUserProfile_Intent = new Intent(mActivity, UserProfileActivity.class);
                        searchedUserProfile_Intent.putExtra(UserProfileActivity.SEARCHED_USERPROFILE_TAG, searchedUserProfile.getUserName());

                        startActivity(searchedUserProfile_Intent);
                    }
                });
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
