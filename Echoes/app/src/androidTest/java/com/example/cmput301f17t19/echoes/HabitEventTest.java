/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by taijieyang on 2017/10/23.
 */

public class HabitEventTest extends ActivityInstrumentationTestCase2 {
    public HabitEventTest() {
        super(com.example.cmput301f17t19.echoes.HabitEvent.class);
    }

    public void testGetTitle(){

        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );

        assertTrue(habitevent.getTitle().equals("title1"));
    }

    public void testGetReason(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );

        assertTrue(habitevent.getReason().equals("reason"));
    }

    public void testGetDate(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );

        assertTrue(habitevent.getStartDate().equals(new Date()));
    }

    public void testGetComments(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        assertTrue(habitevent.getComments() == null);
    }

    public void testGetEventPhoto(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        Image image = null;
        assertTrue(habitevent.getEventPhoto() == null);
    }

    public void testSetTitle(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        habitevent.setTitle("title2");
        assertTrue(habitevent.getTitle().equals("title2"));
    }

    public void testSetReason(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        habitevent.setReason("new reason");
        assertTrue(habitevent.getReason().equals("new reason"));
    }

    public void testSetDate(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );

        assertTrue(habitevent.getStartDate().equals(new Date()));

    }

    public void testSetComments() throws ArgTooLongException {
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        habitevent.setComments("This is a comment");
        assertTrue(habitevent.getComments().equals("This is a comment"));
    }

    public void testSetEventPhoto(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        Image image2 = null;
        habitevent.setEventPhoto(image2);
        assertTrue(habitevent.getEventPhoto() == null);
    }
}
