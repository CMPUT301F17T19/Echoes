/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Exceptions.ArgTooLongException;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by taijieyang on 2017/10/23.
 */

public class HabitEventTest extends ActivityInstrumentationTestCase2 {

    public HabitEventTest() {
        super(HabitEvent.class);
    }

    private HabitEvent createTestHabitEvent() throws ParseException {
        String habitEventTitle = "Drink Water";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date habitEventDate = simpleDateFormat.parse("2017-11-11");

        String habitEventUsername = "John Doe";

        HabitEvent testHabitEvent = new HabitEvent(habitEventTitle, habitEventDate, habitEventUsername);

        return testHabitEvent;
    }

    public void testGetTitle() throws ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        assertTrue(testHabitEvent.getTitle().equals("Drink Water"));
        assertFalse(testHabitEvent.getTitle().equals("Watch TV"));
    }

    public void testGetDate() throws ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        String testHabitDate_str = (new SimpleDateFormat("yyyy-MM-dd")).format(testHabitEvent.getStartDate());

        assertTrue(testHabitDate_str.equals("2017-11-11"));
        assertFalse(testHabitDate_str.equals("2017-11-10"));
    }

    public void testGetComments() throws ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        assertEquals(testHabitEvent.getComments(), "");
    }

    public void testGetEventPhoto() throws ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        assertEquals(testHabitEvent.getEventPhoto(), null);
    }

    public void testSetTitle() throws ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        testHabitEvent.setTitle("Watch TV");

        assertEquals(testHabitEvent.getTitle(), "Watch TV");
    }

    public void testSetDate() throws ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date habitEventDate = simpleDateFormat.parse("2017-11-10");

        testHabitEvent.setStartDate(habitEventDate);

        String newHabitEventDate_str = simpleDateFormat.format(testHabitEvent.getStartDate());

        assertEquals(newHabitEventDate_str, "2017-11-10");
    }

    public void testSetComments() throws ArgTooLongException, ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        String newComment = "NA NA NA";

        testHabitEvent.setComments(newComment);

        assertTrue(testHabitEvent.getComments().equals("NA NA NA"));
        assertFalse(testHabitEvent.getComments().equals(""));
    }

    public void testSetEventPhoto() throws ParseException {
        HabitEvent testHabitEvent = createTestHabitEvent();

        testHabitEvent.setEventPhoto(new byte[1]);

        assertTrue(testHabitEvent.getEventPhoto().length == 1);
    }
}
