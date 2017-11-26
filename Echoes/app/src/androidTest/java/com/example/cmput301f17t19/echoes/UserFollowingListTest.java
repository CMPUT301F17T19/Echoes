/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.UserFollowingList;

/**
 * Unit Test for UserFollowingList class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserFollowingListTest extends ActivityInstrumentationTestCase2 {

    public UserFollowingListTest() {
        super(UserFollowingList.class);
    }

    public void testGetUsername() {
        UserFollowingList userFollowingList = new UserFollowingList("John Doe");

        assertEquals(userFollowingList.getUserName(), "John Doe");
        assertFalse(userFollowingList.getUserName().equals("John"));
    }

    public void testGetFollowings () {
        UserFollowingList userFollowingList = new UserFollowingList("John Doe");

        assertTrue(userFollowingList.getFollowings().size() == 0);
        assertFalse(userFollowingList.getFollowings().contains("John"));
    }
}
