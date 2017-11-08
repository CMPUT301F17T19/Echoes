/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

include java.util.Date;
include java.time.LocalTime;

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
  
    public ArrayList<DailySchedule> plan;  //schedule[SUNDAY] should be all scheduled times for sunday   
  
    public getToDo(Day) throws InvalidDayException{
        if (DAY >= NUM_DAYS) {
            throw new InvalidDayException();
        }
        else {
            return plan.getIndex(DAY);
        }
    }  
}
