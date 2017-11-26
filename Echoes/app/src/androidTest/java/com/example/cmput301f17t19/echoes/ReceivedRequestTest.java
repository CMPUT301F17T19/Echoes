/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.ReceivedRequest;

/**
 * Unit Test for ReceivedRequest class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class ReceivedRequestTest extends ActivityInstrumentationTestCase2 {

    public ReceivedRequestTest() {
        super(ReceivedRequest.class);
    }

    public void testGetter() {
        String username = "John Doe";

        ReceivedRequest receivedRequest = new ReceivedRequest(username);

        assertEquals(receivedRequest.getUsername(), username);
    }
}
