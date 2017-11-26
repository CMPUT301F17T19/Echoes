/*
 * Class Name: MyHabitsActivityTest
 *
 * Version: Version 1.0
 *
 * Date: November 12nd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cmput301f17t19.echoes.Activities.HabitDetailActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.MainMenuActivity;
import com.example.cmput301f17t19.echoes.Activities.MyHabitsActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;

/**
 * Intent test for MyHabitsActivity
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class MyHabitsActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public MyHabitsActivityTest() {
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

    /**
     * Test for click 'Add Habit' button
     */
    public void testAddNewHabit() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.View_My_Habits));
        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);

        // Click on 'ADD HABIT' button
        solo.clickOnView(solo.getView(R.id.habit_add_button));

        // Open HabitDetailActivity with all field empty, and Date field be "Click to select start date"
        // Wait for HabitDetailActivity Activity
        solo.waitForActivity(HabitDetailActivity.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HabitDetailActivity.class);

        // Search for Habit Name EditText
        EditText habit_name_EditText = (EditText) solo.getView(R.id.Habit_name_editText);
        assertEquals("", habit_name_EditText.getText().toString());

        // Search for Habit Reason EditText
        EditText habit_reason_EditText = (EditText) solo.getView(R.id.Habit_reason_editText);
        assertEquals("", habit_reason_EditText.getText().toString());

        // Search for Start Date TextView
        TextView startDate_TextView = (TextView) solo.getView(R.id.date_textView);
        assertEquals("Click to select start date", startDate_TextView.getText().toString());
    }

    /**
     * Test for click on a Habit item and Open its HabitDetailActivity Activity
     */
    public void testClickHabitToDetailActivity() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.View_My_Habits));
        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);

        // Add a dummy Habit to MyHabits list
        // Click on 'ADD HABIT' button
        solo.clickOnView(solo.getView(R.id.habit_add_button));
        // Open HabitDetailActivity with all field empty, and Date field be "Click to select start date"
        // Wait for HabitDetailActivity Activity
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

        // Get the first item in the list
        // Dummy user "dummy3"
        UserProfile userProfile = new UserProfile("dummy3");
        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), userProfile.getUserName());
        offlineStorageController.saveInFile(userProfile);

        final RecyclerView habitList = ((MyHabitsActivity) solo.getCurrentActivity()).getHabitsRecyclerView();
        View habitView = habitList.getLayoutManager().getChildAt(0);

        // Check the Name of the Habit
        assertEquals("dummyHabit", ((TextView) solo.getView(R.id.habitOverview_title)).getText().toString());
        // Check the Reason of the Habit
        assertEquals("", ((TextView) solo.getView(R.id.habitOverview_reason)).getText().toString());
        // Check the Date of the Habit
        assertEquals("2017-11-11", ((TextView) solo.getView(R.id.habitOverview_date)).getText().toString());
        // Check the progress of the Habit
        assertEquals("0.0%", ((TextView) solo.getView(R.id.habitOverview_status)).getText().toString());
    }
}
