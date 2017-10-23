/*
 * Class Name: HabitEventListTest
 *
 * Version: Version 1.0
 *
 * Date: October 19th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit Test for HabitEventList class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitEventListTest extends ActivityInstrumentationTestCase2<HabitHistoryActivity> {
    public HabitEventListTest(){
        super(com.example.cmput301f17t19.echoes.HabitHistoryActivity.class);
    }

    /**
     * Test for add(HabitEvent habitEvent) in HabitEventList class
     */
    public void testAdd() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2017-10-01");

        HabitEvent habitEvent = new HabitEvent("testHabitEvent", "testAdd", date);

        HabitEventList habitEventList = new HabitEventList();
        habitEventList.add(habitEvent);

        assertTrue(habitEventList.hasHabitEvent(habitEvent));
    }

    /**
     * Test for remove(int idx) in HabitEventList class
     */
    public void testRemove() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse("2017-10-01");
        Date date2 = simpleDateFormat.parse("2017-10-02");

        HabitEvent habitEvent1 = new HabitEvent("testHabitEvent", "testRemove", date1);
        HabitEvent habitEvent2 = new HabitEvent("testHabitEvent", "testRemove", date2);

        HabitEventList habitEventList = new HabitEventList();
        habitEventList.add(habitEvent1);
        habitEventList.add(habitEvent2);

        habitEventList.remove(0);

        assertFalse(habitEventList.hasHabitEvent(habitEvent1));
    }

    /**
     * Test for sortList() in HabitEventList class
     */
    public void testSortList() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse("2017-10-01");
        Date date2 = simpleDateFormat.parse("2017-10-02");
        Date date3 = simpleDateFormat.parse("2017-05-03");
        Date date4 = simpleDateFormat.parse("2016-10-02");
        Date date5 = simpleDateFormat.parse("2014-09-08");
        Date date6 = simpleDateFormat.parse("2015-06-08");

        HabitEvent habitEvent1 = new HabitEvent("testHabitEvent", "testSort", date1);
        HabitEvent habitEvent2 = new HabitEvent("testHabitEvent", "testSort", date2);
        HabitEvent habitEvent3 = new HabitEvent("testHabitEvent", "testSort", date3);
        HabitEvent habitEvent4 = new HabitEvent("testHabitEvent", "testSort", date4);
        HabitEvent habitEvent5 = new HabitEvent("testHabitEvent", "testSort", date5);
        HabitEvent habitEvent6 = new HabitEvent("testHabitEvent", "testSort", date6);

        HabitEventList habitEventList = new HabitEventList();

        habitEventList.add(habitEvent1);
        habitEventList.add(habitEvent2);
        habitEventList.add(habitEvent3);
        habitEventList.add(habitEvent4);
        habitEventList.add(habitEvent5);
        habitEventList.add(habitEvent6);

        habitEventList.sortList();

        // Check if the habitEventList is sorted in reverse chronological order (most recent first)
        for(int i = 0; i < habitEventList.getHabitEvents().size() - 1; i++){
            assertTrue(habitEventList.getHabitEvents().get(i).getStartDate()
                    .compareTo(habitEventList.getHabitEvents().get(i+1).getStartDate()) > 0);
        }
    }

    /**
     * Test for hasHabitEvent(HabitEvent habitEvent) in HabitEventList class
     */
    public void testHasHabitEvent() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2017-10-01");

        HabitEvent habitEvent = new HabitEvent("testHabitEvent", "test", date);

        HabitEventList habitEventList = new HabitEventList();
        habitEventList.add(habitEvent);

        assertTrue(habitEventList.hasHabitEvent(habitEvent));
    }
}
