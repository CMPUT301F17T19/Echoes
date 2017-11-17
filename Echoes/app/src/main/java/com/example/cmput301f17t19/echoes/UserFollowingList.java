/*
 * Class Name: UserFollowingList
 *
 * Version: Version 1.0
 *
 * Date: November 17th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.util.ArrayList;

/**
 * The following list for the user
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserFollowingList {
    private String userName;
    private ArrayList<Following> followings;

    public UserFollowingList(String username) {
        this.userName = username;
        followings = new ArrayList<Following>();
    }

    /**
     * Get the username
     *
     * @return String: username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get the following array list
     *
     * @return ArrayList<Following>: array list of followings
     */
    public ArrayList<Following> getFollowings() {
        return followings;
    }
}
