/*
 * Class Name: LoginActivity
 *
 * Version: Version 1.0
 *
 * Date: November 7th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private Activity mActivity;


    // UI references.
    private TextView signupTextView;
    private EditText userEditText;
    private Button loginButton;

    private br.com.simplepass.loading_button_lib.customViews.CircularProgressButton Username_sign_in_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.dimPurple));
        }


        //activity initialization
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

       Username_sign_in_button = (CircularProgressButton) findViewById(R.id.username_sign_in_button);


        Username_sign_in_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                login();


            }
        });


        mActivity = this;

        //check and handle sign_in,sign_up stuff
        userEditText = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.username_sign_in_button);
        signupTextView = (TextView) findViewById(R.id.link_signup);

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

                final Intent main_menu_Intent = new Intent(LoginActivity.this, main_menu.class);
                main_menu_Intent.putExtra(LOGIN_USERNAME, login_UserName);

                Handler handler=  new Handler();



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Username_sign_in_button.startAnimation();

                    Runnable runnable = new Runnable()  {

                        public void run() {


                            Username_sign_in_button.stopAnimation();


                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity, Username_sign_in_button, login_UserName);

                            startActivity(main_menu_Intent, activityOptions.toBundle());

                            finish();
                        }



                };

                handler.postDelayed(runnable,3000);



                }



//                //Intent main_menu_Intent = new Intent(LoginActivity.this, main_menu.class);
//                main_menu_Intent.putExtra(LOGIN_USERNAME, login_UserName);
//
//                startActivity(main_menu_Intent);

//                finish();

            } else{

                Toast.makeText(LoginActivity.this, "Username not found!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * handling signup
     */
    private void signUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}
