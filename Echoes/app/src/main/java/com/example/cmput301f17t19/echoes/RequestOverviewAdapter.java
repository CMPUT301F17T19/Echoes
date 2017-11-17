/*
 * Class Name: HabitOverviewAdapter
 *
 * Version: Version 1.0
 *
 * Date: November 16th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Request Overview Recycler View Adapter
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class RequestOverviewAdapter extends RecyclerView.Adapter<RequestOverviewAdapter.RequestOverviewViewHolder>{
    public static final String SELECTED_HABIT_POSITION = "SELECTED_HABIT_POSITION";

    private Context mContext;

    /**
     * Constructor for RequestOverview Adapter
     */
    public RequestOverviewAdapter(Context context) {
        mContext = context;
    }

    /**
     * This function creates a view holder for each RequestOverview item displayed in Received Requests
     *
     * @param parent: The parent which contains the ViewHolder
     * @param viewType: The view type of this view holder
     * @return RequestOverviewAdapter.RequestOverviewViewHolder
     */
    @Override
    public RequestOverviewAdapter.RequestOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_received_requests, parent, false);
        RequestOverviewViewHolder requestOverviewViewHolder = new RequestOverviewViewHolder(view);

        return requestOverviewViewHolder;
    }

    /**
     * Bind the RequestViewHolder with the received Request object at the specific position
     *
     * @param holder: RequestOverviewViewHolder for the received Request object at the position in the List
     * @param position: The position of the received Request object in the List to be bound with the viewHolder
     */
    @Override
    public void onBindViewHolder(RequestOverviewAdapter.RequestOverviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        // Return the size of received request list of the logged-in user
        //TODO
        return MyHabitsActivity.getMyHabitList().getHabits().size();
    }

    /**
     * View Holder for each HabitEvent object displayed in HabitHistory
     */
    class RequestOverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView requestUserImage;
        private TextView requestUserName;
        private Button accept_Button;
        private Button decline_Button;

        public RequestOverviewViewHolder(View itemView) {


            super(itemView);

            requestUserImage = (ImageView) itemView.findViewById(R.id.request_userProfileImage);
            requestUserName = (TextView) itemView.findViewById(R.id.request_username);
            accept_Button = (Button) itemView.findViewById(R.id.accept_request_Button);
            decline_Button = (Button) itemView.findViewById(R.id.decline_request_Button);

            itemView.setOnClickListener(this);
        }

        /**
         * Bind the RequestViewHolder with the Request User Profile object at the specific position
         *
         * @param position: The position of the Request User Profile object in the List to be bound with the viewHolder
         */
        private void bind(int position) {
            // Get the request user profile object at the specific position in Received Requests list
            UserProfile requestUser_pos = UserMessageActivity.get
            Habit habit_pos = MyHabitsActivity.getMyHabitList().getHabits().get(position);

            // Set the comment and date
            habitTitleTextView.setText(habit_pos.getName());
            habitReasonTextView.setText(habit_pos.getReason());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            habitDateTextView.setText(simpleDateFormat.format(habit_pos.getStartDate()));

            HabitStatus habitStatus = new HabitStatus(MyHabitsActivity.getLogin_userProfile(), habit_pos);
            habitStatusTextView.setText(Float.toString(habitStatus.statisticalPlannedHabitStatus()));
        }

        /**
         * Send the intent to open the HabitDetail Activity of  the selected Habit
         *
         * @param view: View, the view of Habit clicked
         */
        @Override
        public void onClick(View view) {
            // The position of the Habit in the list that the user clicks
            int adapterPosition = getAdapterPosition();

            // The selected Habit object
            Habit selected_Habit = MyHabitsActivity.getMyHabitList().getHabits().get(adapterPosition);

            // Start Habit Detail Activity
            // Show the details of the selected Habit object in Habit Detail Screen
            Intent habitDetail_Intent = new Intent(mContext, HabitDetail.class);
            // Pass the position of the selected Habit item
            habitDetail_Intent.putExtra(SELECTED_HABIT_POSITION, adapterPosition);

            mContext.startActivity(habitDetail_Intent);

        }
    }
}
