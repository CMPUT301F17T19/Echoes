/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.Following;

/**
 * Unit Test for Following class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class FollowingTest extends ActivityInstrumentationTestCase2 {

    public FollowingTest(){
        super(Following.class);
    }

    /**
     * Test Getter method
     */
    public void testGetter() {
        String followingUsername = "John Doe";
        Following following = new Following(followingUsername);

        assertTrue(following.getUsername().equals(followingUsername));
    }
}
