/*
 * Class Name: ElasticSearchController
 *
 * Version: Version 1.0
 *
 * Date: October 30th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.test.ActivityInstrumentationTestCase2;

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
        super(com.example.cmput301f17t19.echoes.MainActivity.class);
    }

    /**
     * Test for AddNewUserProfileTask
     */
    public void testAddNewUserProfileTask() {
        UserProfile dummyUser = new UserProfile("dummy1", "dummy1@gmail.com", "7803929483", "no comment", null);

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
    }

    /**
     * Test for GetUserProfileTask
     */
    public void testGetUserProfileTask() {
        UserProfile getDummyUser = new UserProfile("GetdummyT", "GetdummyT@gmail.com", "7803929483", "no comment", null);

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
}
