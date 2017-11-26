/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.Follower;

/**
 * Unit Test for Follower class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class FollowerTest extends ActivityInstrumentationTestCase2 {

    public FollowerTest(){
        super(Follower.class);
    }

    /**
     * Test Getter method
     */
    public void testGetter() {
        String followerUsername = "John Doe";
        Follower follower = new Follower(followerUsername);

        assertTrue(follower.getUsername().equals(followerUsername));
    }
}
