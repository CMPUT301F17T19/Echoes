/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;


/**
 * Created by CrackCrack on 2017-10-23.
 */

public class SignUpActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity> {

    public Solo solo;

    public SignUpActivityTest() {
        super(com.example.cmput301f17t19.echoes.SignUpActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void setUp() throws Exception {
        //solo = new Solo(getInstrumentation(), getActivity());
        Log.d("SETUP","setUp()");
    }


}
