/*
 * Class Name: UserProfile
 *
 * Version: Version 1.0
 *
 * Date: October 22nd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.util.ArrayList;

/**
 * User Profile, represents each user
 *
 * @author Peter Liang
 * @version 1.0
 * @since 1.0
 */
public class UserProfile {

    private String id;
    private String username;  // unique user
    private String email_address;     //user contact email address
    private String phone_number;
    private String comment;
    // Byte array of the image
    private byte[] profile_picture;
    private HabitList habit_list;
    private HabitEventList habit_event_list;
    private ArrayList<Following> following_list;
    private ArrayList<Follower> follower_list;
    private ArrayList<Request> requests;

    /**
     * Constructor for the UserProfile for each user
     *
     * @param username: String, unique username for each user
     *
     */
    public UserProfile(String username) {
        this.username = username;

        this.profile_picture = null;
        this.email_address = null;
        this.phone_number = null;
        this.comment = null;

        this.habit_list = new HabitList();
        this.habit_event_list = new HabitEventList();

        this.following_list = new ArrayList<Following>();
        this.follower_list = new ArrayList<Follower>();
        this.requests = new ArrayList<Request>();
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

    public byte[] getProfilePicture(){
        return profile_picture;
    }

    public void setProfilePicture(byte[] profile_picture){
        this.profile_picture = profile_picture;
    }

    public HabitList getHabit_list() {
        return this.habit_list;
    }

    public HabitEventList getHabit_event_list() {
        return this.habit_event_list;
    }

    public ArrayList<Following> getFollowing(){
        return following_list;
    }

    public ArrayList<Follower> getFollower_list(){
        return follower_list;
    }

}
