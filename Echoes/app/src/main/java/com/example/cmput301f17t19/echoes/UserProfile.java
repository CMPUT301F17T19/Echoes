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
    private ArrayList<ReceivedRequest> receivedRequests;
    private ArrayList<SentRequest> sentRequests;

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
        this.receivedRequests = new ArrayList<ReceivedRequest>();
        this.sentRequests = new ArrayList<SentRequest>();
    }

    /**
     * generate unique id using hashing function according to the username string
     * @param id
     */
    public void setID(String id){
        this.id = id;
    }

    /**
     * Add the request from other user
     *
     * @param receivedRequest: ReceivedRequest, the following request received from other user
     */
    public void addReceivedRequest(ReceivedRequest receivedRequest){
        receivedRequests.add(receivedRequest);
    }

    /**
     * Remove the received request from other user
     *
     * @param receivedRequest: ReceivedRequest, the following request received from other user
     */
    public void removeReceivedRequest(ReceivedRequest receivedRequest){
        receivedRequests.remove(receivedRequest);
    }

    /**
     * Get the Received requests list
     *
     * @return receivedRequests
     */
    public ArrayList<ReceivedRequest> getReceivedRequests() {
        return receivedRequests;
    }

    /**
     * Add the request sent to other user
     *
     * @param sentRequest: SentRequest, the following request sent to other user
     */
    public void addSentRequest(SentRequest sentRequest){
        sentRequests.add(sentRequest);
    }

    /**
     * Remove the received request sent to other user
     *
     * @param sentRequest: SentRequest, the following request sent to other user
     */
    public void removeSentRequest(SentRequest sentRequest){
        sentRequests.remove(sentRequest);
    }

    /**
     * Get the sent requests list
     *
     * @return sentRequests
     */
    public ArrayList<SentRequest> getSentRequests() {
        return sentRequests;
    }

    /**
     * Check if the given username is in the login user's sent requests
     */
    public boolean isInSentRequests(String given_username) {
        for (SentRequest sentRequest : sentRequests) {
            if (sentRequest.getUsername().equals(given_username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the username
     *
     * @return String: the username of the user
     */
    public String getUserName(){
        return username;
    }

    /**
     * Get the email
     *
     * @return String: the email of the user
     */
    public String getEmailAddress(){
        return email_address;
    }

    /**
     * Set the email of the user
     *
     * @param email_address: String, the email of the user
     */
    public void  setEmailAddress(String email_address){
        this.email_address = email_address;
    }

    /**
     * Get the phone number of the user
     *
     * @return String: the phone number of the user
     */
    public String getPhoneNumber(){
        return phone_number;
    }

    /**
     * Set the phone number of the user
     *
     * @param phone_number: String, the phone number of the user
     */
    public void setPhoneNumber(String phone_number){
        this.phone_number =  phone_number;
    }

    /**
     * Get the bio comment of the user
     *
     * @return String: the bio comment of the user
     */
    public String getComment(){
        return comment;
    }

    /**
     * Set the bio comment of the user
     * @param comment: String, the bio comment of the user
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * Get the profile photo of the user
     *
     * @return byte[]: the profile photo byte array
     */
    public byte[] getProfilePicture(){
        return profile_picture;
    }

    /**
     * Set the profile photo of the user
     *
     * @param profile_picture: byte[], the profile photo byte array
     */
    public void setProfilePicture(byte[] profile_picture){
        this.profile_picture = profile_picture;
    }

    /**
     * Get the HabitList of the user
     *
     * @return HabitList: the HabitList of the user
     */
    public HabitList getHabit_list() {
        return this.habit_list;
    }

    /**
     * Get the HabitEventList of the user
     *
     * @return HabitEventList: the HabitEventList of the user
     */
    public HabitEventList getHabit_event_list() {
        return this.habit_event_list;
    }

    /**
     * Add the following in my following list
     */
    public void addFollowing(Following following) {
        following_list.add(following);
    }

    /**
     * Get the following array of the user
     *
     * @return ArrayList<Following>: the following array of the user
     */
    public ArrayList<Following> getFollowing(){
        return following_list;
    }

    /**
     * Check if the given username in following list
     */
    public boolean isInFollowing(String given_username) {
        for (Following following : following_list) {
            if (following.getUsername().equals(given_username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add the follower in my follower list
     */
    public void addFollower(Follower follower) {
        follower_list.add(follower);
    }

    /**
     * Get the follower array of the user
     *
     * @return ArrayList<Follower>: the follower array of the user
     */
    public ArrayList<Follower> getFollower_list(){
        return follower_list;
    }

}
