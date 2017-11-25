/*
 * Class Name: FollowingHabitsStatus
 *
 * Version: Version 1.0
 *
 * Date: November 17th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

/**
 * The Habit Status for the given username (from my followings)
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class FollowingHabitsStatus {

    private String followingUsername;
    private byte[] followingUserProfileImg;
    private Habit followingHabit;
    private HabitEvent followingMostRecentHabitEvents;

    /**
     * Constructor for FollowingHabitsStatus object
     *
     * @param following_username: String, username of the following
     * @param following_userprofileImg: byte[], the byte array of the profile image of the following user
     * @param following_habit: Habit, habit of the following
     * @param followingMostRecentHabit_Events: HabitEvent, the most recent habit events of the following's this habit
     */
    public FollowingHabitsStatus(String following_username, byte[] following_userprofileImg, Habit following_habit, HabitEvent followingMostRecentHabit_Events) {
        followingUsername = following_username;
        followingUserProfileImg = following_userprofileImg;
        followingHabit = following_habit;
        followingMostRecentHabitEvents = followingMostRecentHabit_Events;
    }

    /**
     * Get the username of the following
     */
    public String getFollowingUsername() {
        return followingUsername;
    }

    /**
     * Get the profile image of the following
     */
    public byte[] getFollowingUserProfileImg() {
        return followingUserProfileImg;
    }

    /**
     * Get the following habit
     */
    public Habit getFollowingHabit() {
        return followingHabit;
    }

    /**
     * Get the most recent habit events of this habit
     */
    public HabitEvent getFollowingMostRecentHabitEvent() {
        return followingMostRecentHabitEvents;
    }
}
