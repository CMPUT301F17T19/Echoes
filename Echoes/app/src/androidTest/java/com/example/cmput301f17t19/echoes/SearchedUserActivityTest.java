/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.cmput301f17t19.echoes.Activities.HabitsFollowingActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.SearchedUserActivity;
import com.example.cmput301f17t19.echoes.Activities.ToDoActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;

/**
 * Created by shanlu on 2017-11-26.
 */

public class SearchedUserActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public SearchedUserActivityTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
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

    public void testSearchUserActivity() {
        login();
        solo.assertCurrentActivity("Wrong Activity", ToDoActivity.class);
        solo.clickOnView(solo.getView(R.id.following));
        solo.assertCurrentActivity("Wrong Activity", HabitsFollowingActivity.class);

        // Enter username want to search
        EditText searchuser_EditText = (EditText) solo.getView(R.id.search_user_edittext);
        assertEquals("", searchuser_EditText.getText().toString());

        solo.enterText(searchuser_EditText, "dummy3");

        // Click on search button
        solo.sendKey(Solo.ENTER);

        solo.waitForActivity(SearchedUserActivity.class, 2000);
//        solo.assertCurrentActivity("Wrong Activity", SearchedUserActivity.class);
    }
}
