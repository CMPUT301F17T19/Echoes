/*
 * Class Name: ElasticSearchControllerTest
 *
 * Version: Version 1.0
 *
 * Date: October 30th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Unit Test for Elastic Search Controller class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class ElasticSearchControllerTest extends ActivityInstrumentationTestCase2 {

    public ElasticSearchControllerTest(){
        super(com.example.cmput301f17t19.echoes.LoginActivity.class);
    }

    /**
     * Test for AddNewUserProfileTask
     */
    public void testAddNewUserProfileTask() {
        UserProfile dummyUser = new UserProfile("dummy1");
        dummyUser.setEmailAddress("dummy1@gmail.com");
        dummyUser.setPhoneNumber("7803929483");
        dummyUser.setComment("no comment");

        ElasticSearchController.AddNewUserProfileTask addNewUserProfileTask = new ElasticSearchController.AddNewUserProfileTask();
        addNewUserProfileTask.execute(dummyUser);

        ElasticSearchController.CheckUserProfileExistTask checkUserProfileExistTask = new ElasticSearchController.CheckUserProfileExistTask();
        checkUserProfileExistTask.execute("dummy1");

        try {
            Boolean isExist = checkUserProfileExistTask.get();

            assertTrue(isExist);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for CheckUserProfileExistTask
     */
    public void testCheckUserProfileExistTask() {
        ElasticSearchController.CheckUserProfileExistTask checkUserProfileExistTask = new ElasticSearchController.CheckUserProfileExistTask();
        checkUserProfileExistTask.execute("dummyNotExist");

        try {
            Boolean isExist = checkUserProfileExistTask.get();

            assertFalse(isExist);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ElasticSearchController.CheckUserProfileExistTask checkUserProfileExistTask1 = new ElasticSearchController.CheckUserProfileExistTask();
        checkUserProfileExistTask1.execute("dummy1");

        try {
            Boolean isExist = checkUserProfileExistTask1.get();

            assertTrue(isExist);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for GetUserProfileTask
     */
    public void testGetUserProfileTask() {
        UserProfile getDummyUser = new UserProfile("GetdummyT");
        getDummyUser.setEmailAddress("GetdummyT@gmail.com");
        getDummyUser.setPhoneNumber("7803929483");
        getDummyUser.setComment("no comment");

        ElasticSearchController.AddNewUserProfileTask addNewUserProfileTask = new ElasticSearchController.AddNewUserProfileTask();
        addNewUserProfileTask.execute(getDummyUser);

        ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
        getUserProfileTask.execute("GetdummyT");

        try {
            UserProfile getUserProfile = getUserProfileTask.get();

            assertTrue(getDummyUser.getUserName().equals(getUserProfile.getUserName()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for UpdateUserProfileTask when User updates its HabitList
     */
    public void testUpdateUserProfileTaskNewHabits() {
        ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
        getUserProfileTask.execute("dummy1");

        try {
            UserProfile getUserProfile = getUserProfileTask.get();

            HabitList habits = getUserProfile.getHabit_list();

            // Update this habits list
            Habit testHabit1 = new Habit("updateHa1", "testUpdate1", new Date(), new Plan());
            Habit testHabit2 = new Habit("updateHa2", "testUpdate2", new Date(), new Plan());

            habits.add(testHabit1);
            habits.add(testHabit2);

            ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
            updateUserProfileTask.execute(getUserProfile);

            // Get the updated userProfile
            ElasticSearchController.GetUserProfileTask getUserProfileTaskT = new ElasticSearchController.GetUserProfileTask();
            getUserProfileTaskT.execute("dummy1");

            UserProfile getUserProfileT = getUserProfileTaskT.get();

            // Check if habits of this userProfile updated
            HabitList habitsT = getUserProfileT.getHabit_list();

            assertTrue(habitsT.getHabits().get(0).getName().equals("updateHa1"));
            assertTrue(habitsT.getHabits().get(1).getName().equals("updateHa2"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for UpdateUserProfileTask when User updates its HabitEventList
     */
    public void testUpdateUserProfileTaskNewHabitEvents() {
        ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
        getUserProfileTask.execute("dummy1");

        try {
            UserProfile getUserProfile = getUserProfileTask.get();

            HabitEventList habitEvents = getUserProfile.getHabit_event_list();

            // Update this habits list
            HabitEvent testHabitEvent1 = new HabitEvent("updateHaE1", new Date());
            HabitEvent testHabitEvent2 = new HabitEvent("updateHaE2", new Date());

            habitEvents.add(testHabitEvent1);
            habitEvents.add(testHabitEvent2);

            ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
            updateUserProfileTask.execute(getUserProfile);

            // Get the updated userProfile
            ElasticSearchController.GetUserProfileTask getUserProfileTaskT = new ElasticSearchController.GetUserProfileTask();
            getUserProfileTaskT.execute("dummy1");

            UserProfile getUserProfileT = getUserProfileTaskT.get();

            // Check if habits of this userProfile updated
            HabitEventList habitEventsT = getUserProfileT.getHabit_event_list();

            assertTrue(habitEventsT.getHabitEvents().get(0).getTitle().equals("updateHaE1"));
            assertTrue(habitEventsT.getHabitEvents().get(1).getTitle().equals("updateHaE2"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
