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
    public void testGetTitle(){

        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );

        assertTrue("test1" == habitevent.getTitle());
    }

    public void testGetReason(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );

        assertTrue("reason" == habitevent.getReason());
    }

    public void testGetDate(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );

        assertTrue(new Date() == habitevent.getStartDate());
    }

    public void testGetComments(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        assertTrue(null == habitevent.getComments());
    }

    public void testGetEventPhoto(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        Image image = null;
        assertTrue(image == habitevent.getEventPhoto());
    }

    public void testSetTitle(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        habitevent.setTitle("title2");
        assertTrue("test2" == habitevent.getTitle());
    }

    public void testSetReason(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        habitevent.setReason("new reason");
        assertTrue("new reason" == habitevent.getReason());
    }

    public void testSetDate(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        habitevent.setStartDate(new Date());
        assertTrue(new Date() == habitevent.getStartDate());

    }

    public void testSetComments(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        habitevent.setComments("This is a comment");
        assertTrue("This is a comment" == habitevent.getComments());
    }

    public void testSetEventPhoto(){
        HabitEvent habitevent = new HabitEvent("title1", "reason", new Date() );
        Image image2 = null;
        habitevent.setEventPhoto(image2);
        assertTrue(image2 == habitevent.getEventPhoto());
    }
}
