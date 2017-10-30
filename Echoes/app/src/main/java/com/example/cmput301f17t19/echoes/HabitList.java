/*
 * Class Name: HabitEventList
 *
 * Version: Version 1.0
 *
 * Date: October 20th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.util.ArrayList;

/**
 * Represents a List of User's habits
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitList {
    private ArrayList<Habit> habits;

    /**
     * Constructs a HabitList object
     */
    public HabitList(){
        habits = new ArrayList<Habit>();
    }

    /**
     * Add the Habit object to the HabitList
     *
     * @param habit: Habit object
     */
    public void add(Habit habit){
        this.habits.add(habit);
    }

    /**
     * Remove the Habit object at the index idx from the HabitList
     *
     * @param idx: integer
     */
    public void remove(int idx){
        this.habits.remove(idx);
    }

    /**
     * Check if the HabitList contains the Habit object
     *
     * @param habit: Habit
     * @return true: if the HabitList contain the input Habit object
     *         false: if the HabitList does not contain the input Habit object
     */
    public boolean hasHabit(Habit habit){
        return this.habits.contains(habit);
    }

    /**
     * Get the habit arraylist
     *
     * @return habits: ArrayList<Habit>
     */
    public ArrayList<Habit> getHabits(){
        return this.habits;
    }

}
