/*
 * Class Name: FollowingSharingController
 *
 * Version: Version 1.0
 *
 * Date: November 16th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Controller for following and sharing
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class FollowingSharingController {

    /**
     * Check if the username is in the following list of the login user or is in the request list of the login user
     *
     * @param loginUserProfile: UserProfile: the user profile of the login user
     * @param searchedUsername: String: the username of the searched user
     * @return int: 0: the given searched username is in my following list
     *              1: already sent following request to the given searched username
     *              2: not following or sent request
     */
    public static int checkSearchedUserStat(UserProfile loginUserProfile, String searchedUsername) {
        ArrayList<Following> myFollowing = loginUserProfile.getFollowing();
        ArrayList<SentRequest> mySentRequest = loginUserProfile.getSentRequests();

        if (loginUserProfile.isInFollowing(searchedUsername)) {
            // The searched username is in my following list
            return 0;
        } else {
            if (loginUserProfile.isInSentRequests(searchedUsername)) {
                // The searched username is in my sent requests list
                return 1;
            } else {
                // the login user did not follow or send following request to the searched username
                return 2;
            }
        }
    }

    /**
     * Unfollow the given user
     *
     * @param loginUserProfile: UserProfile, user profile of the login user
     * @param unfollowedUserProfile: UserProfile, user profile of the unfollowed user
     * @param activity: Activity
     */
    public static void UnfollowUser(UserProfile loginUserProfile, UserProfile unfollowedUserProfile, Activity activity) {
        // Remove the unfollowed user from the following list of the login user
        ArrayList<Following> loginFollowings = loginUserProfile.getFollowing();

        for(Following following : loginFollowings) {
            if (following.getUsername().equals(unfollowedUserProfile.getUserName())) {
                // Remove
                loginFollowings.remove(following);
                break;
            }
        }

        // Update offline storage of the login user and sync with online
        OfflineStorageController mOfflineStorageController = new OfflineStorageController(activity, loginUserProfile.getUserName());
        mOfflineStorageController.saveInFile(loginUserProfile);
        // Sync login user data with online data
        ElasticSearchController.syncOnlineWithOffline(loginUserProfile);

        // Remove the login user from the follower list of the unfollowed user
        ArrayList<Follower> unfollowedFollower = unfollowedUserProfile.getFollower_list();

        for (Follower follower : unfollowedFollower) {
            if (follower.getUsername().equals(loginUserProfile.getUserName())) {
                // Remove
                unfollowedFollower.remove(follower);
                break;
            }
        }

        // Update the unfollowed user's data online
        ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
        updateUserProfileTask.execute(unfollowedUserProfile);
    }

    /**
     * Sending following request to the searched user
     *
     * @param loginUserProfile: UserProfile, user profile of the login user
     * @param searchedUserProfile: UserProfile, user profile of the searched user
     * @param activity: Activity
     */
    public static void sendFollowingRequest(UserProfile loginUserProfile, UserProfile searchedUserProfile, Activity activity) {
        // Add the searched user to the sent request of the login user
        SentRequest sentRequest = new SentRequest(searchedUserProfile.getUserName());
        loginUserProfile.getSentRequests().add(sentRequest);

        // Update offline storage of the login user and sync with online
        OfflineStorageController mOfflineStorageController = new OfflineStorageController(activity, loginUserProfile.getUserName());
        mOfflineStorageController.saveInFile(loginUserProfile);
        // Sync login user data with online data
        ElasticSearchController.syncOnlineWithOffline(loginUserProfile);

        // Add the login user to the received request of the search user
        ReceivedRequest receivedRequest = new ReceivedRequest(loginUserProfile.getUserName());
        searchedUserProfile.getReceivedRequests().add(receivedRequest);

        // Update the searched user's data online
        ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
        updateUserProfileTask.execute(searchedUserProfile);
    }
}
