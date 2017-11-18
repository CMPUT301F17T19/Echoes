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
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Request Overview Recycler View Adapter
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class RequestOverviewAdapter extends RecyclerView.Adapter<RequestOverviewAdapter.RequestOverviewViewHolder>{

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
        return UserMessageActivity.getReceivedRequests().size();
    }

    /**
     * View Holder for each Request object displayed in Received Requests
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
            ReceivedRequest receivedRequest_pos = UserMessageActivity.getReceivedRequests().get(position);

            ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
            getUserProfileTask.execute(receivedRequest_pos.getUsername());

            try {
                UserProfile userProfile = getUserProfileTask.get();

                if (userProfile != null) {
                    if (userProfile.getProfilePicture() != null) {
                        // Set profile photo
                        requestUserImage.setImageBitmap(BitmapFactory.decodeByteArray(userProfile.getProfilePicture(),0,userProfile.getProfilePicture().length));
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            // Set the username
            requestUserName.setText(receivedRequest_pos.getUsername());

            // Accept button
            accept_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Accept the following request from the selected user
                    FollowingSharingController.acceptFollowingRequest(UserMessageActivity.getLogin_userProfile(), UserMessageActivity.getReceivedRequests().get(getAdapterPosition()).getUsername(), mContext);

                    // Remove the selected request from the list
                    UserMessageActivity.getReceivedRequests().remove(getAdapterPosition());
                    UserMessageActivity.getRequestOverviewAdapter().notifyDataSetChanged();
                }
            });

            // Decline button
            decline_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Decline the following request from the selected user
                    FollowingSharingController.declineFollowingRequest(UserMessageActivity.getLogin_userProfile(), UserMessageActivity.getReceivedRequests().get(getAdapterPosition()).getUsername(), mContext);
                    // Remove the selected request from the list
                    UserMessageActivity.getReceivedRequests().remove(getAdapterPosition());
                    UserMessageActivity.getRequestOverviewAdapter().notifyDataSetChanged();
                }
            });
        }

        /**
         * Send the intent to open the UserProfile Activity of  the requested user
         *
         * @param view: View, the view of Habit clicked
         */
        @Override
        public void onClick(View view) {
            // The position of the Habit in the list that the user clicks
            int adapterPosition = getAdapterPosition();

            // The selected Habit object
            ReceivedRequest selected_ReceivedRequest = UserMessageActivity.getReceivedRequests().get(adapterPosition);

            // Start UserProfile Activity
            Intent userProfile_Intent = new Intent(mContext, UserProfileActivity.class);
            // Pass the username of the selected received request
            userProfile_Intent.putExtra(UserProfileActivity.SEARCHED_USERPROFILE_TAG, selected_ReceivedRequest.getUsername());

            mContext.startActivity(userProfile_Intent);

        }
    }
}
