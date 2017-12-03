/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Controllers;

import com.example.cmput301f17t19.echoes.Activities.CommentsActivity;
import com.example.cmput301f17t19.echoes.Models.UserHabitKudosComments;

/**
 * Created by shanlu on 2017-12-02.
 */

public class CommentThread extends Thread {
    String followingUsername;
    String followingHabitTitle;

    public CommentThread(String followingUsername, String followingHabitTitle){
        this.followingUsername = followingUsername;
        this.followingHabitTitle = followingHabitTitle;
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                // Pull comment data
                UserHabitKudosComments userHabitKudosComments = FollowingSharingController.getUserHabitKudosComments(followingUsername, followingHabitTitle);

                CommentsActivity.setUserHabitKudosComments(userHabitKudosComments);

                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
