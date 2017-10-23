/*
 * Class Name: HabitListTest
 *
 * Version: Version 1.0
 *
 * Date: October 23rd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit Test for HabitList class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitListTest extends ActivityInstrumentationTestCase2 {
    public HabitListTest(){
        super(com.example.cmput301f17t19.echoes.MyHabitsActivity.class);
    }

    /**
     * Test for add(Habit habit) in HabitList class
     */
    public void testAdd() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2017-10-01");

        Habit habit = new Habit("testHabit", "test", date, new Plan());

        HabitList habitList = new HabitList();
        habitList.add(habit);

        assertTrue(habitList.hasHabitEvent(habit));
    }

    /**
     * Test for remove(int idx) in HabitList class
     */
    public void testRemove() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse("2017-10-01");
        Date date2 = simpleDateFormat.parse("2017-10-01");

        Habit habit1 = new Habit("testHabit1", "test", date1, new Plan());
        Habit habit2 = new Habit("testHabit2", "test", date2, new Plan());

        HabitList habitList = new HabitList();
        habitList.add(habit1);
        habitList.add(habit2);

        habitList.remove(0);

        assertFalse(habitList.hasHabitEvent(habit1));
    }

    /**
     * Test for hasHabitEvent(Habit habit) in HabitList class
     */
    public void testHasHabitEvent() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2017-10-01");

        Habit habit = new Habit("testHabit", "test", date, new Plan());

        HabitList habitList = new HabitList();
        habitList.add(habit);

        assertTrue(habitList.hasHabitEvent(habit));
    }
}
