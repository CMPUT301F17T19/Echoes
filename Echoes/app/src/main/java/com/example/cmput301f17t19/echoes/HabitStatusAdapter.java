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
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    class HabitStatusViewHolder extends RecyclerView.ViewHolder {

        private ImageView userProfileImage;
        private TextView userName;

        private TextView habitTitleTextView;
        private TextView habitReasonTextView;
        private TextView habitDateTextView;
        private TextView habitStatusTextView;
        private ProgressBar habitStatusProgressBar;
        private TextView habitPlanTextView;

        private TextView habitEventHabitTypeTextView;
        private ImageView habitEventImgView;
        private TextView habitEventCommentTextView;
        private TextView habitEventDateTextView;

        private LinearLayout habitEventLayout;
        private TextView noRecentHabitEvent_TextView;


        public HabitStatusViewHolder(View itemView) {


            super(itemView);

            userProfileImage = (ImageView) itemView.findViewById(R.id.user_profile_img);
            userName = (TextView) itemView.findViewById(R.id.user_name);

            habitTitleTextView = (TextView) itemView.findViewById(R.id.habitOverview_title);
            habitReasonTextView = (TextView) itemView.findViewById(R.id.habitOverview_reason);
            habitDateTextView = (TextView) itemView.findViewById(R.id.habitOverview_date);
            habitStatusTextView = (TextView) itemView.findViewById(R.id.habitOverview_status);
            habitStatusProgressBar = (ProgressBar) itemView.findViewById(R.id.habit_status_progressBar);
            habitPlanTextView = (TextView) itemView.findViewById(R.id.habitPlanTextView);

            habitEventHabitTypeTextView = (TextView) itemView.findViewById(R.id.habitevent_type_textView);
            habitEventImgView = (ImageView) itemView.findViewById(R.id.habitevent_photo);
            habitEventCommentTextView = (TextView) itemView.findViewById(R.id.habitevent_comment);
            habitEventDateTextView = (TextView) itemView.findViewById(R.id.habitevent_date);

            habitEventLayout = (LinearLayout) itemView.findViewById(R.id.followingHabitMostRecentEvent_layout);
            noRecentHabitEvent_TextView = (TextView) itemView.findViewById(R.id.no_mostRecentEvent);
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

            // Set the habit overview
            Habit thisHabit = followingHabitsStatus_pos.getFollowingHabit();
            // Set the comment and date
            habitTitleTextView.setText(thisHabit.getName());
            habitReasonTextView.setText(thisHabit.getReason());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            habitDateTextView.setText(simpleDateFormat.format(thisHabit.getStartDate()));

            // Get the array list of string description of habit plan
            ArrayList<String> planDescription = thisHabit.getPlan().getScheduleDescription();
            String planDescription_str = "" ;

            for (String plan_str : planDescription) {
                planDescription_str += plan_str + " ";
            }

            habitPlanTextView.setText(planDescription_str);

            habitStatusTextView.setText(Float.toString(thisHabit.getProgress() * 100) + "%");

            habitStatusProgressBar.setProgress(Math.round(thisHabit.getProgress() * 100));
            habitStatusProgressBar.setMax(100);

            // Set the most recent event
            HabitEvent mostRecentHabitEvent = followingHabitsStatus_pos.getFollowingMostRecentHabitEvent();
            if (mostRecentHabitEvent == null) {
                habitEventLayout.setVisibility(View.GONE);
            } else {
                noRecentHabitEvent_TextView.setVisibility(View.GONE);

                // Set habit type
                habitEventHabitTypeTextView.setText(mostRecentHabitEvent.getTitle());
                // Set the comment and date
                habitEventCommentTextView.setText(mostRecentHabitEvent.getComments());

                habitEventDateTextView.setText(simpleDateFormat.format(mostRecentHabitEvent.getStartDate()));

                // Set image
                if (mostRecentHabitEvent.getEventPhoto() != null) {
                    habitEventImgView.setImageBitmap(BitmapFactory.decodeByteArray(mostRecentHabitEvent.getEventPhoto(), 0, mostRecentHabitEvent.getEventPhoto().length));
                } else {
                    habitEventImgView.setVisibility(View.GONE);
                }
            }
        }
    }
}
