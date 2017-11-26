/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.example.cmput301f17t19.echoes.Activities.MainMenuActivity;
import com.example.cmput301f17t19.echoes.Activities.SignUpActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;


/**
 * Created by CrackCrack on 2017-10-23.
 */

public class SignUpActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity> {

    public Solo solo;


    public SignUpActivityTest() {
        super(SignUpActivity.class);
    }



    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        Log.d("SETUP","setUp()");
    }


    public void testSign_Up_success_case(){

        // check if it is currently in the right activity
        solo.assertCurrentActivity("Wrong Activity",  SignUpActivity.class);

        //enter the not register usrname
        solo.enterText((EditText) solo.getView(R.id.user_name),"Not Registered Name");

        //click sign up button
        solo.clickOnView(solo.getView(R.id.signup));

        //load activity
        solo.waitForActivity(MainMenuActivity.class, 2000);
        //check if it is in the correct main menu activity
        solo.assertCurrentActivity("click SignUp button failed", MainMenuActivity.class);


    }



    public void testSign_Up_fail_case(){

        // check if it is currently in the right activity
        solo.assertCurrentActivity("Wrong Activity",  SignUpActivity.class);


        //existed username "dummy3"
        //create a new username login for testing
        UserProfile userProfile = new UserProfile("dummy3");
        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), userProfile.getUserName());
        offlineStorageController.saveInFile(userProfile);


        //check if code handle properly with exited username

        //enter the not register usrname
        solo.enterText((EditText) solo.getView(R.id.user_name),"dummy3");

        //click sign up button
        solo.clickOnView(solo.getView(R.id.signup));

        //load activity
        solo.waitForActivity(SignUpActivity.class, 2000);
        //check if it is in the correct sign up activity
        solo.assertCurrentActivity("prevent exited username sign up failed", SignUpActivity.class);


    }







}
