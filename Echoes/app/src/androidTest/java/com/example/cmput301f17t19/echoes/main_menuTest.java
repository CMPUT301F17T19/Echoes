/*
* Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
*/

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by taijieyang on 2017/11/13.
 */

public class main_menuTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public main_menuTest() {
        super(com.example.cmput301f17t19.echoes.LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

   public void testStart() throws Exception {
        Activity activity = getActivity();
   }

    public void login() {
        solo.assertCurrentActivity("Wrong Activity",  LoginActivity.class);
        //create a new username login for testing
        UserProfile userProfile = new UserProfile("dummy3");
        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), userProfile.getUserName());
        offlineStorageController.saveInFile(userProfile);


        //enter the usrname "dummy3"
        solo.enterText((EditText) solo.getView(R.id.username),"dummy3");
        solo.clickOnView(solo.getView(R.id.username_sign_in_button));
        solo.assertCurrentActivity("Wrong Activity", main_menu.class);


    }
//    public void testViewMyHabit() {
//        // test if the button works correctly
//        solo.assertCurrentActivity("Wrong Activity", main_menu.class);
//        solo.clickOnView(solo.getView(R.id.View_My_Habits));
//        solo.assertCurrentActivity("Wrong Activity", MyHabitsActivity.class);
//    }

//    public void testFollowing() {
//        // test if the button works correctly
//        solo.assertCurrentActivity("Wrong Activity", main_menu.class);
//        solo.clickOnView(solo.getView(R.id.following));
//        solo.assertCurrentActivity("Wrong Activity", .class);
//    }

//
//    public void testTODO() {
//        // test if the button works correctly
//        solo.assertCurrentActivity("Wrong Activity", main_menu.class);
//        solo.clickOnView(solo.getView(R.id.TODO));
//        solo.assertCurrentActivity("Wrong Activity", .class);
//    }

//    public void testHabitHistory() {
//        // test if the button works correctly
//        solo.assertCurrentActivity("Wrong Activity", main_menu.class);
//        solo.clickOnView(solo.getView(R.id.habit_history));
//        solo.assertCurrentActivity("Wrong Activity", HabitHistoryActivity.class);
//    }

//    public void testHabitEventMap() {
//        // test if the button works correctly
//        solo.assertCurrentActivity("Wrong Activity", main_menu.class);
//        solo.clickOnView(solo.getView(R.id.habit_event_map));
//        solo.assertCurrentActivity("Wrong Activity", .class);
//    }

//    public void testLogout() {
//        // test if the button works correctly
//        solo.assertCurrentActivity("Wrong Activity", main_menu.class);
//        solo.clickOnView(solo.getView(R.id.logout));
//        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
//    }


}

