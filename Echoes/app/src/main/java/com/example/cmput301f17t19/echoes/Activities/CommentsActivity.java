/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cmput301f17t19.echoes.Adapters.CommentAdapter;
import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.UserHabitKudosComments;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.example.cmput301f17t19.echoes.R;

public class CommentsActivity extends AppCompatActivity {
    public static final String USERHABITKUDOSCOMMENTS_FollowingUsername = "UHKC_FollowingUsername";
    public static final String USERHABITKUDOSCOMMENTS_FollowingHabitTitle = "UHKC_FollowingHabitTitle";

    // The user name of the login user
    private static String login_UserName;

    // The following username for UserHabitKudosComments
    private static String userHabitKudosComments_FollowingUsername;
    // The following habit title for UserHabitKudosComments
    private static String userHabitKudosComments_FollowingHabitTitle;

    // The UserHabitKudosComments for this search id
    private static UserHabitKudosComments mUserHabitKudosComments;

    private RecyclerView comments_RecyclerView;
    private static CommentAdapter commentAdapter;

    private TextView loginUsername_TextView;
    private EditText loginUserComment_EditText;


    private static UserProfile login_UserProfile;

    private de.hdodenhof.circleimageview.CircleImageView profile_ImageButton;

    //private Button sendComment_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.redPink));
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        this.setTitle(R.string.comments);

        Intent intent = getIntent();
        if (intent.getStringExtra(LoginActivity.LOGIN_USERNAME) != null) {
            login_UserName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);
        }

        if (intent.getStringExtra(USERHABITKUDOSCOMMENTS_FollowingUsername) != null &&
                intent.getStringExtra(USERHABITKUDOSCOMMENTS_FollowingHabitTitle) != null) {
            userHabitKudosComments_FollowingUsername = intent.getStringExtra(USERHABITKUDOSCOMMENTS_FollowingUsername);
            userHabitKudosComments_FollowingHabitTitle = intent.getStringExtra(USERHABITKUDOSCOMMENTS_FollowingHabitTitle);
        }

        if (userHabitKudosComments_FollowingUsername != null && userHabitKudosComments_FollowingHabitTitle != null) {
            mUserHabitKudosComments = FollowingSharingController.getUserHabitKudosComments(userHabitKudosComments_FollowingUsername, userHabitKudosComments_FollowingHabitTitle);
        }

        // Set up recycler view
        comments_RecyclerView = (RecyclerView) findViewById(R.id.comments_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        comments_RecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(comments_RecyclerView.getContext(),
                layoutManager.getOrientation());
        comments_RecyclerView.addItemDecoration(mDividerItemDecoration);

        comments_RecyclerView.setHasFixedSize(true);

        loginUsername_TextView = (TextView) findViewById(R.id.sendingComment_Username);
        loginUsername_TextView.setText(login_UserName);







        /*

        setLogin_UserProfile(login_UserName);

        profile_ImageButton = findViewById(R.id.profile_photo_3);

        // Set User Profile Photo, Username, UserBioComment, Email, PhoneNumber, Num of Followers and Following
        if (login_UserProfile.getProfilePicture() != null){
            profile_ImageButton.setImageBitmap(BitmapFactory.decodeByteArray(login_UserProfile.getProfilePicture(), 0, login_UserProfile.getProfilePicture().length));
        }

        */





        loginUserComment_EditText = (EditText) findViewById(R.id.sendingComment_Content);

        //sendComment_Button = (Button) findViewById(R.id.sendingComment_Button);

        /*
        sendComment_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send the comment if edit text not empty
                String comment = loginUserComment_EditText.getText().toString().trim();

                if (!comment.equals("")) {
                    // Add the login username and the comment to mUserHabitKudosComments's comment_username list and comment_content list
                    if (mUserHabitKudosComments != null) {
                        mUserHabitKudosComments.addUsernameComments(login_UserName);
                        mUserHabitKudosComments.addCommentContent(comment);

                        // Update the list
                        commentAdapter.notifyDataSetChanged();

                        // Update online data
                        ElasticSearchController.UpdateUserHabitKudosCommentsTask updateUserHabitKudosCommentsTask = new ElasticSearchController.UpdateUserHabitKudosCommentsTask();
                        updateUserHabitKudosCommentsTask.execute(mUserHabitKudosComments);

                        // Clear edit text
                        loginUserComment_EditText.setText("");

                    } else {
                        Log.d("Test", "Send Comment Error");
                    }
                }
            }
        });
        */

        loginUserComment_EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {


                    // Send the comment if edit text not empty
                    String comment = loginUserComment_EditText.getText().toString().trim();

                    if (!comment.equals("")) {
                        // Add the login username and the comment to mUserHabitKudosComments's comment_username list and comment_content list
                        if (mUserHabitKudosComments != null) {
                            mUserHabitKudosComments.addUsernameComments(login_UserName);
                            mUserHabitKudosComments.addCommentContent(comment);

                            // Update the list
                            commentAdapter.notifyDataSetChanged();

                            // Update online data
                            ElasticSearchController.UpdateUserHabitKudosCommentsTask updateUserHabitKudosCommentsTask = new ElasticSearchController.UpdateUserHabitKudosCommentsTask();
                            updateUserHabitKudosCommentsTask.execute(mUserHabitKudosComments);

                            // Clear edit text
                            loginUserComment_EditText.setText("");

                        } else {
                            Log.d("Test", "Send Comment Error");
                        }
                    }


                    handled = true;
                }
                return handled;
            }
        });





    }

    protected void onStart() {
        super.onStart();

        hideKeyboard(CommentsActivity.this);

        if (mUserHabitKudosComments != null) {
            commentAdapter = new CommentAdapter();
            comments_RecyclerView.setAdapter(commentAdapter);

            ((TextView) findViewById(R.id.comments_networkError)).setVisibility(View.GONE);
        } else {
            ((LinearLayout) findViewById(R.id.comments_Layout)).setVisibility(View.GONE);
        }
    }

    /**
     * Get the UserHabitKudosComments
     */
    public static UserHabitKudosComments getmUserHabitKudosComments() {
        return mUserHabitKudosComments;
    }





    private void setLogin_UserProfile(String login_Username) {

        OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_Username);

        // Get the Offline Storage file of this user
        login_UserProfile = offlineStorageController.readFromFile();

        // Sync the offline file with online data storage
        ElasticSearchController.syncOnlineWithOffline(login_UserProfile);
    }



    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
