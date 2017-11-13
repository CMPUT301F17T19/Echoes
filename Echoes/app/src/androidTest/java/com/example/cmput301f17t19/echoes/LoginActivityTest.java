package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;


/**
 * Created by CrackCrack on 2017-10-23.
 */

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public Solo solo;

    public LoginActivityTest() {
        super(com.example.cmput301f17t19.echoes.LoginActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void setUp() throws Exception {
        //solo = new Solo(getInstrumentation(), getActivity());
        Log.d("SETUP","setUp()");
    }


}
