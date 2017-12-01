/*
 * Class Name: UserProfileActivityTest
 *
 * Version: Version 1.0
 *
 * Date: November 12nd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.ToDoActivity;
import com.example.cmput301f17t19.echoes.Activities.UserProfileActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;

/**
 * Intent test for UserProfileActivity
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public UserProfileActivityTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
        Log.d("SETUP", "setUp()");
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    private void login() {
        solo.assertCurrentActivity("Wrong Activity",  LoginActivity.class);
        //create a new username login for testing
        UserProfile userProfile = new UserProfile("dummy3");
        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), userProfile.getUserName());
        offlineStorageController.saveInFile(userProfile);


        //enter the usrname "dummy3"
        solo.enterText((EditText) solo.getView(R.id.username),"dummy3");
        solo.clickOnView(solo.getView(R.id.username_sign_in_button));
        solo.assertCurrentActivity("Wrong Activity", ToDoActivity.class);
    }

    /**
     * Test for user profile for "dummy3"
     */
    public void testDummy3UserProfile() {
        login();

        solo.clickOnView(solo.getView(R.id.action_UserProfile));
        solo.assertCurrentActivity("Wrong Activity", UserProfileActivity.class);

        // Dummy user "dummy3"
        // Check UserName
        assertEquals("dummy3", ((TextView) solo.getView(R.id.profile_username)).getText().toString());
        // Check Comment
        assertEquals("", ((TextView) solo.getView(R.id.profile_comment)).getText().toString());
        // Check Email
        assertEquals("", ((TextView) solo.getView(R.id.profile_email)).getText().toString());
        // Check Phone Number
        assertEquals("", ((TextView) solo.getView(R.id.profile_phone_number)).getText().toString());
        // Check follower number
        assertEquals("0", ((TextView) solo.getView(R.id.follower_num)).getText().toString());
        // Check following number
        assertEquals("0", ((TextView) solo.getView(R.id.following_num)).getText().toString());
    }
}
