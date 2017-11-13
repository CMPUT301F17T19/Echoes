/*
 * Class Name: HabitEventList
 *
 * Version: Version 1.0
 *
 * Date: October 19th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a List of User's habit events
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitEventList {
    private ArrayList<HabitEvent> habitEvents;

    /**
     * Constructs a HabitEventList object
     */
    public HabitEventList(){
        habitEvents = new ArrayList<HabitEvent>();
    }

    /**
     * Add the HabitEvent object to the HabitEventList
     *
     * @param habitEvent: HabitEvent object
     */
    public void add(HabitEvent habitEvent){
        this.habitEvents.add(habitEvent);
    }

    /**
     * Remove the HabitEvent object at the index idx from the HabitEventList
     *
     * @param idx: integer
     */
    public void remove(int idx){
        this.habitEvents.remove(idx);
    }

    public HabitEvent get(int idx) { return this.habitEvents.get(idx); }

    /**
     * Sort the HabitEvent objects in the list in reverse chronological order (most recent coming first).
     */
    public void sortList(){
        Collections.sort(habitEvents, Collections.<HabitEvent>reverseOrder());
    }

    /**
     *
     * @return size of the Habit Event List
     */
    public int size(){return this.size();}

    /**
     * Check if the HabitEventList contains the HabitEvent with the given type and event date
     *
     * @param habitType: the type of the habit event
     * @param eventDate: the date string of the habit event
     *
     * @return Boolean: true, if the HabitEventList contain HabitEvent with the given type and event date
     *                  false, if the HabitEventList does not contain HabitEvent with the given type and event date
     */
    public boolean hasHabitEvent(String habitType, String eventDate){
        boolean ifContain = false;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (HabitEvent habitEvent : habitEvents) {
            String type = habitEvent.getTitle();
            String date = simpleDateFormat.format(habitEvent.getStartDate());

            if (habitType.equals(type) && eventDate.equals(date)) {
                ifContain = true;

                break;
            }
        }

        return ifContain;
    }


    public HabitEvent getHabitEvent(int idx){
        return this.habitEvents.get(idx);
    }

    /**
     * Check if the HabitEventList contains the HabitEvent with the given type and event date other than the given position
     *
     * @param habitType: the type of the habit event
     * @param eventDate: the date string of the habit event
     *
     * @return Boolean: true, if the HabitEventList contain HabitEvent with the given type and event date
     *                  false, if the HabitEventList does not contain HabitEvent with the given type and event date
     */
    public boolean hasHabitEvent(String habitType, String eventDate, int selected_pos){
        boolean ifContain = false;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < habitEvents.size(); i++) {
            String type = habitEvents.get(i).getTitle();
            String date = simpleDateFormat.format(habitEvents.get(i).getStartDate());

            if (habitType.equals(type) && eventDate.equals(date) && i != selected_pos) {
                ifContain = true;
                break;
            }
        }

        return ifContain;
    }

    /**
     * Get the habitEvent arraylist
     *
     * @return ArrayList<HabitEvent>: the habitEvent arraylist
     */
    public ArrayList<HabitEvent> getHabitEvents(){
        return this.habitEvents;
    }

    /**
     * Set the habitEvent arraylist
     *
     * @param HabitEvents: ArrayList<HabitEvent>, the habitEvent arraylist
     */
    public void setHabitEvents(ArrayList<HabitEvent> HabitEvents){
        this.habitEvents=  HabitEvents;
    }
}
