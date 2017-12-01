/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


/**
 * A login screen that offers login via email/password.
 *
 * @author Zhaozhen(peter) Liang
 * @version 1.0
 * @since 1.0
 */
public class LoginActivity extends AppCompatActivity  {

    public static final String LOGIN_USERNAME = "LOGIN_USERNAME";

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final int REQUEST_SIGNUP = 0;


    private View animateView;

    // UI references.
    private TextView signupTextView;
    private EditText userEditText;


    private br.com.simplepass.loading_button_lib.customViews.CircularProgressButton Username_sign_in_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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


        //activity initialization
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


       Username_sign_in_button = (CircularProgressButton) findViewById(R.id.username_sign_in_button);


        Username_sign_in_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard(LoginActivity.this);
                login();


            }
        });





        //check and handle sign_in,sign_up stuff
        userEditText = (EditText) findViewById(R.id.username);

        userEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {



                    login();


                    handled = true;
                }
                return handled;
            }
        });

        userEditText.requestFocus();

        signupTextView = (TextView) findViewById(R.id.link_signup);



        animateView = findViewById(R.id.animate_view);



        //handle signup when signup being clicked
        signupTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signUp();
            }
        });

        //call signin handler when signin being clicked
        /*
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });

        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        animateView.setVisibility(View.INVISIBLE);
        userEditText.getText().clear();
    }


    /**
     * handling login
     */
    private void login() {

        // check if username/password are empty, discard empty space
        final String login_UserName = userEditText.getText().toString().trim();

        if (login_UserName.length() == 0) {
            Toast.makeText(LoginActivity.this, "Please provide a Username!", Toast.LENGTH_SHORT).show();
            return;
        } else{

            //check if username exists in the offline file
            OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_UserName);


            if (offlineStorageController.isFileExist()){
                // User File exist, this user has logged in before
                // Pass the login User to the main menu


                hideKeyboard(LoginActivity.this);


                Handler handler=  new Handler();



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Username_sign_in_button.startAnimation();

                    Runnable runnable = new Runnable()  {

                        public void run() {

                            Username_sign_in_button.stopAnimation();
                            toNextPage();


                        }


                };

                handler.postDelayed(runnable,3000);

                }



            } else{

                Toast.makeText(LoginActivity.this, "Username not found!", Toast.LENGTH_SHORT).show();

            }
        }
    }






    private void toNextPage(){


        final String login_UserName = userEditText.getText().toString().trim();
        final Intent main_menu_Intent = new Intent(LoginActivity.this, ToDoActivity.class);
        main_menu_Intent.putExtra(LOGIN_USERNAME, login_UserName);

        //int cx = 380;
        //int cy = 830;

        int cx = (Username_sign_in_button.getLeft() + Username_sign_in_button.getRight()+30) / 2;
        int cy = (Username_sign_in_button.getTop() + Username_sign_in_button.getBottom()) / 2*3;

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





    /**
     * handling signup
     */
    private void signUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
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
