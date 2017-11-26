/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t19.echoes.Models.SystemAdministrator;

/**
 * Unit Test for SystemAdministrator class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class SystemAdministratorTest extends ActivityInstrumentationTestCase2 {

    public SystemAdministratorTest() {
        super(SystemAdministrator.class);
    }

    public void testImageStorageConstraint() {
        SystemAdministrator systemAdministrator = new SystemAdministrator("John Doe");

        systemAdministrator.setMax_img_storage_bytes(65536);

        assertEquals(systemAdministrator.getMax_img_storage_bytes(), 65536);
    }
}
