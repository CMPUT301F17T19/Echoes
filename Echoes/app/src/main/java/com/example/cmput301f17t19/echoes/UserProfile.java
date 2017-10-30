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
    private String username;  // unique user
    private String email_address;     //user contact email address
    private String phone_number;
    private String comment;
    private Image profile_picture;
    private HabitList habit_list;
    private HabitEventList habit_event_list;
    private ArrayList<following> following_list;
    private ArrayList<follower> follower_list;

    public UserProfile(String username, String email_address, String phone_number, String comment, Image profile_picture) {
        this.username = username;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.comment = comment;
        this.profile_picture = profile_picture;

        this.habit_list = new HabitList();
        this.habit_event_list = new HabitEventList();

        this.following_list = new ArrayList<following>();
        this.follower_list = new ArrayList<follower>();
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

    public Image getProfilePicture(){
        return profile_picture;
    }

    public void setProfilePicture(Image profile_picture){
        this.profile_picture = profile_picture;
    }

    public HabitList getHabit_list() {
        return this.habit_list;
    }

    public HabitEventList getHabit_event_list() {
        return this.habit_event_list;
    }

    public ArrayList<following> getFollowing(){
        return following_list;
    }

    public ArrayList<follower> getFollower_list(){
        return follower_list;
    }




}
