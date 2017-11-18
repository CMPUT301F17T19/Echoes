/*
 * Class Name: Follower
 *
 * Version: Version 1.0
 *
 * Date: November 3rd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

/**
 * Follower has attribute of unique username for each follower of the user
 *
 * @author Peter Liang, Shan Lu
 * @version 1.0
 * @since 1.0
 */

public class Follower extends Follow_Request {

    /**
     * Constructor for the Follower object, set the username of the follower
     *
     * @param Username: String, the username of the follower
     */
    public Follower(String Username) {
        super(Username);
    }
}


