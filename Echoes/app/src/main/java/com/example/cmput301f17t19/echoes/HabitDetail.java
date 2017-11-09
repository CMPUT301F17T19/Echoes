/*
* Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
*/

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HabitDetail extends AppCompatActivity {

    private Activity mActivity;
    private TextView startDate_TextView;
    private EditText Habit_name_EditText;
    private EditText Habit_reason_EditText;
    private DatePickerDialog start_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        mActivity = this;

        this.setTitle("Habit Details");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Habit_reason_EditText = (EditText) findViewById(R.id.Habit_reason_editText);
        Habit_name_EditText = (EditText) findViewById(R.id.Habit_name_editText);
        startDate_TextView = (TextView) findViewById(R.id.date_textView);

        startDate_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reference: https://www.edureka.co/blog/android-tutorials-event-listeners/
                //Setting OnDateSetListener on the DatePickerDialog
                DatePickerDialog.OnDateSetListener dateCallback = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //Displaying a Toast with the date selected
                        Toast.makeText(mActivity, "The date is : " + dayOfMonth+"/"+  ++monthOfYear +"/"+  year, Toast.LENGTH_LONG).show();

                        startDate_TextView.setText(dayOfMonth+"/"+  ++monthOfYear +"/"+  year);
                    }
                };

                start_Date = new DatePickerDialog(mActivity, dateCallback, 2017, 10, 10);
                //Showing the DatePickerDialog
                start_Date.show();
            }
        });

    }

    public void addNewHabit(View view) {
        boolean isSuccessful = true;
        String Habit_name = Habit_name_EditText.getText().toString();
        String Habit_reason  = Habit_reason_EditText.getText().toString();

        if ((Habit_name.length() == 0) || Habit_name.length() > 20){
            Habit_name_EditText.setError("Habit name should not be empty or beyond 20 words!");
            isSuccessful = false;
        }


        if (Habit_reason.length() > 30){
            Habit_reason_EditText.setError("Reason should not be empty and should be at most 30 words");
            isSuccessful = false;
        }

        if (isSuccessful){
            Intent intent = new Intent(this,MyHabitsActivity.class);
            startActivity(intent);
        }


    }

}

