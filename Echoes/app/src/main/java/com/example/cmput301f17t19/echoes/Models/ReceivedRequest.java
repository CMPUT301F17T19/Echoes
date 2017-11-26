/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Models;

/**
 * The request that the user receives
 *
 * @author Peter Liang
 * @version 1.0
 * @since 1.0
 */
public class ReceivedRequest extends Follow_Request {
    /**
     * Constructor for the Received Request object, set the username of the user sending the request
     *
     * @param Username: String, the username of the follower
     */
    public ReceivedRequest(String Username) {
        super(Username);
    }
}
