/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */
package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Activities.ToDoActivity;
import com.robotium.solo.Solo;

/**
 * Created by Hayden Bauder on 12/11/2017.
 */

public class ToDoActivityTest extends ActivityInstrumentationTestCase2<ToDoActivity> {
    public Solo solo;

    public ToDoActivityTest() {
        super(ToDoActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }
    // There isn't really anything to test right now other than if it displays habits that we need to do

}
