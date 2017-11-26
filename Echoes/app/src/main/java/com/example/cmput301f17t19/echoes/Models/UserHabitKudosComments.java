/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Models;

import java.util.ArrayList;

/**
 * UserHabitKudosComments class
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserHabitKudosComments {
    // The following username
    private String followingUsername;
    // The following's habit title
    private String followingHabitTitle;
    // The string array list of kudos that the user get from other users, value is the username of other users
    private ArrayList<String> kudos_usernames;
    // The string array list of comments that the user get from other users, value is the username giving comments
    private ArrayList<String> comments_usernames;
    // The string array list of comments content
    private ArrayList<String> comments_contents;

    /**
     * Constructor for UserHabitKudosComments
     *
     * @param followingUsername: String, the following username
     * @param followingHabitTitle: String, the following habit title
     */
    public UserHabitKudosComments(String followingUsername, String followingHabitTitle) {
        this.followingUsername = followingUsername;
        this.followingHabitTitle = followingHabitTitle;
        this.kudos_usernames = new ArrayList<String>();
        this.comments_usernames = new ArrayList<String>();
        this.comments_contents = new ArrayList<String>();
    }

    /**
     * Get the username of the following
     *
     * @return String: the following username
     */
    public String getFollowingUsername() {
        return followingUsername;
    }

    /**
     * Get the following habit title
     *
     * @return String: the following habit title
     */
    public String getFollowingHabitTitle() {
        return followingHabitTitle;
    }

    /**
     * Get the elastic search id of this object
     *
     * @return String: the elastic search id: ownerUserName + followingUsername + followingHabitTitle
     */
    public String getElasticSearchID() {
        return  followingUsername + followingHabitTitle;
    }

    /**
     * Get the array list of username that like this habit
     * @return ArrayList<String>: the array list of username that like this habit
     */
    public ArrayList<String> getKudos_usernames() {
        return kudos_usernames;
    }

    /**
     * Check if the given username in the kudos list
     */
    public boolean isGivenKudos(String username) {
        for (String givenKudosUsername : kudos_usernames) {
            if (givenKudosUsername.equals(username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add the username to kudos list
     */
    public void addKudos(String username) {
        kudos_usernames.add(username);
    }

    /**
     * Remove the username from kudos list
     */
    public void removeKudos(String username) {
        kudos_usernames.remove(username);
    }

    /**
     * Return the total number of kudos
     */
    public int getTotalKudosNum() {
        return kudos_usernames.size();
    }

    /**
     * Get the comments_usernames array list
     */
    public ArrayList<String> getComments_usernames() {
        return comments_usernames;
    }

    /**
     * Get the comments_contents array list
     */
    public ArrayList<String> getComments_contents() {
        return comments_contents;
    }

    /**
     * Add username to comments_usernames list
     */
    public void addUsernameComments(String username) {
        comments_usernames.add(username);
    }

    /**
     * Add comment content
     */
    public void addCommentContent(String content) {
        comments_contents.add(content);
    }
}
