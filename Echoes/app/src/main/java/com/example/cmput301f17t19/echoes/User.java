/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.util.ArrayList;

/**
 * Created by CrackCrack on 2017-10-22.
 */

public class User {

    private String id;
    private String username;
    protected ArrayList<Request> requests;

    public  User(String username){
        requests = new ArrayList<>();
    }

    public void addRequest(Request request){
        requests.add(request);
    }


    //generate unique id using hashing function according to the username string
    public void setID(String id){
        this.id = id;
    }



}
