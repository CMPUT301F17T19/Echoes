/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Hayden Bauder on 23/10/2017.
 */

public class HabitTest  {
    public Habit testHabit;

    public HabitTest() {
        this.testHabit = new Habit("", "", new Date(), new Plan());
    }

    public void testHabitName() {
        String test = "This is a test name";
        String result;
//        testHabit.setName(test);
        result = testHabit.getName();

        assertEquals(test, result);
    }

    public void testHabitReason() {
        String test = "This is a test reason";
        String result;
//        testHabit.setReason(test);
        result = testHabit.getReason();

        assertEquals(test, result);
    }

    public void testHabitStartDate() {
        Date test = new Date();
        Date result;
        testHabit.setStartDate(test);
        result = testHabit.getStartDate();

        assertEquals(test, result);
    }

    public void testHabitPlan() {
        Plan test = new Plan();
        Plan result;
        testHabit.setPlan(test);
        result = testHabit.getPlan();

        assertEquals(test, result);
    }

    public void testHabitProgress() {
        Float test = 0f;
        Float result;
        testHabit.setProgress(test);
        result = testHabit.getProgress();

        assertEquals(test, result);

        test = 1.0f;
        testHabit.setProgress(test);
        result = testHabit.getProgress();
        assertEquals(test, result);
    }

}
