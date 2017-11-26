/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Models;

import java.util.ArrayList;

/**
 * The Received Request list for the user
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserReceivedRequestsList {
    private String userName;
    private ArrayList<ReceivedRequest> receivedRequests;

    public UserReceivedRequestsList(String username) {
        this.userName = username;
        receivedRequests = new ArrayList<ReceivedRequest>();
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
     * Get the Received request array list
     *
     * @return ArrayList<ReceivedRequest>: array list of ReceivedRequests
     */
    public ArrayList<ReceivedRequest> getReceivedRequests() {
        return receivedRequests;
    }
}
