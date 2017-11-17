/*
 * Class Name: Follow_Request
 *
 * Version: Version 1.0
 *
 * Date: November 16th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

/**
 * Superclass for Following, Follower, SentRequest, ReceivedRequest
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class Follow_Request {

    private String username;

    /**
     * Constructor for the Follow_Request object, set the username
     *
     * @param Username: String, the username
     */
    public Follow_Request(String Username) {
        this.username = Username;
    }

    /**
     * Get the username
     *
     * @return String: username
     */
    public String getUsername() {
        return username;
    }
}
