/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cmput301f17t19.echoes.Activities.HabitEventDetailActivity;
import com.example.cmput301f17t19.echoes.Activities.HabitHistoryActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.MainMenuActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;

/**
 * Created by Al on 2017-11-13.
 */

public class HabitEventDetailActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public HabitEventDetailActivityTest() {
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
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
    }

    public void testAddHabit() throws Exception {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.habit_history));
        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);

        // Click on 'ADD HABIT Event' button
        solo.clickOnView(solo.getView(R.id.habitevents_add_button));

        // Open HabitEventDetailActivity with all field empty, and Date field be "Click to select start date"
        // Wait for HabitEventDetailActivity
        solo.waitForActivity(HabitEventDetailActivity.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);
    }

    public void testcheckbox() throws Exception {
        testAddHabit();

        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);

    }

    public void testspinner1() throws Exception {
        testAddHabit();

        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);
        View spnr = solo.getView(R.id.Types);
        solo.clickOnView(spnr);
    }

    public void testcomment() throws Exception {
        testAddHabit();

        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);
        solo.enterText((EditText) solo.getView(R.id.WriteComment), "comment");
        assertTrue(solo.searchText("comment"));
    }
    public void testdate() throws Exception {
        testAddHabit();

        solo.clickOnView(solo.getView(R.id.Get_Date));
        solo.setDatePicker(0, 2017, 11-1, 11);
        solo.clickOnText("OK");
    }

    public void testcheck() throws Exception {
        testAddHabit();

        View ct = solo.getView(R.id.Save);
        solo.pressSpinnerItem(0,1);
        solo.enterText((EditText) solo.getView(R.id.WriteComment), "comment");
        solo.clickOnView(ct);
    }
}
