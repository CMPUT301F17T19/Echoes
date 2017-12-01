/*
* Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
*/

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.cmput301f17t19.echoes.Activities.HabitDetailActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.MyHabitsActivity;
import com.example.cmput301f17t19.echoes.Activities.ToDoActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;


/**
 * Created by taijieyang on 2017/11/12.
 */

public class HabitDetailActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public HabitDetailActivityTest() {
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

    public void testcheckDataValid() {
        login();
        solo.assertCurrentActivity("Wrong Activity", ToDoActivity.class);
        solo.clickOnView(solo.getView(R.id.myhabit));
        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);

        // Click on 'ADD HABIT' button
        solo.clickOnView(solo.getView(R.id.habit_add_button));

        // Open HabitDetailActivity with all field empty, and Date field be "Click to select start date"
        // Wait for HabitDetailActivity Activity
        solo.waitForActivity(HabitDetailActivity.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", HabitDetailActivity.class);

        solo.enterText((EditText) solo.getView(R.id.Habit_name_editText), "This is a test habit");

        solo.enterText((EditText) solo.getView(R.id.Habit_reason_editText), "This habit is for test");

        solo.clickOnText("Click to select start date");
        solo.setDatePicker(0, 2017, 11-1, 11);
        solo.clickOnText("OK");

        solo.clickOnView(solo.getView(R.id.sunday_checkbox));
        solo.clickOnView(solo.getView(R.id.monday_checkBox));

        // add successful
        solo.clickOnView(solo.getView(R.id.save_button));
        solo.waitForActivity(MyHabitsActivity.class, 2000);
        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);
    }

}



