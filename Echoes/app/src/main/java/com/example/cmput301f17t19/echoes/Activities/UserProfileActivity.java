/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Utils.PhotoOperator;
import com.example.cmput301f17t19.echoes.R;
import com.example.cmput301f17t19.echoes.Controllers.SelectPhotoController;
import com.example.cmput301f17t19.echoes.Controllers.TakePhotoController;
import com.example.cmput301f17t19.echoes.Models.UserProfile;

import java.util.concurrent.ExecutionException;

import static com.example.cmput301f17t19.echoes.Controllers.SelectPhotoController.loadPhoto;

/**
 * User Profile UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserProfileActivity extends AppCompatActivity {

    public static final String USERPROFILE_TAG = "USERPROFILE_TAG";

    public static final String SEARCHED_USERPROFILE_TAG = "SEARCHED_USERPROFILE_TAG";

    private String profile_username;

    private UserProfile userProfile;

    private String searched_profile_username;

    private OfflineStorageController offlineStorageController;

    private de.hdodenhof.circleimageview.CircleImageView profile_ImageButton;

    private TextView profile_username_TextView;
    private TextView profile_userComment_TextView;
    private TextView profile_userEmail_TextView;
    private TextView profile_userPhone_TextView;
    private TextView profile_userFollower_TextView;
    private TextView profile_userFollowing_TextView;

    // Send request button
    private Button request_Button;

    private Activity mActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.setTitle(R.string.userprofile_title);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mActivity = this;
        mContext = this;

        profile_ImageButton = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_photo);

        profile_username_TextView = (TextView) findViewById(R.id.profile_username);
        profile_userComment_TextView = (TextView) findViewById(R.id.profile_comment);
        profile_userEmail_TextView = (TextView) findViewById(R.id.profile_email);
        profile_userPhone_TextView = (TextView) findViewById(R.id.profile_phone_number);
        profile_userFollower_TextView = (TextView) findViewById(R.id.follower_num);
        profile_userFollowing_TextView = (TextView) findViewById(R.id.following_num);

        request_Button = (Button) findViewById(R.id.Request_Button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString(USERPROFILE_TAG) != null) {
                // Get the username passed from other activity
                profile_username = bundle.getString(USERPROFILE_TAG);

                // Get the UserProfile object with the given username
                offlineStorageController = new OfflineStorageController(this, profile_username);

                userProfile = offlineStorageController.readFromFile();

            } else if (bundle.getString(SEARCHED_USERPROFILE_TAG) != null) {
                // Get the searched profile username passed from other activity
                searched_profile_username = bundle.getString(SEARCHED_USERPROFILE_TAG);

                // Get the UserProfile object with the given username from online
                ElasticSearchController.GetUserProfileTask getUserProfileTask = new ElasticSearchController.GetUserProfileTask();
                getUserProfileTask.execute(searched_profile_username);

                try {
                    userProfile = getUserProfileTask.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

            setUI();

        }
    }

    /**
     * Set User Interface
     */
    private void setUI() {
        // If userProfile is null, getting online data failed
        if (userProfile == null) {
            // No need to show the linear layout for user profile and the request button
            ((LinearLayout) findViewById(R.id.profile_info_layout)).setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.follow_layout)).setVisibility(View.GONE);
            request_Button.setVisibility(View.GONE);

            // Show the Network error TextView
            ((TextView) findViewById(R.id.userProfile_newtorkError)).setVisibility(View.VISIBLE);

        } else {
            // Hide the Network error TextView
            ((TextView) findViewById(R.id.userProfile_newtorkError)).setVisibility(View.GONE);

            if (searched_profile_username == null) {
                // The profile of the login user
                // No need to show request button
                request_Button.setVisibility(View.INVISIBLE);

                // onClickLister for profile image button
                profile_ImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // show the dialog
                        AlertDialog profilePhoto_dialog = buildAlertDialog().create();
                        profilePhoto_dialog.show();
                    }
                });
            } else {
                // Show the request button
                // No need to show request button
                request_Button.setVisibility(View.VISIBLE);

                // Set the text of the request Button
                int followingIndicator = FollowingSharingController.checkSearchedUserStat(HabitsFollowingActivity.getLogin_userProfile(), userProfile, mContext);

                if (followingIndicator == 0) {
                    // Already followed this searched user, able to unfollow
                    request_Button.setText(R.string.followed);
                } else if (followingIndicator == 1) {
                    // Already sent following request (pending)
                    request_Button.setText(R.string.sentRequest);
                } else if (followingIndicator == 2) {
                    // Able to send follow request
                    request_Button.setText(R.string.follow);
                } else {
                    // Network error
                    request_Button.setText(R.string.network_error);
                }

                request_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int FollowingIndicator = FollowingSharingController.checkSearchedUserStat(HabitsFollowingActivity.getLogin_userProfile(), userProfile, mContext);

                        if (FollowingIndicator == 0) {
                            // Followed the searched user

                        } else if (FollowingIndicator == 1) {
                            // Pending request, do nothing

                        } else if (FollowingIndicator == 2) {
                            // Follow the search user
                            FollowingSharingController.sendFollowingRequest(HabitsFollowingActivity.getLogin_userProfile(), userProfile, mActivity);

                            // Set the button to pending
                            request_Button.setText(R.string.sentRequest);
                        } else {
                            // Network error
                            request_Button.setText(R.string.network_error);
                        }
                    }
                });
            }

            // Set User Profile Photo, Username, UserBioComment, Email, PhoneNumber, Num of Followers and Following
            if (userProfile.getProfilePicture() != null){
                profile_ImageButton.setImageBitmap(BitmapFactory.decodeByteArray(userProfile.getProfilePicture(), 0, userProfile.getProfilePicture().length));
            }

            profile_username_TextView.setText(userProfile.getUserName());
            profile_userComment_TextView.setText(userProfile.getComment());
            profile_userEmail_TextView.setText(userProfile.getEmailAddress());
            profile_userPhone_TextView.setText(userProfile.getPhoneNumber());
            profile_userFollower_TextView.setText(Integer.toString(userProfile.getFollower_list().size()));
            profile_userFollowing_TextView.setText(Integer.toString(userProfile.getFollowing().size()));
        }
    }

    /**
     * Build the AlertDialog for prompting user to select Profile Photo
     *
     * @return AlertDialog.Builder
     */
    private AlertDialog.Builder buildAlertDialog() {

        final Activity thisActivity = this;

        // Reference: https://stackoverflow.com/questions/43513919/android-alert-dialog-with-one-two-and-three-buttons
        // Open Dialog prompts user to Upload a profile photo; Take a photo; Cancel
        AlertDialog.Builder profilePhoto_dialog_builder = new AlertDialog.Builder(this);

        profilePhoto_dialog_builder.setTitle("Profile Photo");
        // Add three buttons
        profilePhoto_dialog_builder.setPositiveButton("Upload from Photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ask user's permission for reading from gallery
                SelectPhotoController.askPermission(thisActivity);

                // Create the intent of selecting photo from gallery and start this activity for result
                Intent selectPhotoIntent = SelectPhotoController.selectPhotoFromGallery();
                startActivityForResult(selectPhotoIntent, SelectPhotoController.SELECT_PHOTO_GALLERY_CODE);

            }
        });
        profilePhoto_dialog_builder.setNegativeButton("Take a Photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ask user's permission for writing to gallery
                TakePhotoController.askPermission(thisActivity);

                // Create the intent of taking a photo and start this activity for result
                Intent takePhotoIntent = TakePhotoController.takePhotoIntent();
                startActivityForResult(takePhotoIntent, TakePhotoController.TAKE_PHOTO_CODE);
            }
        });
        profilePhoto_dialog_builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Close alertDialog
                dialog.dismiss();
            }
        });

        return profilePhoto_dialog_builder;
    }

    /**
     * Set the image from upload photo or take photo
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SelectPhotoController.SELECT_PHOTO_GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = loadPhoto(this, data);

            // Resize the bitmap to user profile's size
            Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, profile_ImageButton.getWidth(), profile_ImageButton.getHeight());

            // Set the scaled profile photo to the view
            profile_ImageButton.setImageBitmap(resizeBitmap);

            // Save the uploaded profile photo to Offline Storage
            userProfile.setProfilePicture(PhotoOperator.bitmapToByteArray(resizeBitmap));
            offlineStorageController.saveInFile(userProfile);
            // online storage update
            ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
            updateUserProfileTask.execute(userProfile);

        }
        else if (requestCode == TakePhotoController.TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = TakePhotoController.loadPhoto(data);

            if (bitmap != null) {
                // Resize the bitmap to user profile's size
                Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, profile_ImageButton.getWidth(), profile_ImageButton.getHeight());

                // Set the scaled profile photo to the view
                profile_ImageButton.setImageBitmap(resizeBitmap);

                // Save the uploaded profile photo to Offline Storage
                userProfile.setProfilePicture(PhotoOperator.bitmapToByteArray(resizeBitmap));
                offlineStorageController.saveInFile(userProfile);

                // online storage update
                ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
                updateUserProfileTask.execute(userProfile);
            }
        }
    }
}
