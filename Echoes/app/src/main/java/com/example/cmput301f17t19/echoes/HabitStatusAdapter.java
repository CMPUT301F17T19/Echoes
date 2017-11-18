/*
 * Class Name: HabitStatusAdapter
 *
 * Version: Version 1.0
 *
 * Date: November 17th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.cmput301f17t19.echoes.FollowingHabitEventsActivity.FOLLOWINGHABITEVENT_TAG;

/**
 * Habit Status Recycler View Adapter
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitStatusAdapter extends RecyclerView.Adapter<HabitStatusAdapter.HabitStatusViewHolder>{

    private Context mContext;

    /**
     * Constructor for HabitStatus Adapter
     */
    public HabitStatusAdapter(Context context) {
        mContext = context;
    }

    /**
     * This function creates a view holder for each HabitStatus item displayed in following habits status list
     *
     * @param parent: The parent which contains the ViewHolder
     * @param viewType: The view type of this view holder
     * @return HabitStatusAdapter.HabitStatusViewHolder
     */
    @Override
    public HabitStatusAdapter.HabitStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_habit_status, parent, false);
        HabitStatusViewHolder habitStatusViewHolder = new HabitStatusViewHolder(view);

        return habitStatusViewHolder;
    }

    /**
     * Bind the HabitStatusViewHolder with the habit status object at the specific position
     *
     * @param holder: HabitStatusViewHolder for the habit status object at the position in the List
     * @param position: The position of the Habit status object in the List to be bound with the viewHolder
     */
    @Override
    public void onBindViewHolder(HabitStatusAdapter.HabitStatusViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        // Return the size of myFollowingHabitsStatuses of the logged-in user
        return HabitsFollowingActivity.getMyFollowingHabitsStatuses().size();
    }

    /**
     * View Holder for each HabitStatus object displayed in HabitsFollowing
     */
    class HabitStatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView userProfileImage;
        private TextView userName;
        private TextView habitName_TextView;
        private TextView habitStatusNum_TextView;


        public HabitStatusViewHolder(View itemView) {


            super(itemView);

            userProfileImage = (ImageView) itemView.findViewById(R.id.user_profile_img);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            habitName_TextView = (TextView) itemView.findViewById(R.id.habitName_TextView);
            habitStatusNum_TextView = (TextView) itemView.findViewById(R.id.habit_status_number_TextView);

            itemView.setOnClickListener(this);
        }

        /**
         * Bind the HabitStatusViewHolder with the FollowingHabitStatus object at the specific position
         *
         * @param position: The position of the FollowingHabitStatus object in the List to be bound with the viewHolder
         */
        private void bind(int position) {
            // Get the FollowingHabitStatus object at the specific position in list
            FollowingHabitsStatus followingHabitsStatus_pos = HabitsFollowingActivity.getMyFollowingHabitsStatuses().get(position);

            // Set the image
            if (followingHabitsStatus_pos.getFollowingUserProfileImg() != null) {
                userProfileImage.setImageBitmap(BitmapFactory.decodeByteArray(followingHabitsStatus_pos.getFollowingUserProfileImg(), 0, followingHabitsStatus_pos.getFollowingUserProfileImg().length));
            }

            // Set the username
            userName.setText(followingHabitsStatus_pos.getFollowingUsername());

            // Set the habit name and status
            Habit thisHabit = followingHabitsStatus_pos.getFollowingHabit();
            habitName_TextView.setText(thisHabit.getName());
            habitStatusNum_TextView.setText(Float.toString(thisHabit.getProgress()));
        }

        /**
         * Send the intent to open the HabitEvents Activity of selected Habit Status
         *
         * @param view: View, the view of Habit Status clicked
         */
        @Override
        public void onClick(View view) {
            // The selected FollowingHabitsStatus
            FollowingHabitsStatus selected_FollowingHabitsStatus = HabitsFollowingActivity.getMyFollowingHabitsStatuses().get(getAdapterPosition());
            // The habit event list of this Habit
            ArrayList<HabitEvent> habit_HabitEvents = selected_FollowingHabitsStatus.getFollowingHabitEvents();

            Intent followingHabit_HabitEvents_Intent = new Intent(mContext, FollowingHabitEventsActivity.class);
            followingHabit_HabitEvents_Intent.putExtra(FOLLOWINGHABITEVENT_TAG, habit_HabitEvents);

            mContext.startActivity(followingHabit_HabitEvents_Intent);
        }
    }
}
