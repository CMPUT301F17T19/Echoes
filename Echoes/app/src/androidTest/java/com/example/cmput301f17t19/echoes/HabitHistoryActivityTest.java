/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cmput301f17t19.echoes.Activities.HabitEventDetailActivity;
import com.example.cmput301f17t19.echoes.Activities.HabitHistoryActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.ToDoActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;

/**
 * Created by shanlu on 2017-11-14.
 */

public class HabitHistoryActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public HabitHistoryActivityTest() {
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
     * Test for click 'Add Habit Event' button
     */
    public void testAddNewHabit() {
        login();
        solo.assertCurrentActivity("Wrong Activity", ToDoActivity.class);
        solo.clickOnView(solo.getView(R.id.history));
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);

        // Click on 'ADD HABIT Event' button
        solo.clickOnView(solo.getView(R.id.habitevents_add_button));

        // Open HabitEventDetailActivity with all field empty, and Date field be "Click to select start date"
        // Wait for HabitEventDetailActivity
        solo.waitForActivity(HabitEventDetailActivity.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);

        // Search for Comment EditText
        EditText comment_EditText = (EditText) solo.getView(R.id.WriteComment);
        assertEquals("", comment_EditText.getText().toString());

        // Search for Start Date TextView
        TextView startDate_TextView = (TextView) solo.getView(R.id.Get_Date);
        assertEquals("Click to select event date", startDate_TextView.getText().toString());
    }
}
