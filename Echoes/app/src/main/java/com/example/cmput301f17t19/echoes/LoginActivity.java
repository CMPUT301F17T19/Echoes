/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * User Profile, represents each user
 *
 * @author Zhaozhen(peter) Liang
 * @version 1.0
 * @since 1.0
 */



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final int REQUEST_SIGNUP = 0;


    // UI references.
    private TextView signupTextView;
    private EditText userEditText;
    private Button loginButton;


    LinearLayout myLayout;
    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //activity initialization
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        /********************************************************************************/
        //animation background
        myLayout = (LinearLayout) findViewById(R.id.myLayout);

        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);

        animationDrawable.start();

        /********************************************************************************/


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
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });


    }

    //overwrite onStart
    @Override
    protected void onStart() {
        super.onStart();
        userEditText.getText().clear();
    }



    //handling login
    private void login() {


    }



    //handling signup
    private void signUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }






    }

