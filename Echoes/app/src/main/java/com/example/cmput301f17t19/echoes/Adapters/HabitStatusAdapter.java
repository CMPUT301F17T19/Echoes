/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Activities.CommentsActivity;
import com.example.cmput301f17t19.echoes.Activities.HabitsFollowingActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Models.FollowingHabitsStatus;
import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.Models.UserHabitKudosComments;
import com.example.cmput301f17t19.echoes.R;

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
    private String loginUserName;

    /**
     * Constructor for HabitStatus Adapter
     */
    public HabitStatusAdapter(Context context, String loginUsername) {
        mContext = context;
        loginUserName = loginUsername;
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

        private TextView mon;
        private TextView tue;
        private TextView wed;
        private TextView thurs;
        private TextView fri;
        private TextView sat;
        private TextView sun;




        private TextView habitEventHabitTypeTextView;
        private ImageView habitEventImgView;
        private TextView habitEventCommentTextView;
        private TextView habitEventDateTextView;

        private RelativeLayout habitEventLayout;
        private TextView noRecentHabitEvent_TextView;

        // Button for viewing habit comments
        private TextView viewComments_Button;
        // View for kudos
        private ImageView kudos_ImageView;
        private TextView kudos_num_TextView;

        private UserHabitKudosComments thisUserHabitKudosComments;


        public HabitStatusViewHolder(View itemView) {


            super(itemView);

            userProfileImage = (ImageView) itemView.findViewById(R.id.user_profile_img);
            userName = (TextView) itemView.findViewById(R.id.user_name);

            habitTitleTextView = (TextView) itemView.findViewById(R.id.habitOverview_title);
            habitReasonTextView = (TextView) itemView.findViewById(R.id.habitOverview_reason);
            habitDateTextView = (TextView) itemView.findViewById(R.id.habitOverview_date);
            habitStatusTextView = (TextView) itemView.findViewById(R.id.habitOverview_status);
            habitStatusProgressBar = (ProgressBar) itemView.findViewById(R.id.habit_status_progressBar);
            //habitPlanTextView = (TextView) itemView.findViewById(R.id.habitPlanTextView);

            mon = (TextView)itemView.findViewById(R.id.monday);
            tue = (TextView)itemView.findViewById(R.id.tuesday);
            wed = (TextView)itemView.findViewById(R.id.wednesday);
            thurs = (TextView)itemView.findViewById(R.id.thursday);
            fri = (TextView)itemView.findViewById(R.id.friday);
            sat = (TextView)itemView.findViewById(R.id.saturday);
            sun = (TextView)itemView.findViewById(R.id.sunday);


            habitEventHabitTypeTextView = (TextView) itemView.findViewById(R.id.habitevent_type_textView);
            habitEventImgView = (ImageView) itemView.findViewById(R.id.habitevent_photo);
            habitEventCommentTextView = (TextView) itemView.findViewById(R.id.habitevent_comment);
            habitEventDateTextView = (TextView) itemView.findViewById(R.id.habitevent_date);

            habitEventLayout = (RelativeLayout) itemView.findViewById(R.id.followingHabitMostRecentEvent_layout);
            noRecentHabitEvent_TextView = (TextView) itemView.findViewById(R.id.no_mostRecentEvent);

            viewComments_Button = (TextView) itemView.findViewById(R.id.view_Comments_Button);

            viewComments_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (thisUserHabitKudosComments == null) {
                        Toast.makeText(mContext, "You're offline.", Toast.LENGTH_LONG).show();
                    } else {
                        // Open View Comments Activity to show all comments for this following's habit type
                        Intent commentsIntent = new Intent(mContext, CommentsActivity.class);
                        // Pass the login username
                        commentsIntent.putExtra(LoginActivity.LOGIN_USERNAME, loginUserName);
                        // Pass the following username and following habit title to comments activity (elastic search id)
                        commentsIntent.putExtra(CommentsActivity.USERHABITKUDOSCOMMENTS_FollowingUsername,
                                thisUserHabitKudosComments.getFollowingUsername());
                        commentsIntent.putExtra(CommentsActivity.USERHABITKUDOSCOMMENTS_FollowingHabitTitle,
                                thisUserHabitKudosComments.getFollowingHabitTitle());

                        mContext.startActivity(commentsIntent);
                    }
                }
            });

            kudos_ImageView = (ImageView) itemView.findViewById(R.id.kudos_imageView);
            kudos_num_TextView = (TextView) itemView.findViewById(R.id.kudos_num);

            kudos_ImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (thisUserHabitKudosComments == null) {
                        Toast.makeText(mContext, "You're offline.", Toast.LENGTH_LONG).show();
                    } else {
                        // Check if the login username in the kudos list of userHabitKudosComments
                        boolean isGivenKudos = thisUserHabitKudosComments.isGivenKudos(loginUserName);

                        if (isGivenKudos) {
                            // Set unliked heart icon
                            kudos_ImageView.setImageResource(R.drawable.unliked_ic);

                            // Remove the login username to the kudos list of userHabitKudosComments
                            thisUserHabitKudosComments.removeKudos(loginUserName);

                            // Update online data
                            ElasticSearchController.UpdateUserHabitKudosCommentsTask updateUserHabitKudosCommentsTask = new ElasticSearchController.UpdateUserHabitKudosCommentsTask();
                            updateUserHabitKudosCommentsTask.execute(thisUserHabitKudosComments);

                            // Update kudos number
                            kudos_num_TextView.setText(Integer.toString(thisUserHabitKudosComments.getTotalKudosNum()));

                        } else {
                            // Set liked heart icon
                            kudos_ImageView.setImageResource(R.drawable.liked_ic);

                            // Add the login username to the kudos list of userHabitKudosComments
                            thisUserHabitKudosComments.addKudos(loginUserName);

                            // Update online data
                            ElasticSearchController.UpdateUserHabitKudosCommentsTask updateUserHabitKudosCommentsTask = new ElasticSearchController.UpdateUserHabitKudosCommentsTask();
                            updateUserHabitKudosCommentsTask.execute(thisUserHabitKudosComments);

                            // Update kudos number
                            kudos_num_TextView.setText(Integer.toString(thisUserHabitKudosComments.getTotalKudosNum()));
                        }

                    }
                }
            });
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

            boolean mo=false;
            boolean tu=false;
            boolean we=false;
            boolean th=false;
            boolean fr=false;
            boolean sa=false;
            boolean su=false;

            for (String plan_str : planDescription) {

                if (plan_str.equals("Monday")){
                    mo = true;
                }


                if (plan_str.equals("Tuesday") ){
                    tu = true;
                }


                if (plan_str.equals("Wednesday")){
                    we = true;
                }


                if (plan_str.equals("Thursday")){
                    th = true;
                }


                if (plan_str.equals("Friday")){
                    fr = true;
                }


                if (plan_str.equals("Saturday")){
                    sa = true;
                }


                if (plan_str.equals("Sunday")){
                    su = true;
                }


            }


            if (mo){
                mon.setBackground(mContext.getDrawable(R.drawable.redpink_circle_button));
                mon.setTextColor(mContext.getColor(R.color.white));
            }
            else{

                mon.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
                mon.setTextColor(mContext.getColor(R.color.black));
            }


            if (tu){
                tue.setBackground(mContext.getDrawable(R.drawable.redpink_circle_button));
                tue.setTextColor(mContext.getColor(R.color.white));
            }
            else{

                tue.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
                tue.setTextColor(mContext.getColor(R.color.black));
            }



            if (we){
                wed.setBackground(mContext.getDrawable(R.drawable.redpink_circle_button));
                wed.setTextColor(mContext.getColor(R.color.white));
            }
            else{

                wed.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
                wed.setTextColor(mContext.getColor(R.color.black));
            }



            if (th){
                thurs.setBackground(mContext.getDrawable(R.drawable.redpink_circle_button));
                thurs.setTextColor(mContext.getColor(R.color.white));
            }
            else{

                thurs.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
                thurs.setTextColor(mContext.getColor(R.color.black));
            }



            if (fr){
                fri.setBackground(mContext.getDrawable(R.drawable.redpink_circle_button));
                fri.setTextColor(mContext.getColor(R.color.white));
            }
            else{

                fri.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
                fri.setTextColor(mContext.getColor(R.color.black));
            }



            if (sa){
                sat.setBackground(mContext.getDrawable(R.drawable.redpink_circle_button));
                sat.setTextColor(mContext.getColor(R.color.white));
            }
            else{

                sat.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
                sat.setTextColor(mContext.getColor(R.color.black));
            }



            if (su){
                sun.setBackground(mContext.getDrawable(R.drawable.redpink_circle_button));
                sun.setTextColor(mContext.getColor(R.color.white));
            }
            else{

                sun.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
                sun.setTextColor(mContext.getColor(R.color.black));
            }

            //habitPlanTextView.setText(planDescription_str);

            habitStatusTextView.setText(Float.toString(thisHabit.getProgress() * 100) + "%");

            habitStatusProgressBar.setProgress(Math.round(thisHabit.getProgress() * 100));
            habitStatusProgressBar.setMax(100);

            // Set kudos
            String followingUsername = followingHabitsStatus_pos.getFollowingUsername();
            String followingHabitTitle = followingHabitsStatus_pos.getFollowingHabit().getName();

            UserHabitKudosComments userHabitKudosComments = FollowingSharingController.getUserHabitKudosComments(followingUsername, followingHabitTitle);

            thisUserHabitKudosComments = userHabitKudosComments;

            // Set Kudos UI
            if (userHabitKudosComments == null) {
                Toast.makeText(mContext, "You're offline.", Toast.LENGTH_LONG).show();
            } else {
                // Set kudos icon
                if (thisUserHabitKudosComments.isGivenKudos(loginUserName)) {
                    kudos_ImageView.setImageResource(R.drawable.liked_ic);
                } else {
                    kudos_ImageView.setImageResource(R.drawable.unliked_ic);
                }

                // Set kudos number
                kudos_num_TextView.setText(Integer.toString(thisUserHabitKudosComments.getTotalKudosNum()));
            }


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
