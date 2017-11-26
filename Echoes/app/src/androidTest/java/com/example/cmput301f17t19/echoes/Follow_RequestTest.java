/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.Follow_Request;

/**
 * Unit Test for Follow_Request class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class Follow_RequestTest extends ActivityInstrumentationTestCase2 {

    public Follow_RequestTest(){
        super(Follow_Request.class);
    }

    /**
     * Test Getter method for Follow_Request class
     */
    public void testGetter() {
        String followingUsername = "John Doe";
        Follow_Request follow_request = new Follow_Request(followingUsername);

        assertTrue(follow_request.getUsername().equals(followingUsername));
    }
}
