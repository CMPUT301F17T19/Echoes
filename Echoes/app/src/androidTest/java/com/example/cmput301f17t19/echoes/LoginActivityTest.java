package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

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
        solo = new Solo(getInstrumentation(), getActivity());
        Log.d("SETUP","setUp()");
    }


    public void testClickLogIn_withCorrectUserName(){

        //successfully login case

        // check if it is currently in the right activity
        solo.assertCurrentActivity("Wrong Activity",  LoginActivity.class);


        //create a new username login for testing
        UserProfile userProfile = new UserProfile("dummy3");
        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), userProfile.getUserName());
        offlineStorageController.saveInFile(userProfile);


        //enter the usrname "dummy3"
        solo.enterText((EditText) solo.getView(R.id.username),"dummy3");

        //click sign in button
        solo.clickOnView(solo.getView(R.id.username_sign_in_button));

        //load activity
        solo.waitForActivity(HabitDetail.class, 2000);
        //check if successfully log in
        solo.assertCurrentActivity("Wrong Activity", HabitDetail.class);


    }


    public void testClickLogIn_withWrongUserName(){

        //fail login case


        // check if it is currently in the right activity
        solo.assertCurrentActivity("Wrong Activity",  LoginActivity.class);


        //enter the usrname wrong user name
        solo.enterText((EditText) solo.getView(R.id.username),"wrongUserName");

        //click sign in button
        solo.clickOnView(solo.getView(R.id.username_sign_in_button));


        //check if prevent log in
        solo.assertCurrentActivity("prevent wrong username log in failed", LoginActivity.class);


    }

    



}
