/*
 * Class Name: HabitOverviewAdapter
 *
 * Version: Version 1.0
 *
 * Date: October 23rd, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Habit Overview Recycler View Adapter
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitOverviewAdapter extends RecyclerView.Adapter<HabitOverviewAdapter.HabitOverviewViewHolder>{
    private Context mContext;

    /**
     * Constructor for HabitOverview Adapter
     */
    public HabitOverviewAdapter(Context context) {
        mContext = context;
    }

    /**
     * This function creates a view holder for each HabitOverview item displayed in My Habits
     *
     * @param parent: The parent which contains the ViewHolder
     * @param viewType: The view type of this view holder
     * @return
     */
    @Override
    public HabitOverviewAdapter.HabitOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_habit_overview, parent, false);
        HabitOverviewViewHolder habitOverviewViewHolder = new HabitOverviewViewHolder(view);

        return habitOverviewViewHolder;
    }

    /**
     * Bind the HabitViewHolder with the Habit object at the specific position
     *
     * @param holder: HabitOverviewViewHolder for the Habit object at the position in the List
     * @param position: The position of the Habit object in the List to be bound with the viewHolder
     */
    @Override
    public void onBindViewHolder(HabitOverviewAdapter.HabitOverviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return MyHabitsActivity.getHabits_MyHabits().size();
    }

    /**
     * View Holder for each HabitEvent object displayed in HabitHistory
     */
    class HabitOverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public HabitOverviewViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * Bind the HabitViewHolder with the HabitE object at the specific position
         *
         * @param position: The position of the Habit object in the List to be bound with the viewHolder
         */
        private void bind(int position) {

        }

        @Override
        public void onClick(View view) {

        }
    }
}
