/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.UserReceivedRequestsList;

/**
 * Created by shanlu on 2017-11-26.
 */

public class UserReceivedRequestsListTest extends ActivityInstrumentationTestCase2 {

    public UserReceivedRequestsListTest() {
        super(UserReceivedRequestsList.class);
    }

    public void testGetUserName() {
        UserReceivedRequestsList userReceivedRequestsList = new UserReceivedRequestsList("John Doe");

        assertEquals(userReceivedRequestsList.getUserName(), "John Doe");
    }

    public void testGetReceivedRequests() {
        UserReceivedRequestsList userReceivedRequestsList = new UserReceivedRequestsList("John Doe");

        assertTrue(userReceivedRequestsList.getReceivedRequests().size() == 0);
    }
}
