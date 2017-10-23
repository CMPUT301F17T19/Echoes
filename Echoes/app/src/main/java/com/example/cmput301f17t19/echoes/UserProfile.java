/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Peter Liang on 2017-10-22.
 */

public class UserProfile {

    protected ArrayList<Request> requests;
    private String id;
    public String username;  // unique user
    public String email_address;     //user contact email address
    public String phone_number;
    public String comment;
    public Image profile_picture;
    public ArrayList<following>following_list;
    public ArrayList<follower>follower_list;

    public UserProfile(String username, String email_address, String phone_number, String comment, Image profile_picture) {
        this.username = username;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.comment = comment;
        this.profile_picture = profile_picture;

        this.following_list = new ArrayList<>();
        this.follower_list = new ArrayList<>();
        this.requests = new ArrayList<>();

    }

    //generate unique id using hashing function according to the username string
    public void setID(String id){
        this.id = id;
    }


    public void addRequest(Request request){
        requests.add(request);
    }

    public String getUserName(){
        return username;
    }

    public String getEmailAddress(){
        return email_address;
    }

    public void  setEmailAddress(String email_address){
        this.email_address = email_address;
    }

    public String getPhoneNumber(){
        return phone_number;
    }

    public void setPhoneNumber(String phone_number){
        this.phone_number =  phone_number;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public Image getProfilePicture(){
        return profile_picture;
    }

    public void setProfilePicture(Image profile_picture){
        this.profile_picture = profile_picture;
    }

    public ArrayList<following> getFollowing(){
        return following_list;
    }

    public ArrayList<follower> getFollower_list(){
        return follower_list;
    }




}
