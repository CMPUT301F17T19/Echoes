/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.media.Image;

import java.util.Date;

/**
 * Created by taijieyang on 2017/10/22.
 */

public class HabitEvent {
    public String Title;
    public String Reason;
    public Date StartDate;
    public String Comments;
    public Image EventPhoto;


    public HabitEvent(String title, String reason, Date startDate){
        this.Title = title;
        this.Reason = reason;
        this.StartDate = startDate;
    }


    public String getTitle(){
        return this.Title;
    }

    public String getReason(){
        return this.Reason;
    }

    public Date getStartDate(){
        return this.StartDate;
    }

    public String getComments(){
        return this.Comments;
    }

    public Image getEventPhoto(){
        return this.EventPhoto;
    }

    public void setTitle(String title){
        this.Title = title;
    }

    public void setReason(String reason){
        this.Reason = reason;
    }

    public void setStartDate(Date startDate){
        this.StartDate = startDate;
    }

    public void setComments(String comments){
        if (comments.length() > 20)
            throw new ArgTooLongException();
        else
            this.Comments = comments;

    }

    public void setEventPhoto(Image eventPhoto){

        //TODO storage for each photographic image to be under 65536 bytes.

        this.EventPhoto = eventPhoto;
    }
}
