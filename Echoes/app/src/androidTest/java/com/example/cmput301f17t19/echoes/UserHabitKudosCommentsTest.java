/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.UserHabitKudosComments;

/**
 * Unit test for UserHabitKudosComments class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserHabitKudosCommentsTest extends ActivityInstrumentationTestCase2 {

    public UserHabitKudosCommentsTest() {
        super(UserHabitKudosComments.class);
    }

    public void testGetFollowingUsername() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        assertEquals(userHabitKudosComments.getFollowingUsername(), "John Doe");
    }

    public void testGetFollowingHabitTitle() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        assertEquals(userHabitKudosComments.getFollowingHabitTitle(), "Watch TV");
    }

    public void testGetElasticSearchID() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        assertEquals(userHabitKudosComments.getElasticSearchID(), "John Doe"+"Watch TV");
    }

    public void testGetKudos_usernames() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        assertTrue(userHabitKudosComments.getKudos_usernames().size() == 0);
    }

    public void testIsGivenKudos() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        assertEquals(userHabitKudosComments.isGivenKudos("John"), false);
    }

    public void testAddKudos() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        userHabitKudosComments.addKudos("John");
        assertEquals(userHabitKudosComments.isGivenKudos("John"), true);
    }

    public void testRemoveKudos() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        userHabitKudosComments.addKudos("John");
        userHabitKudosComments.removeKudos("John");

        assertEquals(userHabitKudosComments.isGivenKudos("John"), false);
    }

    public void testGetTotalKudosNum() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        userHabitKudosComments.addKudos("John");
        assertEquals(userHabitKudosComments.getTotalKudosNum(), 1);
    }

    public void testGetComments_usernames() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        assertTrue(userHabitKudosComments.getComments_usernames().size() == 0);
    }

    public void testGetComments_contents() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        assertTrue(userHabitKudosComments.getComments_contents().size() == 0);
    }

    public void testAddUsernameComments() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        userHabitKudosComments.addUsernameComments("John");
        assertTrue(userHabitKudosComments.getComments_usernames().get(0).equals("John"));
    }

    public void testAddCommentContent() {
        UserHabitKudosComments userHabitKudosComments = new UserHabitKudosComments("John Doe", "Watch TV");

        userHabitKudosComments.addCommentContent("Hello");
        assertTrue(userHabitKudosComments.getComments_contents().get(0).equals("Hello"));
    }
}
