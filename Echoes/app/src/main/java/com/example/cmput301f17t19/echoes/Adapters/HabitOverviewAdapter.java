/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Activities.CommentsActivity;
import com.example.cmput301f17t19.echoes.Activities.HabitDetailActivity;
import com.example.cmput301f17t19.echoes.Activities.LoginActivity;
import com.example.cmput301f17t19.echoes.Activities.MyHabitsActivity;
import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.UserHabitKudosComments;
import com.example.cmput301f17t19.echoes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Habit Overview Recycler View Adapter
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitOverviewAdapter extends RecyclerView.Adapter<HabitOverviewAdapter.HabitOverviewViewHolder>{
    public static final String SELECTED_HABIT_POSITION = "SELECTED_HABIT_POSITION";

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
     * @return HabitOverviewAdapter.HabitOverviewViewHolder
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
        // Return the size of HabitList of the logged-in user
        return MyHabitsActivity.getMyHabitList().getHabits().size();
    }

    /**
     * View Holder for each HabitEvent object displayed in HabitHistory
     */
    class HabitOverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView habitTitleTextView;
        private TextView habitReasonTextView;
        private TextView habitDateTextView;
        private TextView habitStatusTextView;
        private ProgressBar habitStatusProgressBar;
        private TextView habitPlanTextView;

        // For kudos and comments
        // Button for viewing habit comments
        private Button viewComments_Button;
        // View for kudos
        private ImageView kudos_ImageView;
        private TextView kudos_num_TextView;

        private UserHabitKudosComments thisUserHabitKudosComments;

        public HabitOverviewViewHolder(View itemView) {


            super(itemView);

            habitTitleTextView = (TextView) itemView.findViewById(R.id.habitOverview_title);
            habitReasonTextView = (TextView) itemView.findViewById(R.id.habitOverview_reason);
            habitDateTextView = (TextView) itemView.findViewById(R.id.habitOverview_date);
            habitStatusTextView = (TextView) itemView.findViewById(R.id.habitOverview_status);
            habitStatusProgressBar = (ProgressBar) itemView.findViewById(R.id.habit_status_progressBar);
            habitPlanTextView = (TextView) itemView.findViewById(R.id.habitPlanTextView);

            viewComments_Button = (Button) itemView.findViewById(R.id.view_Comments_Button);

            viewComments_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String habitTitle = habitTitleTextView.getText().toString();

                    if (!habitTitle.equals("")) {

                        if (thisUserHabitKudosComments == null) {
                            Toast.makeText(mContext, "You're offline. Cannot read comments and kudos from online", Toast.LENGTH_LONG).show();
                        } else {
                            // Open View Comments Activity to show all comments for this following's habit type
                            Intent commentsIntent = new Intent(mContext, CommentsActivity.class);
                            // Pass the login username
                            commentsIntent.putExtra(LoginActivity.LOGIN_USERNAME, MyHabitsActivity.getLogin_userProfile().getUserName());
                            // Pass the following username and following habit title to comments activity (elastic search id)
                            commentsIntent.putExtra(CommentsActivity.USERHABITKUDOSCOMMENTS_FollowingUsername,
                                    MyHabitsActivity.getLogin_userProfile().getUserName());
                            commentsIntent.putExtra(CommentsActivity.USERHABITKUDOSCOMMENTS_FollowingHabitTitle,
                                    habitTitle);

                            mContext.startActivity(commentsIntent);
                        }
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
                        boolean isGivenKudos = thisUserHabitKudosComments.isGivenKudos(MyHabitsActivity.getLogin_userProfile().getUserName());

                        if (isGivenKudos) {
                            // Set unliked heart icon
                            kudos_ImageView.setImageResource(R.drawable.unliked_ic);

                            // Remove the login username to the kudos list of userHabitKudosComments
                            thisUserHabitKudosComments.removeKudos(MyHabitsActivity.getLogin_userProfile().getUserName());

                            // Update online data
                            ElasticSearchController.UpdateUserHabitKudosCommentsTask updateUserHabitKudosCommentsTask = new ElasticSearchController.UpdateUserHabitKudosCommentsTask();
                            updateUserHabitKudosCommentsTask.execute(thisUserHabitKudosComments);

                            // Update kudos number
                            kudos_num_TextView.setText(Integer.toString(thisUserHabitKudosComments.getTotalKudosNum()));

                        } else {
                            // Set liked heart icon
                            kudos_ImageView.setImageResource(R.drawable.liked_ic);

                            // Add the login username to the kudos list of userHabitKudosComments
                            thisUserHabitKudosComments.addKudos(MyHabitsActivity.getLogin_userProfile().getUserName());

                            // Update online data
                            ElasticSearchController.UpdateUserHabitKudosCommentsTask updateUserHabitKudosCommentsTask = new ElasticSearchController.UpdateUserHabitKudosCommentsTask();
                            updateUserHabitKudosCommentsTask.execute(thisUserHabitKudosComments);

                            // Update kudos number
                            kudos_num_TextView.setText(Integer.toString(thisUserHabitKudosComments.getTotalKudosNum()));
                        }

                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        /**
         * Bind the HabitViewHolder with the HabitE object at the specific position
         *
         * @param position: The position of the Habit object in the List to be bound with the viewHolder
         */
        private void bind(int position) {
            // Get the habit object at the specific position in My Habits' list
            Habit habit_pos = MyHabitsActivity.getMyHabitList().getHabits().get(position);

            // Set the comment and date
            habitTitleTextView.setText(habit_pos.getName());
            habitReasonTextView.setText(habit_pos.getReason());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            habitDateTextView.setText(simpleDateFormat.format(habit_pos.getStartDate()));

            // Get the array list of string description of habit plan
            ArrayList<String> planDescription = habit_pos.getPlan().getScheduleDescription();
            String planDescription_str = "" ;

            for (String plan_str : planDescription) {
                planDescription_str += plan_str + " ";
            }

            habitPlanTextView.setText(planDescription_str);

            habitStatusTextView.setText(Float.toString(habit_pos.getProgress() * 100) + "%");

            habitStatusProgressBar.setProgress(Math.round(habit_pos.getProgress() * 100));
            habitStatusProgressBar.setMax(100);

            // Set Kudos
            thisUserHabitKudosComments = FollowingSharingController.getUserHabitKudosComments(MyHabitsActivity.getLogin_userProfile().getUserName(), habit_pos.getName());

            if (thisUserHabitKudosComments == null) {
                Toast.makeText(mContext, "You're offline.", Toast.LENGTH_LONG).show();


            } else {
                // Set kudos icon
                if (thisUserHabitKudosComments.isGivenKudos(MyHabitsActivity.getLogin_userProfile().getUserName())) {
                    kudos_ImageView.setImageResource(R.drawable.liked_ic);
                } else {
                    kudos_ImageView.setImageResource(R.drawable.unliked_ic);
                }

                // Set kudos number
                kudos_num_TextView.setText(Integer.toString(thisUserHabitKudosComments.getTotalKudosNum()));
            }
        }

        /**
         * Send the intent to open the HabitDetailActivity Activity of  the selected Habit
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
            Intent habitDetail_Intent = new Intent(mContext, HabitDetailActivity.class);
            // Pass the position of the selected Habit item
            habitDetail_Intent.putExtra(SELECTED_HABIT_POSITION, adapterPosition);

            mContext.startActivity(habitDetail_Intent);

        }
    }
}
