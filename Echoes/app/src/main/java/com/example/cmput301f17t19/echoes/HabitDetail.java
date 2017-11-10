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
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class HabitDetail extends AppCompatActivity {

    private Activity mActivity;
    private TextView startDate_TextView;
    private EditText Habit_name_EditText;
    private EditText Habit_reason_EditText;
    private DatePickerDialog start_Date;

    // Checkboxes
    private CheckBox sunday_CheckBox;
    private CheckBox monday_CheckBox;
    private CheckBox tuesday_CheckBox;
    private CheckBox wednesday_CheckBox;
    private CheckBox thursday_CheckBox;
    private CheckBox friday_CheckBox;
    private CheckBox saturday_CheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        mActivity = this;

        this.setTitle("Habit Details");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        sunday_CheckBox = (CheckBox) findViewById(R.id.sunday_checkbox);
        monday_CheckBox = (CheckBox) findViewById(R.id.monday_checkBox);
        tuesday_CheckBox = (CheckBox) findViewById(R.id.tuesday_checkBox);
        wednesday_CheckBox = (CheckBox) findViewById(R.id.wednesday_checkBox);
        thursday_CheckBox = (CheckBox) findViewById(R.id.thursday_checkBox);
        friday_CheckBox = (CheckBox) findViewById(R.id.friday_checkBox);
        saturday_CheckBox = (CheckBox) findViewById(R.id.saturday_checkBox);


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
                start_Date.getDatePicker().setMinDate(System.currentTimeMillis());
                //Showing the DatePickerDialog
                start_Date.show();
            }
        });

    }

    private ArrayList<Integer> checkedCheckBox(){
        ArrayList<Integer> checkedCheckBox = new ArrayList<Integer>();

        if (sunday_CheckBox.isChecked()) {
            checkedCheckBox.add(0);
        }

        if (monday_CheckBox.isChecked()) {
            checkedCheckBox.add(1);
        }

        if (tuesday_CheckBox.isChecked()) {
            checkedCheckBox.add(2);
        }

        if (wednesday_CheckBox.isChecked()) {
            checkedCheckBox.add(3);
        }

        if (thursday_CheckBox.isChecked()) {
            checkedCheckBox.add(4);
        }

        if (friday_CheckBox.isChecked()) {
            checkedCheckBox.add(5);
        }

        if (saturday_CheckBox.isChecked()) {
            checkedCheckBox.add(6);
        }

        return checkedCheckBox;
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

        if (startDate_TextView.getText().toString().equals("Click to select start date")){
            Toast.makeText(this, "Please select start date", Toast.LENGTH_LONG).show();
            isSuccessful = false;
        }

        ArrayList<Integer> checkedCheckbox = checkedCheckBox();
        Log.d("check", checkedCheckbox.toString());

        if (checkedCheckbox.size() == 0){
            Toast.makeText(this, "Please select at least one plan day", Toast.LENGTH_LONG).show();
            isSuccessful = false;
        }
        Log.d("Date", startDate_TextView.getText().toString());
        if (isSuccessful){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
            try {
                Date date = simpleDateFormat.parse(startDate_TextView.getText().toString());

               // Habit new_habit = new Habit(Habit_name, Habit_reason, date, );

                Intent intent = new Intent(this,MyHabitsActivity.class);
                startActivity(intent);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Return an int array containing the number of checked checkbox
     * Sunday 0, Monday 1 ...
     * @return int array
     */


}



