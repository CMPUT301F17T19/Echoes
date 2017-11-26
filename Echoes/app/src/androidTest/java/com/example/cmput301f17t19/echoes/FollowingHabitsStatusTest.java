/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.FollowingHabitsStatus;
import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.Models.Plan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit Test for FollowingHabitsStatus class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class FollowingHabitsStatusTest extends ActivityInstrumentationTestCase2 {

    public FollowingHabitsStatusTest(){
        super(FollowingHabitsStatus.class);
    }

    public FollowingHabitsStatus createTestFollowingHabitsStatusObj () throws ParseException {
        String followingUsername = "John Doe";
        byte[] followingProfileImg = new byte[0];

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date followingHabitDate = simpleDateFormat.parse("2017-11-11");

        Habit followingHabit = new Habit("Drink Water", "For Health", followingHabitDate, new Plan());

        HabitEvent followingRecentHabitEvent = new HabitEvent("Drink Water", followingHabitDate, followingUsername);

        FollowingHabitsStatus followingHabitsStatus = new FollowingHabitsStatus(followingUsername, followingProfileImg, followingHabit, followingRecentHabitEvent);

        return followingHabitsStatus;
    }

    public void testGetFollowingUsername () throws ParseException {
        FollowingHabitsStatus followingHabitsStatus = createTestFollowingHabitsStatusObj();

        assertTrue(followingHabitsStatus.getFollowingUsername().equals("John Doe"));
    }

    public void testGetFollowingUserProfileImg() throws ParseException {
        FollowingHabitsStatus followingHabitsStatus = createTestFollowingHabitsStatusObj();

        assertTrue(followingHabitsStatus.getFollowingUserProfileImg().length == (new byte[0]).length);
    }

    public void testGetFollowingHabit () throws ParseException {
        FollowingHabitsStatus followingHabitsStatus = createTestFollowingHabitsStatusObj();

        assertTrue(followingHabitsStatus.getFollowingHabit().getName().equals("Drink Water"));

        assertTrue(followingHabitsStatus.getFollowingHabit().getReason().equals("For Health"));

        Date habitDate = followingHabitsStatus.getFollowingHabit().getStartDate();
        String habitDate_str = (new SimpleDateFormat("yyyy-MM-dd")).format(habitDate);
        assertTrue(habitDate_str.equals("2017-11-11"));

        Plan habitPlan = followingHabitsStatus.getFollowingHabit().getPlan();
        assertFalse(habitPlan.getSchedule().contains(true));

        assertTrue(followingHabitsStatus.getFollowingHabit().getProgress() == 0f);
    }
}
