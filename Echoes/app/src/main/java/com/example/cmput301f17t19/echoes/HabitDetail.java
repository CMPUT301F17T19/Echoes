/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class HabitDetail extends AppCompatActivity {

    private Activity mActivity;

    private TextView startDate_TextView;
    private DatePickerDialog start_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        mActivity = this;

        this.setTitle("Habit Details");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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
}
