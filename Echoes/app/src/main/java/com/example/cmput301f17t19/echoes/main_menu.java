/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_menu extends AppCompatActivity {

    private Button myHabitsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        myHabitsButton = (Button) findViewById(R.id.View_My_Habits);
    }


    public void viewMyHabits(View view) {
        Intent intent = new Intent(this, MyHabitsActivity.class);
        startActivity(intent);
    }

    public void Following(View view) {
        Intent intent = new Intent(this, Following.class);
        startActivity(intent);
    }

//    public void Habits_todo_today (View view) {
//        Intent intent = new Intent(this, AddNewActivity.class);
//        startActivity(intent);
//    }

//    public void  (View view) {
//        Intent intent = new Intent(this, AddNewActivity.class);
//        startActivity(intent);
//    }
//
//    public void (View view) {
//        Intent intent = new Intent(this, AddNewActivity.class);
//        startActivity(intent);
//    }
//
//    public void Logout(View view) {
//        Intent intent = new Intent(this, AddNewActivity.class);
//        startActivity(intent);
//    }


}
