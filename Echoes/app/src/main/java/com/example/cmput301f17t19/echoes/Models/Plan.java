/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Models;

//include java.util.Date;
//include java.util.Calender;
//include java.time.LocalTime;

import java.util.ArrayList;

/**
 * The plan of the Habit
 *
 * @author Hayden Bauder
 * @version 1.0
 * @since 1.0
 */
public class Plan {

    public enum Day {
        SUNDAY,   //0
        MONDAY,   //1
        TUESDAY,  //2
        WEDNESDAY,//3
        THURSDAY, //4
        FRIDAY,   //5
        SATURDAY, //6
        NUM_DAYS
    }

    //IMPLEMENT DailySchedule LATER, for now habits are schedueled on Days only. Not specific times.
    private ArrayList<Boolean> schedule;  //schedule[SUNDAY] should be true if a habit is schedueled for sundays

    /**
     * Constructor for the Plan object, initially all false
     */
    public Plan() {
        // Originally all false
        this.schedule = new ArrayList<Boolean>();

        for (int i = 0; i < 7; i++) {
            this.schedule.add(false);
        }
    }

    /**
     * Set the status of the day
     *
     * @param day: integer, represent each day (Monday, Tuesday, etc.)
     * @param status: Boolean, whether the habit needs to be done on this day
     */
    public void setToDo(int day, Boolean status) {
        schedule.set(day, status);
    }

    /**
     * Get the schedule of the Plan
     *
     * @return ArrayList<Boolean>: The array list of boolean represents whether the habit needs to be done on each day
     */
    public ArrayList<Boolean> getSchedule() {
        return this.schedule;
    }

    /**
     * Get the description of the schedule of the Plan
     *
     * @return scheduleDescription: The String description of the planned schedule
     */
    public ArrayList<String> getScheduleDescription() {
        ArrayList<String> scheduleDescription = new ArrayList<String>();

        for (int day = 0; day < 7; day++) {
            switch (day) {
                case 0:
                    // Sunday
                    if (this.schedule.get(day)) {
                        scheduleDescription.add("Sunday");
                    }

                    break;

                case 1:
                    // Monday
                    if (this.schedule.get(day)) {
                        scheduleDescription.add("Monday");
                    }

                    break;

                case 2:
                    // Tuesday
                    if (this.schedule.get(day)) {
                        scheduleDescription.add("Tuesday");
                    }

                    break;

                case 3:
                    // Wednesday
                    if (this.schedule.get(day)) {
                        scheduleDescription.add("Wednesday");
                    }

                    break;

                case 4:
                    // Thursday
                    if (this.schedule.get(day)) {
                        scheduleDescription.add("Thursday");
                    }

                    break;

                case 5:
                    // Friday
                    if (this.schedule.get(day)) {
                        scheduleDescription.add("Friday");
                    }

                    break;

                case 6:
                    // Saturday
                    if (this.schedule.get(day)) {
                        scheduleDescription.add("Saturday");
                    }

                    break;
            }
        }

        return scheduleDescription;
    }
}
