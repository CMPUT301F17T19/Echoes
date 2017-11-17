/*
 * Class Name: SentRequest
 *
 * Version: Version 1.0
 *
 * Date: October 22nd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

/**
 * The request that the user sent
 *
 * @author Peter Liang
 * @version 1.0
 * @since 1.0
 */
public class SentRequest extends Follow_Request {
    /**
     * Constructor for the Received Request object, set the username of the user sending the request
     *
     * @param Username: String, the username of the follower
     */
    public SentRequest(String Username) {
        super(Username);
    }
}
