/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.cmput301f17t19.echoes.SelectPhotoController.loadPhoto;


/**
 * Created by Peter Liang on 2017-10-23.
 */

public class SignUpActivity extends AppCompatActivity {


    LinearLayout myLayout;
    AnimationDrawable animationDrawable;

    private ImageButton profile_ImageButton;

    //UI references
    private EditText UserName;
    private EditText UserEmail;
    private EditText UserPhone;
    private EditText UserComment;
    private byte[]   UserProfile_Picture;

    private Button UserSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //animation background
        myLayout = (LinearLayout) findViewById(R.id.myLayout2);

        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);

        animationDrawable.start();



        //reference link
        profile_ImageButton = (ImageButton) findViewById(R.id.profile_photo);
        UserName = (EditText) findViewById(R.id.username);
        UserEmail = (EditText) findViewById(R.id.user_email);
        UserComment = (EditText) findViewById(R.id.user_comment);

        UserSignUp = (Button) findViewById(R.id.signup);
        profile_ImageButton = (ImageButton) findViewById(R.id.profile_photo);





        UserSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //handle signup check
                SignUp();

            }

        });




        profile_ImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //handle upload image
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
            //userProfile.setProfilePicture(PhotoOperator.bitmapToByteArray(resizeBitmap));
            //offlineStorageController.saveInFile(userProfile);
            // online storage update
            ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
            //updateUserProfileTask.execute(userProfile);

        }
        else if (requestCode == TakePhotoController.TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = TakePhotoController.loadPhoto(data);

            if (bitmap != null) {
                // Resize the bitmap to user profile's size
                Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, profile_ImageButton.getWidth(), profile_ImageButton.getHeight());

                // Set the scaled profile photo to the view
                profile_ImageButton.setImageBitmap(resizeBitmap);

                // Save the uploaded profile photo to Offline Storage
                //userProfile.setProfilePicture(PhotoOperator.bitmapToByteArray(resizeBitmap));
                //offlineStorageController.saveInFile(userProfile);

                // online storage update
                ElasticSearchController.UpdateUserProfileTask updateUserProfileTask = new ElasticSearchController.UpdateUserProfileTask();
                //updateUserProfileTask.execute(userProfile);
            }
        }
    }










    //overwrite onStart
    @Override
    protected void onStart() {
        super.onStart();
        UserName.getText().clear();
        UserEmail.getText().clear();
        UserComment.getText().clear();
        UserPhone.getText().clear();

    }



    private void SignUp(){

        //check if empty username
        int text_len = UserName.getText().length();

        if (text_len == 0){

            Toast.makeText(SignUpActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
            return;

        }else{




        }



    }




}

