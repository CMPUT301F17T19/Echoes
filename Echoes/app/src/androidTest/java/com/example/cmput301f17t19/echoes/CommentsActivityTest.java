/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.cmput301f17t19.echoes.Activities.CommentsActivity;
import com.example.cmput301f17t19.echoes.Activities.HabitDetailActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.MainMenuActivity;
import com.example.cmput301f17t19.echoes.Activities.MyHabitsActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;

/**
 * Created by shanlu on 2017-11-26.
 */

public class CommentsActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CommentsActivityTest() {
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
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
    }

    public void testCommentsActivityTest() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.View_My_Habits));
        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);

        solo.clickOnView(solo.getView(R.id.habit_add_button));
        solo.waitForActivity(HabitDetailActivity.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HabitDetailActivity.class);

        // Enter habit name: dummyHabit
        solo.enterText((EditText) solo.getView(R.id.Habit_name_editText), "dummyHabit");
        // Set start Date
        // Reference: https://stackoverflow.com/questions/6837012/robotium-how-to-set-a-date-in-date-picker-using-robotium
        solo.clickOnText("Click to select start date");
        solo.setDatePicker(0, 2017, 11-1, 11);
        solo.clickOnText("OK");
        // Click on Monday checkbox
        solo.clickOnView(solo.getView(R.id.monday_checkBox));

        // click save button
        solo.clickOnView(solo.getView(R.id.save_button));

        // Go back to MyHabits Activity
        solo.waitForActivity(MyHabitsActivity.class, 10000);
        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);

        // Click on View Comments button
        solo.clickOnView(solo.getView(R.id.view_Comments_Button));
        solo.waitForActivity(CommentsActivity.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", CommentsActivity.class);
    }
}
