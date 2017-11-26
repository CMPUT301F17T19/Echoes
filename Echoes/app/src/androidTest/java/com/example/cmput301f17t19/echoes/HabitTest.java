/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.Plan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hayden Bauder on 23/10/2017.
 */

public class HabitTest extends ActivityInstrumentationTestCase2 {
    private Habit testHabit;

    public HabitTest() {
        super(Habit.class);

        try {
            this.testHabit = createTestHabit();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Habit createTestHabit() throws ParseException {
        String habitTitle = "Drink Water";
        String habitReason = "For Health";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date habitDate = simpleDateFormat.parse("2017-11-11");

        Habit testHabit = new Habit(habitTitle, habitReason, habitDate, new Plan());

        return testHabit;
    }

    public void testHabitName() {
        if (testHabit != null) {
            assertTrue(testHabit.getName().equals("Drink Water"));
            assertFalse(testHabit.getName().equals("Watch TV"));

            testHabit.setName("Watch TV");
            assertTrue(testHabit.getName().equals("Watch TV"));
        }
    }

    public void testHabitReason() {
        if (testHabit != null) {
            assertTrue(testHabit.getReason().equals("For Health"));
            assertFalse(testHabit.getReason().equals("NA NA NA"));

            testHabit.setReason("NA NA NA");
            assertTrue(testHabit.getReason().equals("NA NA NA"));
        }
    }

    public void testHabitStartDate() {
        if (testHabit != null) {
            String habitDate_str = (new SimpleDateFormat("yyyy-MM-dd")).format(testHabit.getStartDate());
            assertEquals(habitDate_str, "2017-11-11");
            assertFalse(habitDate_str.equals("2017-11-10"));
        }
    }

    public void testHabitPlan() {
        if (testHabit != null) {
            assertTrue(testHabit.getPlan().getSchedule().contains(false));
            assertFalse(testHabit.getPlan().getSchedule().contains(true));
        }
    }

    public void testHabitProgress() {
        if (testHabit != null) {
            assertTrue(testHabit.getProgress() == 0f);
            assertFalse(testHabit.getProgress() == 1.0f);

            testHabit.setProgress(1.0f);
            assertTrue(testHabit.getProgress() == 1.0f);
        }
    }

}
