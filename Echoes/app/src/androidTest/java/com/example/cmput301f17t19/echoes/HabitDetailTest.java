/*
* Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
*/

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;


/**
 * Created by taijieyang on 2017/11/12.
 */

public class HabitDetailTest extends ActivityInstrumentationTestCase2 {
    public HabitDetailTest() {
        super(com.example.cmput301f17t19.echoes.HabitDetail.class);
    }
    private Solo solo;

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testcheckDataValid() {
        solo.assertCurrentActivity("Wrong Activity", HabitDetail.class);

        solo.enterText((EditText) solo.getView(R.id.Habit_name_editText), "This is a test habit");

        solo.enterText((EditText) solo.getView(R.id.Habit_reason_editText), "This habit is for test");

        solo.clickOnText("Click to select start date");
        solo.setDatePicker(0, 2017, 11-1, 11);
        solo.clickOnText("OK");

        solo.clickOnView(solo.getView(R.id.sunday_checkbox));
        solo.clickOnView(solo.getView(R.id.monday_checkBox));

        // add successful
        solo.clickOnView(solo.getView(R.id.save_button));
        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);
    }

}



