/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

//include java.util.Date;
//include java.util.Calender;
//include java.time.LocalTime;

import java.util.ArrayList;

/**
 * Created by Hayden Bauder on 23/10/2017.
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
    public ArrayList<Boolean> schedule;  //schedule[SUNDAY] should be true if a habit is schedueled for sundays

    public Plan() {
        this.schedule = new ArrayList<Boolean>();
    }

//    public Boolean getToDo(Day day) throws InvalidDayException{
//        if (DAY >= NUM_DAYS) {
//            throw new InvalidDayException();
//        }
//        else {
//            return schedule.get(day);
//        }
//    }
//
//    public void setToDo(Day day, Boolean status) {
//        schedule.set(day, status);
//    }
//
//    public Boolean scheduledForToday() {
//        Calendar c = Calendar.getInstance();
//        day = c.get(Calendar.DAY_OF_WEEK); //Needs testing
//
//        return getToDo(day - 1);    // subtract 1, because calender does 1 - 7, we do 0 - 6
//    }

}
