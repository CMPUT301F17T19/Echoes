/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.Plan;

/**
 * Unit Test for Plan class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class PlanTest extends ActivityInstrumentationTestCase2 {

    public PlanTest() {
        super(Plan.class);
    }

    public void testSetToDo() {
        Plan plan = new Plan();

        plan.setToDo(0, true);

        for (int i = 0; i < 7; i++){
            if (i == 0) {
                assertTrue(plan.getSchedule().get(i));
            } else {
                assertFalse(plan.getSchedule().get(i));
            }
        }
    }

    public void testGetSchedule() {
        Plan plan = new Plan();

        assertTrue(plan.getSchedule().size() == 7);
        assertTrue(plan.getSchedule().contains(false));
        assertFalse(plan.getSchedule().contains(true));
    }

    public void testGetScheduleDescription() {
        Plan plan = new Plan();

        plan.setToDo(0, true);

        assertTrue(plan.getScheduleDescription().contains("Sunday"));
        assertFalse(plan.getScheduleDescription().contains("Monday"));
    }
}
