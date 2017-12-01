package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.SignUpActivity;
import com.example.cmput301f17t19.echoes.Activities.ToDoActivity;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.robotium.solo.Solo;


/**
 * Created by CrackCrack on 2017-10-23.
 */

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public Solo solo;

    public LoginActivityTest() {
        super(LoginActivity.class);
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
        solo.waitForActivity(ToDoActivity.class, 2000);
        //check if successfully log in
        solo.assertCurrentActivity("Wrong Activity", ToDoActivity.class);


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


    public void testClickSignUplink(){

        // check if it is currently in the right activity
        solo.assertCurrentActivity("Wrong Activity",  LoginActivity.class);


        //click sign in button
        solo.clickOnView(solo.getView(R.id.link_signup));


        //load activity
        solo.waitForActivity(SignUpActivity.class, 2000);
        //check if it is in the correct sign up activity
        solo.assertCurrentActivity("link to SignUp activity failed", SignUpActivity.class);

    }





}
