/*
 * Class Name: UserProfileActivity
 *
 * Version: Version 1.0
 *
 * Date: November 4th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.cmput301f17t19.echoes.SelectPhotoController.loadPhoto;

/**
 * User Profile UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class UserProfileActivity extends AppCompatActivity {

    public static final String USERPROFILE_TAG = "USERPROFILE_TAG";

    private String profile_username;

    private UserProfile userProfile;

    private OfflineStorageController offlineStorageController;

    private ImageButton profile_ImageButton;

    private TextView profile_username_TextView;
    private TextView profile_userComment_TextView;
    private TextView profile_userEmail_TextView;
    private TextView profile_userPhone_TextView;
    private TextView profile_userFollower_TextView;
    private TextView profile_userFollowing_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.setTitle(R.string.userprofile_title);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        profile_ImageButton = (ImageButton) findViewById(R.id.profile_photo);

        profile_username_TextView = (TextView) findViewById(R.id.profile_username);
        profile_userComment_TextView = (TextView) findViewById(R.id.profile_comment);
        profile_userEmail_TextView = (TextView) findViewById(R.id.profile_email);
        profile_userPhone_TextView = (TextView) findViewById(R.id.profile_phone_number);
        profile_userFollower_TextView = (TextView) findViewById(R.id.follower_num);
        profile_userFollowing_TextView = (TextView) findViewById(R.id.following_num);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Get the username passed from other activity
            profile_username = bundle.getString(USERPROFILE_TAG);

            // Get the UserProfile object with the given username
            offlineStorageController = new OfflineStorageController(getApplicationContext(), profile_username);

            userProfile = offlineStorageController.readFromFile();

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

        // onClickLister for profile image button
        profile_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show the dialog
                AlertDialog profilePhoto_dialog = buildAlertDialog().create();
                profilePhoto_dialog.show();
            }
        });
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
