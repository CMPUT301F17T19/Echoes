/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.HabitStatus;
import com.example.cmput301f17t19.echoes.Models.Plan;
import com.example.cmput301f17t19.echoes.Models.UserProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shanlu on 2017-11-26.
 */

public class HabitStatusTest extends ActivityInstrumentationTestCase2 {

    public HabitStatusTest() {
        super(HabitStatus.class);
    }

    public void testStatisticalPlannedHabitStatus() throws ParseException {
        UserProfile testUserProfile = new UserProfile("John Doe");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date habitDate = simpleDateFormat.parse("2017-11-11");

        Plan habitPlan = new Plan();
        habitPlan.setToDo(0, true);

        Habit testHabit = new Habit("Watch TV", "NA NA NA", habitDate, habitPlan);

        // Add test Habit to the test user profile
        testUserProfile.getHabit_list().add(testHabit);

        HabitStatus habitStatus = new HabitStatus(testUserProfile, testHabit);

        assertTrue(habitStatus.statisticalPlannedHabitStatus() == 0f);
    }
}
