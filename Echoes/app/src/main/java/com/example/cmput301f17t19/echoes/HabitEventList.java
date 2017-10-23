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
    private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();

    /**
     * Constructs a HabitEventList object
     */
    public HabitEventList(){

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

    /**
     * Sort the HabitEvent objects in the list in reverse chronological order (most recent coming first).
     */
    public void sortList(){
        Collections.sort(habitEvents, Collections.<HabitEvent>reverseOrder());
    }

    /**
     * Check if the HabitEventList contains the HabitEvent object
     *
     * @param habitEvent: HabitEvent
     * @return true: if the HabitEventList contain the input HabitEvent object
     *         false: if the HabitEventList does not contain the input HabitEvent object
     */
    public boolean hasHabitEvent(HabitEvent habitEvent){
        return this.habitEvents.contains(habitEvent);
    }

    /**
     * Get the habitEvent arraylist
     *
     * @return habitEvents: ArrayList<HabitEvent>
     */
    public ArrayList<HabitEvent> getHabitEvents(){
        return this.habitEvents;
    }
}
