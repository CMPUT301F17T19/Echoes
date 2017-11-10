/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;


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

        UserSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                


            }
        });





    }




}
