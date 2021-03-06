/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Utils.PhotoOperator;
import com.example.cmput301f17t19.echoes.R;
import com.example.cmput301f17t19.echoes.Controllers.SelectPhotoController;
import com.example.cmput301f17t19.echoes.Controllers.TakePhotoController;
import com.example.cmput301f17t19.echoes.Models.UserFollowingList;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.example.cmput301f17t19.echoes.Models.UserReceivedRequestsList;

import java.util.concurrent.ExecutionException;

/**
 * Sign Up Activity
 *
 * @author Peter Liang
 * @version 1.0
 * @since 1.0
 */
public class SignUpActivity extends AppCompatActivity {


    private View animateView;


    private OfflineStorageController offlineStorageController;



    //UI references
    private EditText UserName;
    private EditText UserEmail;
    private EditText UserPhone;
    private EditText UserComment;
    //private ImageButton profile_ImageButton;

    private de.hdodenhof.circleimageview.CircleImageView profile_ImageButton;

    private byte[]   UserProfile_Picture = null;


    private br.com.simplepass.loading_button_lib.customViews.CircularProgressButton UserSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        /*
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.dimPurple));
        }

        */

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);






        //reference link

        animateView = findViewById(R.id.animate_view2);

        UserName = (EditText) findViewById(R.id.user_name);
        UserEmail = (EditText) findViewById(R.id.user_email);
        UserComment = (EditText) findViewById(R.id.user_comment);

        UserPhone = (EditText) findViewById(R.id.phone_number);


        UserSignUp = (br.com.simplepass.loading_button_lib.customViews.CircularProgressButton) findViewById(R.id.signup);

        profile_ImageButton = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);


        UserName.getText().clear();
        UserEmail.getText().clear();
        UserComment.getText().clear();
        UserPhone.getText().clear();



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
            Bitmap bitmap = SelectPhotoController.loadPhoto(this, data);

            // Resize the bitmap to user profile's size
            Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, profile_ImageButton.getWidth(), profile_ImageButton.getHeight());
            


            // Set the scaled profile photo to the view
            profile_ImageButton.setImageBitmap(resizeBitmap);

            //store the photo to the user profile pic tmp variable
            UserProfile_Picture = PhotoOperator.bitmapToByteArray(resizeBitmap);

        }
        else if (requestCode == TakePhotoController.TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = TakePhotoController.loadPhoto(data);

            if (bitmap != null) {
                // Resize the bitmap to user profile's size
                Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, profile_ImageButton.getWidth(), profile_ImageButton.getHeight());

                // Set the scaled profile photo to the view
                profile_ImageButton.setImageBitmap(resizeBitmap);


                //store the photo to the user profile pic tmp variable
                UserProfile_Picture = PhotoOperator.bitmapToByteArray(resizeBitmap);
            }
        }
    }

    /**
     * handle sign up
     */
    private void SignUp(){

        //check if empty username
        int text_len = UserName.getText().toString().trim().length();

        if (text_len == 0){

            Toast.makeText(SignUpActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
            return;

        }else{

            //first check if the username already exited
            //check online server database

            ElasticSearchController.CheckUserProfileExistTask checkUserProfileExistTask = new ElasticSearchController.CheckUserProfileExistTask();

            checkUserProfileExistTask.execute(UserName.getText().toString().trim());

            try {

                Boolean check = checkUserProfileExistTask.get();

                if (check == null) {
                    // Offline, not allowed to sign up
                    Toast.makeText(SignUpActivity.this, "Sorry. You cannot SignUp while offline.", Toast.LENGTH_SHORT).show();

                } else if (!check){
                      //valid username

                      //create new user profile for this user
                      UserProfile userProfile = new UserProfile(UserName.getText().toString().trim());

                      //set up user profile detail accordingly

                      if ( ! UserEmail.getText().toString().isEmpty()) {

                          userProfile.setEmailAddress(UserEmail.getText().toString());

                      }

                      if ( ! UserPhone.getText().toString().isEmpty()) {

                          userProfile.setPhoneNumber(UserPhone.getText().toString());

                      }

                      if ( ! UserComment.getText().toString().isEmpty()) {

                          userProfile.setComment(UserComment.getText().toString());

                      }

                      if (UserProfile_Picture == null) {
                          // Use the default profile photo
                          Bitmap default_profile_img = BitmapFactory.decodeResource(getResources(), R.drawable.user_profile_icon);
                          default_profile_img = PhotoOperator.compressImage(default_profile_img);

                          // Resize the bitmap to user profile's size
                          Bitmap resizeBitmap = PhotoOperator.resizeImage(default_profile_img, profile_ImageButton.getWidth(), profile_ImageButton.getHeight());

                          //store the photo to the user profile pic tmp variable
                          UserProfile_Picture = PhotoOperator.bitmapToByteArray(resizeBitmap);
                      }

                      // Set user profile img
                      userProfile.setProfilePicture(UserProfile_Picture);

                    //create new storage for new user in server
                    ElasticSearchController.AddNewUserProfileTask addNewUserProfileTask = new ElasticSearchController.AddNewUserProfileTask();
                    addNewUserProfileTask.execute(userProfile);

                    // Create new userReceivedRequestList for this user
                    UserReceivedRequestsList userReceivedRequestsList = new UserReceivedRequestsList(UserName.getText().toString().trim());
                    // Add to online data storage
                    ElasticSearchController.AddNewUserReceivedRequestsTask addNewUserReceivedRequestsTask = new ElasticSearchController.AddNewUserReceivedRequestsTask();
                    addNewUserReceivedRequestsTask.execute(userReceivedRequestsList);

                    // Create new userFollowingList for this user
                    UserFollowingList userFollowingList = new UserFollowingList(UserName.getText().toString().trim());
                    // Add to online data storage
                    ElasticSearchController.AddNewUserFollowingsTask addNewUserFollowingsTask = new ElasticSearchController.AddNewUserFollowingsTask();
                    addNewUserFollowingsTask.execute(userFollowingList);

                    //create a new file to store the new user profile to the local/offline storage
                    offlineStorageController = new OfflineStorageController(this, UserName.getText().toString().trim());
                    //save that user profile into its file
                    offlineStorageController.saveInFile(userProfile);

                    Handler handler=  new Handler();



                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        UserSignUp.startAnimation();

                        Runnable runnable = new Runnable()  {

                            public void run() {

                                UserSignUp.stopAnimation();

                                toNextPage();


                            }


                        };

                        handler.postDelayed(runnable,1000);

                    }

                  }else{


                      Toast.makeText(SignUpActivity.this, "Sorry. The username you entered is already existed. Please re-enter another username.", Toast.LENGTH_SHORT).show();
                      UserName.setError("The username you entered is already existed.");

                  }



            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(SignUpActivity.this, "Sorry. You cannot SignUp while offline.", Toast.LENGTH_SHORT).show();

            } catch (ExecutionException e) {
                e.printStackTrace();
                Toast.makeText(SignUpActivity.this, "Sorry. You cannot SignUp while offline.", Toast.LENGTH_SHORT).show();

            }

        }



    }





    private void toNextPage(){


        final String login_UserName = UserName.getText().toString().trim();
        final Intent main_menu_Intent = new Intent(SignUpActivity.this, ToDoActivity.class);
        main_menu_Intent.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);

        //int cx = 380;
        //int cy = 1010;

        int cx = (UserName.getLeft() + UserName.getRight()+30) / 2;
        int cy = (UserName.getTop() + UserName.getBottom()) / 2*3;

        Animator animator = ViewAnimationUtils.createCircularReveal(animateView,cx,cy,0,getResources().getDisplayMetrics().heightPixels * 1.2f);
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animateView.setVisibility(View.VISIBLE);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                animateView.setVisibility(View.VISIBLE);
                //ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity, Username_sign_in_button, "transition");
                startActivity(main_menu_Intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }




}
