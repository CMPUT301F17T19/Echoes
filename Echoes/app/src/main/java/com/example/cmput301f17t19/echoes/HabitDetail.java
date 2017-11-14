/*
 * Class Name: HabitDetail
 *
 * Version: Version 1.0
 *
 * Date: November 8th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * HabitDetail Activity
 *
 * @author Taijie Yang, Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitDetail extends AppCompatActivity {

    // Check if the user wants to create a new Habit or select a existed Habit
    private boolean isNewHabit;

    // The position of the selected Habit
    private int selected_pos;
    // The selected Habit object
    private Habit selected_Habit;

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

    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.primary_dark));
        }



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
                // Reference: https://android--examples.blogspot.ca/2015/05/how-to-use-datepickerdialog-in-android.html
                //Setting OnDateSetListener on the DatePickerDialog
                DatePickerDialog.OnDateSetListener dateCallback = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //Set the startDate textview with the date selected
                        startDate_TextView.setText(year+"-"+ ++monthOfYear +"-"+ dayOfMonth);
                    }
                };

                // The current day, month, year
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                start_Date = new DatePickerDialog(mActivity, dateCallback, year, month, day);
                // The start date need not be after the current date
                // Reference: https://stackoverflow.com/questions/32231734/datepicker-crashes-when-select-date-beyond-min-or-max-date-android-5-1
                Calendar cal1 = Calendar.getInstance();
                cal1.set(Calendar.HOUR_OF_DAY, 23);
                cal1.set(Calendar.MINUTE, 59);
                cal1.set(Calendar.SECOND, 59);
                cal1.set(Calendar.MILLISECOND,999);
                start_Date.getDatePicker().setMaxDate(cal1.getTimeInMillis());
                //Showing the DatePickerDialog
                start_Date.show();
            }
        });

        cancelButton = (Button) findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to MyHabits Screen
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();

        // Check if the user selected an existed Habit
        if (bundle == null) {
            // Open an empty Habit UI
            isNewHabit = true;

        } else {
            isNewHabit = false;

            // Get the position of the selected Habit object in the HabitList
            selected_pos = bundle.getInt(HabitOverviewAdapter.SELECTED_HABIT_POSITION);
            selected_Habit = MyHabitsActivity.getMyHabitList().getHabits().get(selected_pos);

            // Initialize the Habit UI with the selected_Habit info
            initializeHabitUI();
        }

    }

    /**
     * Get an array of checked CheckBox num
     *
     * @return ArrayList<Integer>: an array of checked CheckBox num
     */
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

    /**
     * Add a new Habit object or save the changes made to the selected Habit object
     *
     * @param view: View, add Habit button View
     */
    public void addNewHabit(View view) {

        if (isNewHabit) {
            // User is about to add a new Habit
            boolean isSuccessful = checkDataValid();

            // Check if the title entered exist in the HabitList of the user
            String title_entered = Habit_name_EditText.getText().toString().trim();
            if (MyHabitsActivity.getMyHabitList().hasHabitTitle(title_entered)) {
                Habit_name_EditText.setError("This type of Habit already exists. Please enter a new Type of Habit, or edit the old one");
                isSuccessful = false;
            }

            if (isSuccessful) {
                // Create a new Habit and add to the User's HabitList
                Habit new_Habit = createNewHabit();

                if (new_Habit != null) {
                    // Add this new Habit to the HabitList of the login User
                    HabitList mHabitList = MyHabitsActivity.getMyHabitList();
                    mHabitList.add(new_Habit);

                    // Update Data in My Habits Activity
                    MyHabitsActivity.updateHabitList(mHabitList.getHabits());
                    // Update Data in online and offline data storage
                    MyHabitsActivity.updateDataStorage();
                }

                // Close HabitDetail
                finish();
            }

        } else{
            // User is about to edit an existing Habit
            boolean isSuccessful = checkDataValid();

            // Check if the title entered exist in the HabitList of the user other than the selected position
            String title_entered = Habit_name_EditText.getText().toString().trim();
            if (MyHabitsActivity.getMyHabitList().hasHabitTitle(title_entered, selected_pos)) {
                Habit_name_EditText.setError("This type of Habit already exists. Please enter a new Type of Habit, or edit the old one");
                isSuccessful = false;
            }

            if (isSuccessful) {
                // Create a new Habit and add to the User's HabitList
                Habit new_Habit = createNewHabit();

                ArrayList<String> testPlan = new_Habit.getPlan().getScheduleDescription();

                for (String plan : testPlan) {
                    Log.d("Test", plan);
                }

                if (new_Habit != null) {
                    // Replace this new Habit to the Habit at the selected position in HabitList
                    HabitList mHabitList = MyHabitsActivity.getMyHabitList();
                    mHabitList.getHabits().set(selected_pos, new_Habit);

                    // Update Data in My Habits Activity
                    MyHabitsActivity.updateHabitList(mHabitList.getHabits());
                    // Update Data in online and offline data storage
                    MyHabitsActivity.updateDataStorage();
                }

                // Close HabitDetail
                finish();
            }
        }
    }

    /**
     * Check if the data in the fields are valid
     *
     * @return Boolean: true: the data in the fields are valid
     *                  false: the data in the fields are not valid
     */
    private boolean checkDataValid() {
        boolean isSuccessful = true;
        String Habit_name = Habit_name_EditText.getText().toString().trim();
        String Habit_reason  = Habit_reason_EditText.getText().toString().trim();

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

        if (checkedCheckbox.size() == 0){
            Toast.makeText(this, "Please select at least one plan day", Toast.LENGTH_LONG).show();
            isSuccessful = false;
        }

        return isSuccessful;
    }

    /**
     * Create a new Habit according to the data user entered
     *
     * @return Habit: the new Habit object created
     */
    private Habit createNewHabit() {

        String habit_Name = Habit_name_EditText.getText().toString().trim();
        String habit_Reason = Habit_reason_EditText.getText().toString().trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date habit_Date = simpleDateFormat.parse(startDate_TextView.getText().toString());

            ArrayList<Integer> checkedCheckbox = checkedCheckBox();
            Plan habit_Plan = new Plan();

            for (Integer day_Int : checkedCheckbox){
                // Set the checked Checkbox's corresponding position to true
                habit_Plan.setToDo(day_Int, true);
            }

            Habit new_Habit = new Habit(habit_Name, habit_Reason, habit_Date, habit_Plan);

            return new_Habit;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Initialize the Habit Detail UI with the selected Habit object
     */
    private void initializeHabitUI(){
        Habit_name_EditText.setText(selected_Habit.getName());
        Habit_reason_EditText.setText(selected_Habit.getReason());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDate_TextView.setText(simpleDateFormat.format(selected_Habit.getStartDate()));

        Plan habit_Plan = selected_Habit.getPlan();

        for (int day = 0; day < 7; day++) {
            switch (day) {
                case 0:
                    // Sunday
                    sunday_CheckBox.setChecked(habit_Plan.getSchedule().get(day));

                    break;

                case 1:
                    // Monday
                    monday_CheckBox.setChecked(habit_Plan.getSchedule().get(day));

                    break;

                case 2:
                    // Tuesday
                    tuesday_CheckBox.setChecked(habit_Plan.getSchedule().get(day));

                    break;

                case 3:
                    // Wednesday
                    wednesday_CheckBox.setChecked(habit_Plan.getSchedule().get(day));

                    break;

                case 4:
                    // Thursday
                    thursday_CheckBox.setChecked(habit_Plan.getSchedule().get(day));

                    break;

                case 5:
                    // Friday
                    friday_CheckBox.setChecked(habit_Plan.getSchedule().get(day));

                    break;

                case 6:
                    // Saturday
                    saturday_CheckBox.setChecked(habit_Plan.getSchedule().get(day));

                    break;
            }
        }
    }
}



