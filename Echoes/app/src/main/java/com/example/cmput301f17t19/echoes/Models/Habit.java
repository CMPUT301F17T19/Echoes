/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Models;

import java.util.Date;

/**
 * Habit Object class
 *
 * @author Hayden Bauder
 * @version 1.0
 * @since 1.0
 */
public class Habit {

    private String name;
    private String reason;
    private Date startDate;
    private Plan plan;
    private Boolean doneToday; // need getters / setters
    private Float progress;

    /**
     * Constructor for the Habit object
     *
     * @param name: String, the title of a Habit
     * @param reason: String, the reason of a Habit
     * @param startDate: Date, the start date of a Habit
     * @param plan: Plan, the plan of a Habit
     */
    public Habit(String name, String reason, Date startDate, Plan plan) {

        this.name = name;
        this.reason = reason;
        this.startDate = startDate;
        this.plan = plan;
        this.doneToday = Boolean.FALSE;
        this.progress = 0f;
    }

    /**
     * Constructor for the Habit object
     *
     * @param name: String, the title of a Habit
     * @param reason: String, the reason of a Habit
     * @param startDate: Date, the start date of a Habit
     * @param plan: Plan, the plan of a Habit
     * @param progress: Float, how the user follows the plan of this Habit
     */
    public Habit(String name, String reason, Date startDate, Plan plan, Float progress) {
        this(name, reason, startDate, plan);
        this.progress = progress;
        this.doneToday = Boolean.FALSE;
    }

    /*
    public Boolean needToDo() {
        return this.plan.scheduledForToday();
    }
    */

    /**
     * Set the name of the Habit
     *
     * @param name: String, the title of a Habit
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Get the name of the Habit
     *
     * @return String: the title of the Habit
     */
    public String getName() {
        return name;
    }

    /**
     * Set the reason of the Habit
     *
     * @param reason: String, the reason of the Habit
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Get the reason of the Habit
     *
     * @return String: the reason of the Habit
     */
    public String getReason() {
        return reason;
    }

    /**
     * Get the start date of the Habit
     *
     * @return Date: the start date of  the Habit
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of the Habit
     *
     * @param startDate: Date, the start date of  the Habit
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the plan of the Habit
     *
     * @return Plan: the plan of the Habit
     */
    public Plan getPlan() {
        return plan;
    }

    /**
     * Set the plan of the Habit
     *
     * @param plan: Plan, the plan of the Habit
     */
    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    /**
     * Get the progress of the habit
     *
     * @return Float: the progress of the Habit
     */
    public Float getProgress() {
        return progress;
    }

    /**
     * Set the progress of the Habit
     *
     * @param progress: Float, the progress of the Habit
     */
    public void setProgress(Float progress) {
        this.progress = progress;
    }


}

