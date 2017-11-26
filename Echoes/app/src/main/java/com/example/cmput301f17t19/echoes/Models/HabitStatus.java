/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Compute Habit Status
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitStatus {

    private UserProfile mUserProfile;
    private Habit mHabit;

    // The given habit type
    private String mHabitType;
    // The plan of the given habit
    private Plan mHabitPlan;

    /**
     * Constructor for Habit Status
     *
     * @param userProfile: UserProfile, the user profile
     * @param habit: Habit, the selected Habit
     */
    public HabitStatus(UserProfile userProfile, Habit habit) {
        this.mUserProfile = userProfile;
        this.mHabit = habit;

        this.mHabitType = mHabit.getName();
        this.mHabitPlan = mHabit.getPlan();
    }

    /**
     * Compute statistical habit status of the given habit, shows how closely I am following its plan
     *
     * @return float: the statistical habit status
     */
    public float statisticalPlannedHabitStatus(){
        // num of total habit event with the given habit type / num of total planned habit event with the given habit type
        int totalDonePlanned = totalDonePlannedEvents();
        int totalPlanned = totalPlannedEvent();

        float statPlanned_habitStatus = 0;

        if (totalPlanned != 0) {
            statPlanned_habitStatus = (float) totalDonePlanned/totalPlanned;
        }

        return statPlanned_habitStatus;
    }

    /**
     * Return the number of total finished habit events with the given habit type following the plan
     *
     * @return int, the number of finished habit events of the given habit type following the plan
     */
    private int totalDonePlannedEvents() {
        // The finished habit events
        ArrayList<HabitEvent> doneHabitEvents = mUserProfile.getHabit_event_list().getHabitEvents();

        int totalDonePlanned = 0;

        for (HabitEvent habitEvent : doneHabitEvents) {
            // For the given habit type
            if (habitEvent.getTitle().equals(mHabitType)) {
                // The date of this habit event done
                Date habitEventDate = habitEvent.getStartDate();

                Calendar eventCalender = Calendar.getInstance();
                eventCalender.setTime(habitEventDate);

                if (mHabitPlan.getSchedule().get(eventCalender.get(Calendar.DAY_OF_WEEK)-1)) {
                    totalDonePlanned += 1;
                }
            }
        }


        return totalDonePlanned;
    }

    /**
     * Return the number of total planned habit events with the given habit type
     *
     * Reference: https://stackoverflow.com/questions/4600034/calculate-number-of-weekdays-between-two-dates-in-java
     *
     * @return int, the number of planned habit events of the given habit type
     */
    private int totalPlannedEvent() {
        int totalPlanned = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // The start date string of the given habit
        String startDate_str = simpleDateFormat.format(mHabit.getStartDate());
        // The current date string
        String currentDate_str = simpleDateFormat.format(new Date());

        try {
            // The start Date
            Date startDate = simpleDateFormat.parse(startDate_str);
            // The current Date
            Date currentDate = simpleDateFormat.parse(currentDate_str);

            // Calender for start Date
            Calendar startCalender = Calendar.getInstance();
            startCalender.setTime(startDate);

            // Calender for current Date
            Calendar currentCalender = Calendar.getInstance();
            currentCalender.setTime(currentDate);

            while(startCalender.getTimeInMillis() <= currentCalender.getTimeInMillis()) {
                // Check if the day is planned day
                if (mHabitPlan.getSchedule().get(startCalender.get(Calendar.DAY_OF_WEEK)-1)) {
                    totalPlanned += 1;
                }

                // Increment startDate by 1
                startCalender.add(Calendar.DAY_OF_MONTH, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return totalPlanned;
    }

    /**
     * Update the whole Habits status of the login user
     */
    public static UserProfile updateAllHabitsStatus(UserProfile loginUserProfile) {
        // Update the Habit Status for the login user for all habits
        HabitList habitList = loginUserProfile.getHabit_list();
        ArrayList<Habit> userHabits = habitList.getHabits();

        for (int i = 0; i < userHabits.size(); i++) {
            // The status of this habit
            HabitStatus habitStatus = new HabitStatus(loginUserProfile, userHabits.get(i));
            // Set the status of this habit
            userHabits.get(i).setProgress(habitStatus.statisticalPlannedHabitStatus());
        }

        habitList.setHabits(userHabits);

        loginUserProfile.setHabit_list(habitList);

        return loginUserProfile;
    }
}
