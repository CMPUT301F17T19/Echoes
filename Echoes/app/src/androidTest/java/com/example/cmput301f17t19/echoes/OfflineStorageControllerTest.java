/*
 * Class Name: OfflineStorageControllerTest
 *
 * Version: Version 1.0
 *
 * Date: October 31st, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Unit Test for Offline Storage Controller class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class OfflineStorageControllerTest extends ActivityInstrumentationTestCase2 {

    public OfflineStorageControllerTest() {
        super(com.example.cmput301f17t19.echoes.MainActivity.class);
    }

    /**
     * Test for isFileExist method in OfflineStorageController class
     */
    public void testIsFileExist() {
        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), "NotExistUser");

        assertFalse(offlineStorageController.isFileExist());

        OfflineStorageController offlineStorageController1 = new OfflineStorageController(getActivity().getApplicationContext(), "dummy1");

        assertTrue(offlineStorageController1.isFileExist());
    }

    /**
     * Test for readFromFile() method in OfflineStorageController class
     */
    public void testReadFromFile() {
        UserProfile userProfile = new UserProfile("dummy1");

        userProfile.setEmailAddress("dummy1@gmail.com");
        userProfile.setPhoneNumber("7803929483");
        userProfile.setComment("no comment");

        HabitList habitList = userProfile.getHabit_list();
        Habit habit1 = new Habit("dum1H1", "test", new Date(), new Plan());
        Habit habit2 = new Habit("dum1H2", "test", new Date(), new Plan());

        habitList.add(habit1);
        habitList.add(habit2);

        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), userProfile.getUserName());

        offlineStorageController.saveInFile(userProfile);

        UserProfile testUserProfile = offlineStorageController.readFromFile();

        assertTrue(testUserProfile.getHabit_list().getHabits().get(0).getName().equals("dum1H1"));
        assertTrue(testUserProfile.getHabit_list().getHabits().get(1).getName().equals("dum1H2"));
        assertTrue(testUserProfile.getHabit_list().getHabits().get(0).getReason().equals("test"));

        habitList.remove(0);

        offlineStorageController.saveInFile(userProfile);

        UserProfile testUserProfile1 = offlineStorageController.readFromFile();

        assertFalse(testUserProfile1.getHabit_list().getHabits().get(0).getName().equals("dum1H1"));
        assertTrue(testUserProfile1.getHabit_list().getHabits().get(0).getName().equals("dum1H2"));
        assertTrue(testUserProfile1.getHabit_list().getHabits().size() == 1);
    }

    /**
     * Test for saveInFile() method in OfflineStorageController class
     */
    public void testSaveInFile() {
        UserProfile userProfile = new UserProfile("dummy2");

        HabitList habitList = userProfile.getHabit_list();
        Habit habit1 = new Habit("dum2H1", "test", new Date(), new Plan());
        Habit habit2 = new Habit("dum2H2", "test", new Date(), new Plan());

        habitList.add(habit1);
        habitList.add(habit2);

        OfflineStorageController offlineStorageController = new OfflineStorageController(getActivity().getApplicationContext(), userProfile.getUserName());

        offlineStorageController.saveInFile(userProfile);

        UserProfile testUserProfile = offlineStorageController.readFromFile();

        assertTrue(testUserProfile.getHabit_list().getHabits().get(0).getName().equals("dum2H1"));
        assertTrue(testUserProfile.getHabit_list().getHabits().get(1).getName().equals("dum2H2"));

    }
}
