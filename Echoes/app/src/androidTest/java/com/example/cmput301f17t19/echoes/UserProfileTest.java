/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.UserProfile;

/**
 * Created by CrackCrack on 2017-10-23.
 */
public class UserProfileTest extends ActivityInstrumentationTestCase2 {

    public UserProfile testProfile;

    public UserProfileTest(){
        super(UserProfile.class);

        this.testProfile = new UserProfile("");
    }

    public void testUsername() {
        String test = "";

        String result = testProfile.getUserName();

        assertEquals("",result);
    }

    public void testEmail(){
        String test = "xxx@gmail.com";

        testProfile.setEmailAddress(test);

        String result = testProfile.getEmailAddress();

        assertEquals(test, result);

    }

    public void testComment(){
        String test = "hhhh";

        testProfile.setComment(test);

        String result = testProfile.getComment();

        assertEquals(test, result);
    }

    public void testPhoneNumber(){
        String test = "7809991111";

        testProfile.setPhoneNumber(test);

        String result = testProfile.getPhoneNumber();

        assertEquals(test, result);
    }
}
