/*
 * Class Name: HabitEventOverviewAdapter
 *
 * Version: Version 1.0
 *
 * Date: October 23rd, 2017
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Habit Event Overview Recycler View Adapter
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */

public class HabitEventOverviewAdapter extends RecyclerView.Adapter<HabitEventOverviewAdapter.HabitEventOverviewViewHolder> {
    public static final String SELECTED_HABIT_EVENT_POSITION = "SELECTED_HABIT_EVENT_POSITION";

    private Context mContext;

    /**
     * Constructor for HabitEventOverview Adapter
     */
    public HabitEventOverviewAdapter(Context context) {
        mContext = context;
    }

    /**
     * This function creates a view holder for each HabitEventOverview item displayed in Habit History
     *
     * @param parent: The parent which contains the ViewHolder
     * @param viewType: The view type of this view holder
     * @return
     */
    @Override
    public HabitEventOverviewAdapter.HabitEventOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_habitevent_overview, parent, false);
        HabitEventOverviewViewHolder habitEventOverviewViewHolder = new HabitEventOverviewViewHolder(view);

        return habitEventOverviewViewHolder;
    }

    /**
     * Bind the HabitEventViewHolder with the HabitEvent object at the specific position
     *
     * @param holder: HabitEventOverviewViewHolder for the HabitEvent object at the position in the List
     * @param position: The position of the HabitEvent object in the List to be bound with the viewHolder
     */
    @Override
    public void onBindViewHolder(HabitEventOverviewAdapter.HabitEventOverviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return HabitHistoryActivity.getmHabitEventList().getHabitEvents().size();
    }

    /**
     * View Holder for each HabitEvent object displayed in HabitHistory
     */
    class HabitEventOverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView habitEventImgView;
        private TextView habitEventCommentTextView;
        private TextView habitEventDateTextView;

        public HabitEventOverviewViewHolder(View itemView) {
            super(itemView);

            habitEventImgView = (ImageView) itemView.findViewById(R.id.habitevent_photo);
            habitEventCommentTextView = (TextView) itemView.findViewById(R.id.habitevent_comment);
            habitEventDateTextView = (TextView) itemView.findViewById(R.id.habitevent_date);

            itemView.setOnClickListener(this);
        }

        /**
         * Bind the HabitEventViewHolder with the HabitEvent object at the specific position
         *
         * @param position: The position of the HabitEvent object in the List to be bound with the viewHolder
         */
        private void bind(int position) {
            // Get the habit event object at the specific position in the Habit History's list
            HabitEvent habitEvent_pos = HabitHistoryActivity.getmHabitEventList().getHabitEvents().get(position);

            // Set the comment and date
            habitEventCommentTextView.setText(habitEvent_pos.getComments());
            habitEventDateTextView.setText(habitEvent_pos.getStartDate().toString());

            //TODO set image
        }

        @Override
        public void onClick(View view) {
            // The position of the HabitEvent in the list that the user clicks
            int adapterPosition = getAdapterPosition();

            // The selected HabitEvent object
            HabitEvent selected_HabitEvent = HabitHistoryActivity.getmHabitEventList().getHabitEvents().get(adapterPosition);

            // Start HabitEvent Detail Activity
            // Show the details of the selected HabitEvent object in HabitEvent Detail Screen
            Intent habitEventDetail_Intent = new Intent(mContext, HabitEventDetailActivity.class);
            // Pass the position of the selected HabitEvent
            habitEventDetail_Intent.putExtra(SELECTED_HABIT_EVENT_POSITION,adapterPosition);

            mContext.startActivity(habitEventDetail_Intent);
        }
    }
}
