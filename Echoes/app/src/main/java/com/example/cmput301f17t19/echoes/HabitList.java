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
     * @return Boolean: true, if the HabitList contain the input Habit object
     *                  false, if the HabitList does not contain the input Habit object
     */
    public boolean hasHabit(Habit habit){
        return this.habits.contains(habit);
    }

    /**
     * Check if the HabitList contains the Habit with the same input title
     *
     * @param habit_title: The title of the Habit
     * @return Boolean: true, if the HabitList contain the Habit with the same input title
     *                  false, if the HabitList does not contain the Habit with the same input title
     */
    public boolean hasHabitTitle(String habit_title) {
        boolean isContain = false;
        // Check if the Habit in the arrayList of Habit contains the same title as the input
        for (Habit habit : this.habits) {
            if (habit.getName().equals(habit_title)) {
                isContain = true;

                break;
            }
        }

        return isContain;
    }

    /**
     * Check if the HabitList other than the specified position contains the Habit with the same
     * input title
     *
     * @param habit_title: The title of the Habit
     * @return Boolean: true, if the HabitList contain the Habit with the same input title
     *                  false, if the HabitList does not contain the Habit with the same input title
     */
    public boolean hasHabitTitle(String habit_title, int pos) {
        boolean isContain = false;
        // Check if the Habit in the arrayList of Habit contains the same title as the input
        for (int i = 0; i < this.habits.size(); i++) {
            if (this.habits.get(i).getName().equals(habit_title) && i != pos) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }

    /**
     * Get the habit arraylist
     *
     * @return ArrayList<Habit>: the habit arraylist
     */
    public ArrayList<Habit> getHabits(){
        return this.habits;
    }

    /**
     * Set the habit arrayList
     *
     * @param habits, ArrayList<Habit>, the ArrayList of Habit objects
     */
    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

}
