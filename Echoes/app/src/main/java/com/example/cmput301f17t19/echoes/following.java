/*
 * Class Name: Following
 *
 * Version: Version 1.0
 *
 * Date: November 3rd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.util.concurrent.ExecutionException;

/**
 * Follower has attribute of unique username each user that followed by the logged-in user
 *
 * @author Peter Liang, Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class Following {
    private String username;

    public Following(String Username) {
        this.username = Username;
    }

    /**
     * Get the UserProfile of the user that followed by me from Online database
     */
    public UserProfile getUserProfileOnline() {
        // Read the UserProfile with username from online database
        ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
        getUserProfileTask.execute(username);

        try {
            UserProfile followingUserProfile = getUserProfileTask.get();

            return followingUserProfile;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
