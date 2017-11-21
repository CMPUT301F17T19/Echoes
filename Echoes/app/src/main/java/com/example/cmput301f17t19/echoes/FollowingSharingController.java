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
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
     * @param searchedUserProfile: String: the UserProfile of the searched user
     * @param context: Context
     * @return int: -1: Network error
     *              0: the given searched username is in my following list
     *              1: already sent following request to the given searched username
     *              2: not following or sent request
     */
    public static int checkSearchedUserStat(UserProfile loginUserProfile, UserProfile searchedUserProfile, Context context) {
        int statIndicator = -2;

        // Get the following list of the login user
        ElasticSearchController.CheckUserFollowingsExistTask checkUserFollowingsExistTask = new ElasticSearchController.CheckUserFollowingsExistTask();
        checkUserFollowingsExistTask.execute(loginUserProfile.getUserName());

        try {
            boolean isExist = checkUserFollowingsExistTask.get();

            if (isExist) {
                // Get the FollowingList of the login user
                ElasticSearchController.GetUserFollowingListTask getUserFollowingListTask = new ElasticSearchController.GetUserFollowingListTask();
                getUserFollowingListTask.execute(loginUserProfile.getUserName());

                UserFollowingList loginUserFollowingList = getUserFollowingListTask.get();

                // Check if searched username in login user's following list
                ArrayList<Following> loginUser_Following = loginUserFollowingList.getFollowings();
                // Set the online following list with offline data
                loginUserProfile.setFollowing(loginUser_Following);
                // Update offline and online data storage
                OfflineStorageController offlineStorageController = new OfflineStorageController(context, loginUserProfile.getUserName());
                offlineStorageController.saveInFile(loginUserProfile);
                ElasticSearchController.syncOnlineWithOffline(loginUserProfile);

                for (Following following : loginUser_Following) {
                    if (following.getUsername().equals(searchedUserProfile.getUserName())) {
                        // Login user is already followed the search user
                        statIndicator = 0;
                        break;
                    }
                }
            } else {
                // Add login user following list to online
                UserFollowingList userFollowingList = new UserFollowingList(loginUserProfile.getUserName());
                ElasticSearchController.AddNewUserFollowingsTask addNewUserFollowingsTask = new ElasticSearchController.AddNewUserFollowingsTask();
                addNewUserFollowingsTask.execute(userFollowingList);
            }

        } catch (InterruptedException e) {
            statIndicator = -1;
            e.printStackTrace();
        } catch (ExecutionException e) {
            statIndicator = -1;
            e.printStackTrace();
        }



        // Get the received requests of searched user
        ElasticSearchController.CheckUserReceivedRequestsExistTask checkUserReceivedRequestsExistTask = new ElasticSearchController.CheckUserReceivedRequestsExistTask();
        checkUserReceivedRequestsExistTask.execute(searchedUserProfile.getUserName());

        try {
            boolean isExist = checkUserFollowingsExistTask.get();

            if (isExist) {
                // Get the ReceivedRequestList of the searched user
                ElasticSearchController.GetUserReceivedRequestsTask getUserReceivedRequestsTask = new ElasticSearchController.GetUserReceivedRequestsTask();
                getUserReceivedRequestsTask.execute(searchedUserProfile.getUserName());

                UserReceivedRequestsList userReceivedRequestsList = getUserReceivedRequestsTask.get();

                // Check if the login username in the received request list of the searched User
                ArrayList<ReceivedRequest> receivedRequests = userReceivedRequestsList.getReceivedRequests();
                for (ReceivedRequest receivedRequest : receivedRequests) {
                    if (receivedRequest.getUsername().equals(loginUserProfile.getUserName())) {
                        // The login user has already sent following request to the searched user
                        statIndicator = 1;
                        break;
                    }
                }


            } else {
                // Add the new ReceivedRequestList of the searched user to online data storage
                UserReceivedRequestsList userReceivedRequestsList = new UserReceivedRequestsList(searchedUserProfile.getUserName());
                ElasticSearchController.AddNewUserReceivedRequestsTask addNewUserReceivedRequestsTask = new ElasticSearchController.AddNewUserReceivedRequestsTask();
                addNewUserReceivedRequestsTask.execute(userReceivedRequestsList);
            }

        } catch (InterruptedException e) {
            statIndicator = -1;
            e.printStackTrace();
        } catch (ExecutionException e) {
            statIndicator = -1;
            e.printStackTrace();
        }

        if (statIndicator == -2) {
            // The login user is not following or sent request to the searched user
            statIndicator = 2;
        }

        return statIndicator;
    }

    /**
     * Sending following request to the searched user
     *
     * @param loginUserProfile: UserProfile, user profile of the login user
     * @param searchedUserProfile: UserProfile, user profile of the searched user
     * @param activity: Activity
     */
    public static void sendFollowingRequest(UserProfile loginUserProfile, UserProfile searchedUserProfile, Activity activity) {
        // Add the login user to the received request of the search user
        ElasticSearchController.CheckUserReceivedRequestsExistTask checkUserReceivedRequestsExistTask = new ElasticSearchController.CheckUserReceivedRequestsExistTask();
        checkUserReceivedRequestsExistTask.execute(searchedUserProfile.getUserName());

        try {
            boolean isExist = checkUserReceivedRequestsExistTask.get();

            if (isExist) {
                ElasticSearchController.GetUserReceivedRequestsTask getUserReceivedRequestsTask = new ElasticSearchController.GetUserReceivedRequestsTask();
                getUserReceivedRequestsTask.execute(searchedUserProfile.getUserName());

                UserReceivedRequestsList userReceivedRequestsList = getUserReceivedRequestsTask.get();

                if (userReceivedRequestsList != null) {
                    userReceivedRequestsList.getReceivedRequests().add(new ReceivedRequest(loginUserProfile.getUserName()));

                    ElasticSearchController.AddNewUserReceivedRequestsTask addNewUserReceivedRequestsTask = new ElasticSearchController.AddNewUserReceivedRequestsTask();
                    addNewUserReceivedRequestsTask.execute(userReceivedRequestsList);
                } else {
                    Log.d("Test", "Network error");
                }

            } else {
                UserReceivedRequestsList userReceivedRequestsList = new UserReceivedRequestsList(searchedUserProfile.getUserName());
                // Add the new received request list to online
                userReceivedRequestsList.getReceivedRequests().add(new ReceivedRequest(loginUserProfile.getUserName()));

                ElasticSearchController.AddNewUserReceivedRequestsTask addNewUserReceivedRequestsTask = new ElasticSearchController.AddNewUserReceivedRequestsTask();
                addNewUserReceivedRequestsTask.execute(userReceivedRequestsList);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * The login user accept the request sent from selectedRequestUsername
     * @param loginUserProfile: UserProfile, the user profile of the login user
     * @param selectedRequestUsername: String, the username of the selected Request
     * @param context: Context
     */
    public static void acceptFollowingRequest(UserProfile loginUserProfile, String selectedRequestUsername, Context context) {
        // Add the selectedRequestUsername to the follower list of the login user
        loginUserProfile.addFollower(new Follower(selectedRequestUsername));

        // Remove the corresponding received request of the login user
        removedSelectedFollowingRequest(loginUserProfile, selectedRequestUsername, context);


        // Add login user to the following list of the user with selectedRequestUsername
        ElasticSearchController.GetUserFollowingListTask getUserFollowingListTask = new ElasticSearchController.GetUserFollowingListTask();
        getUserFollowingListTask.execute(selectedRequestUsername);

        try {
            UserFollowingList userFollowingList = getUserFollowingListTask.get();
            userFollowingList.getFollowings().add(new Following(loginUserProfile.getUserName()));

            // Save online data storage
            ElasticSearchController.UpdateUserFollowingListTask updateUserFollowingListTask = new ElasticSearchController.UpdateUserFollowingListTask();
            updateUserFollowingListTask.execute(userFollowingList);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * The login user decline the request sent from selectedRequestUsername
     * @param loginUserProfile: UserProfile, the user profile of the login user
     * @param selectedRequestUsername: String, the username of the selected Request
     * @param context: Context
     */
    public static void declineFollowingRequest(UserProfile loginUserProfile, String selectedRequestUsername, Context context) {
        // Remove the selectedRequestUsername from the received request list of the login user
        removedSelectedFollowingRequest(loginUserProfile, selectedRequestUsername, context);
    }

    /**
     * Remove the selectedRequestUsername from the received request list of the login user
     *
     * @param loginUserProfile: UserProfile, the user profile of the login user
     * @param selectedRequestUsername: String, the username of the selected Request
     * @param context: Context
     */
    private static void removedSelectedFollowingRequest(UserProfile loginUserProfile, String selectedRequestUsername, Context context) {
        // Remove the corresponding received request of the login user
        ElasticSearchController.GetUserReceivedRequestsTask getUserReceivedRequestsTask = new ElasticSearchController.GetUserReceivedRequestsTask();
        getUserReceivedRequestsTask.execute(loginUserProfile.getUserName());

        try {
            UserReceivedRequestsList userReceivedRequestsList = getUserReceivedRequestsTask.get();

            // The arraylist of the received request of the login user
            ArrayList<ReceivedRequest> receivedRequests = userReceivedRequestsList.getReceivedRequests();

            // Remove the corresponding selected user
            for (ReceivedRequest receivedRequest : receivedRequests) {
                if (receivedRequest.getUsername().equals(selectedRequestUsername)) {
                    receivedRequests.remove(receivedRequest);
                    break;
                }
            }

            // Update online storage
            ElasticSearchController.UpdateUserReceivedRequestsListTask updateUserReceivedRequestsListTask = new ElasticSearchController.UpdateUserReceivedRequestsListTask();
            updateUserReceivedRequestsListTask.execute(userReceivedRequestsList);
            // Update offline UserProfile and sync with online
            loginUserProfile.setReceivedRequest(receivedRequests);
            OfflineStorageController offlineStorageController = new OfflineStorageController(context, loginUserProfile.getUserName());
            offlineStorageController.saveInFile(loginUserProfile);
            ElasticSearchController.syncOnlineWithOffline(loginUserProfile);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return an array list of FollowingHabitsStatus, containint all habits statuses of my followings
     *
     * @param myFollowings: ArrayList<Following>: the users I'm following
     * @return ArrayList<FollowingHabitsStatus>: an array list of FollowingHabitsStatus, containint all habits statuses of my followings
     */
    public static ArrayList<FollowingHabitsStatus> createFollowingHabitsStatuses(ArrayList<Following> myFollowings) {
        ArrayList<FollowingHabitsStatus> myFollowingHabitsStatuses = new ArrayList<FollowingHabitsStatus>();

        for (Following following : myFollowings) {
            // Get the habits list of this following
            // Get the user profile of this following
            ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
            getUserProfileTask.execute(following.getUsername());

            try {
                UserProfile following_UserProfile = getUserProfileTask.get();

                if (following_UserProfile != null) {
                    // Get the HabitList of this user
                    HabitList followingHabitList = following_UserProfile.getHabit_list();
                    ArrayList<Habit> followingHabits = followingHabitList.getHabits();

                    for (Habit habit : followingHabits) {
                        // Get the habitEvent list of this habit
                        ArrayList<HabitEvent> thisHabitEvents = new ArrayList<HabitEvent>();

                        HabitEventList allHabitEventList = following_UserProfile.getHabit_event_list();
                        ArrayList<HabitEvent> allHabitEvents = allHabitEventList.getHabitEvents();

                        for (HabitEvent habitEvent : allHabitEvents) {
                            if (habitEvent.getTitle().equals(habit.getName())) {
                                // Add this habit event to this Habit's HabitEvents list
                                thisHabitEvents.add(habitEvent);
                            }
                        }

                        FollowingHabitsStatus followingHabitsStatus = new FollowingHabitsStatus(following_UserProfile.getUserName(), following_UserProfile.getProfilePicture(), habit, thisHabitEvents);

                        // Add this followingHabitsStatus to array list
                        myFollowingHabitsStatuses.add(followingHabitsStatus);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return myFollowingHabitsStatuses;
    }

    /**
     * Get all most recent habit events from my following
     *
     * @param myFollowings: ArrayList<Following>: the users I'm following
     * @return ArrayList<HabitEvent>: an array list of most recent habit events of my followings
     */
    public static ArrayList<HabitEvent> createFollowingRecentHabitEvents(ArrayList<Following> myFollowings) {
        ArrayList<HabitEvent> recentHabitEvents = new ArrayList<HabitEvent>();

        for (Following following : myFollowings) {
            // Get the habits list of this following
            // Get the user profile of this following
            ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
            getUserProfileTask.execute(following.getUsername());

            try {
                UserProfile following_UserProfile = getUserProfileTask.get();

                if (following_UserProfile != null) {
                    // Habit Types that added in recentHabitEvents
                    ArrayList<String> addedHabitsTypes = new ArrayList<String>();

                    // The habit events of the following
                    ArrayList<HabitEvent> followingHabitEvents = following_UserProfile.getHabit_event_list().getHabitEvents();

                    for (HabitEvent habitEvent : followingHabitEvents) {
                        // Add the most recent habit event for each habit to the array
                        // Check if the habitEvent's type has already been added to recent habit events array
                        if (!addedHabitsTypes.contains(habitEvent.getTitle())) {
                            // add this habit event to the most recent habit event array
                            recentHabitEvents.add(habitEvent);

                            addedHabitsTypes.add(habitEvent.getTitle());
                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return recentHabitEvents;
    }
}
