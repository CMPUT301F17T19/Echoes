/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Al on 2017-11-13.
 */

public class HabitEventDetailTest extends ActivityInstrumentationTestCase2 {
    public HabitEventDetailTest() {
        super(com.example.cmput301f17t19.echoes.HabitEventDetailActivity.class);
    }
    private Solo solo;


    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }
    public void testcheckbox(){
        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);

    }

    public void testspinner1(){
        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);
        View spnr = solo.getView(R.id.Types);
        solo.clickOnView(spnr);
    }



    public void testcomment(){
        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailActivity.class);
        solo.enterText((EditText) solo.getView(R.id.WriteComment), "comment");
        assertTrue(solo.searchText("comment"));
    }
    public void testdate(){
        solo.clickOnText("Click to select start date");
        solo.setDatePicker(0, 2017, 11-1, 11);
        solo.clickOnText("OK");
    }

    public void testcheck(){
        View ct = solo.getView(R.id.Save);
        solo.pressSpinnerItem(0,1);
        solo.enterText((EditText) solo.getView(R.id.WriteComment), "comment");
        solo.clickOnView(ct);
    }


}
