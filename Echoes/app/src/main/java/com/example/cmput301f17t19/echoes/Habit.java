/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.util.Date;

/**
 * Created by Hayden Bauder on 23/10/2017.
 */

public class Habit {

    public String name;
    public String reason;
    public Date startDate;
    public Plan plan;
    public Boolean doneToday; // need getters / setters
    public Float progress;

    public Habit(String name, String reason, Date startDate, Plan plan) {

        this.name = name;
        this.reason = reason;
        this.startDate = startDate;
        this.plan = plan;
        //this.doneToday = False;
        this.progress = 0f;
    }

    public Habit(String name, String reason, Date startDate, Plan plan, Float progress) {
        this(name, reason, startDate, plan);
        this.progress = progress;
    }

    /*
    public Boolean needToDo() {
        return this.plan.scheduledForToday();
    }
    */

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }


}

