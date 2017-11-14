/*
 * Class Name: HabitEventDetailActivity
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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.cmput301f17t19.echoes.SelectPhotoController.loadPhoto;

/**
 * HabitEventDetail Activity
 *
 * @author Xinrui Lu, Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitEventDetailActivity extends AppCompatActivity {

    // Check if the user wants to create a new HabitEvent or select a existed HabitEvent
    private boolean isNewHabitEvent;

    // The position of the selected HabitEvent
    private int selected_pos;
    // The selected Habit object
    private HabitEvent selected_HabitEvent;

    private Spinner Types;
    private ArrayList<String> spinnerTypes;
    private EditText WriteComment;
    private TextView date_TextView;
    private EditText Type_Location;
    private DatePickerDialog datePickerDialog;
    private Activity mActivity;

    private Button select_photo_button;
    private Button take_photo_button;

    private Button save_button;
    private Button cancel_button;

    private ImageView imageView;

    private byte[] eventImage;

    // The old habit type
    private String old_HabitType;
    // The old habit date
    private String old_HabitDate;

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
        setContentView(R.layout.activity_habit_event_details);

        mActivity = this;

        save_button = (Button) findViewById(R.id.Save);
        cancel_button = (Button) findViewById(R.id.Cancel);

        Types = (Spinner)findViewById(R.id.Types);
        date_TextView = (TextView) findViewById(R.id.Get_Date);
        WriteComment = (EditText)findViewById(R.id.WriteComment);
        Type_Location = (EditText)findViewById(R.id.Type_Location);

        select_photo_button = (Button) findViewById(R.id.Upload);
        take_photo_button = (Button) findViewById(R.id.Take_a_photo);

        imageView = (ImageView) findViewById(R.id.imageId);

        // Set the Spinner
        spinnerTypes =  getUserHabitTypes();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerTypes);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Types.setAdapter(spinnerAdapter);

        //Setting an OnclickListener on the Button
        date_TextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Reference: https://android--examples.blogspot.ca/2015/05/how-to-use-datepickerdialog-in-android.html
                //Setting OnDateSetListener on the DatePickerDialog
                DatePickerDialog.OnDateSetListener dateCallback = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //Set the date textview with the date selected
                        date_TextView.setText(year+"-"+ ++monthOfYear +"-"+ dayOfMonth);
                    }
                };

                //Creating an object of DatePickerDialog incontext of the Mainactivity
                // The current day, month, year
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //dateCallback is called which defined below
                datePickerDialog = new DatePickerDialog(mActivity, dateCallback, year, month, day);
                // The event date need not be after the current date
                // Reference: https://stackoverflow.com/questions/32231734/datepicker-crashes-when-select-date-beyond-min-or-max-date-android-5-1
                Calendar cal1 = Calendar.getInstance();
                cal1.set(Calendar.HOUR_OF_DAY, 23);
                cal1.set(Calendar.MINUTE, 59);
                cal1.set(Calendar.SECOND, 59);
                cal1.set(Calendar.MILLISECOND,999);
                datePickerDialog.getDatePicker().setMaxDate(cal1.getTimeInMillis());
                //Showing the DatePickerDialog
                datePickerDialog.show();
            }
        });

        // Select Photo button
        select_photo_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Ask user's permission for reading from gallery
                SelectPhotoController.askPermission(mActivity);

                // Create the intent of selecting photo from gallery and start this activity for result
                Intent selectPhotoIntent = SelectPhotoController.selectPhotoFromGallery();
                startActivityForResult(selectPhotoIntent, SelectPhotoController.SELECT_PHOTO_GALLERY_CODE);

            }
        });

        // Take Photo button
        take_photo_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Ask user's permission for writing to gallery
                TakePhotoController.askPermission(mActivity);

                // Create the intent of taking a photo and start this activity for result
                Intent takePhotoIntent = TakePhotoController.takePhotoIntent();
                startActivityForResult(takePhotoIntent, TakePhotoController.TAKE_PHOTO_CODE);
            }
        });

        // Save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHabitEvent();
            }
        });

        // Cancel button
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to HabitHistory Screen
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();

        // Check if the user selected an existed Habit
        if (bundle == null) {
            // Open an empty HabitEvent UI
            isNewHabitEvent = true;

        } else {
            isNewHabitEvent = false;

            // Get the position of the selected HabitEvent object in the HabitEventList
            selected_pos = bundle.getInt(HabitEventOverviewAdapter.SELECTED_HABIT_EVENT_POSITION);
            selected_HabitEvent = HabitHistoryActivity.getDisplayedHabitEventList().getHabitEvents().get(selected_pos);

            // Initialize the Habit UI with the selected_HabitEvent info
            initializeHabitEventUI();
        }

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
            Bitmap bitmap = loadPhoto(this, data);

            // Resize the bitmap to user profile's size
            Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, imageView.getWidth(), imageView.getHeight());

            // Set the scaled profile photo to the view
            imageView.setImageBitmap(resizeBitmap);

            eventImage = PhotoOperator.bitmapToByteArray(resizeBitmap);

        }
        else if (requestCode == TakePhotoController.TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = TakePhotoController.loadPhoto(data);

            if (bitmap != null) {
                // Resize the bitmap to user profile's size
                Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, imageView.getWidth(), imageView.getHeight());

                // Set the scaled profile photo to the view
                imageView.setImageBitmap(resizeBitmap);

                eventImage = PhotoOperator.bitmapToByteArray(resizeBitmap);
            }
        }
    }

    /**
     * Get the login user's all habit types
     *
     * @return ArrayList<String>: an arraylist of user's habit types
     */
    private ArrayList<String> getUserHabitTypes() {
        // The arraylist of all habits that the login user has
        ArrayList<Habit> mHabits = HabitHistoryActivity.getLogin_userProfile().getHabit_list().getHabits();

        ArrayList<String> habitTypes = new ArrayList<String>();

        for (Habit habit : mHabits) {
            if (!habitTypes.contains(habit.getName())) {
                // Add this Habit Typr to habitTypes
                habitTypes.add(habit.getName());
            }
        }

        return habitTypes;
    }

    /**
     * Initialize the HabitEvent Detail UI with the selected HabitEvent object
     */
    private void initializeHabitEventUI() {
        // Set Event Type
        String eventType = selected_HabitEvent.getTitle();

        if (spinnerTypes.contains(eventType)) {
            // Set the spinner at this position
            int type_pos = spinnerTypes.indexOf(eventType);
            Types.setSelection(type_pos);
        } else {
            // the Habit Type is deleted, add it to the end of types list and Spinner
            spinnerTypes.add(eventType);

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerTypes);

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Types.setAdapter(spinnerAdapter);
        }

        // Set Event Comment
        WriteComment.setText(selected_HabitEvent.getComments());

        // Set Event Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date_TextView.setText(simpleDateFormat.format(selected_HabitEvent.getStartDate()));

        // Set Event Photo
        if (selected_HabitEvent.getEventPhoto() != null) {
            eventImage = selected_HabitEvent.getEventPhoto();
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(eventImage, 0, eventImage.length));
        }

        // Assign old habit type and habit date
        old_HabitType = eventType;
        old_HabitDate = date_TextView.getText().toString();
    }

    /**
     * Save the changes to the selected HabitEvent, or create a new HabitEvent and save to HabitEventList
     */
    private void saveHabitEvent(){
        if (isNewHabitEvent) {
            // The user is about to add a new HabitEvent
            boolean isValid = checkValid();

            // Check if the HabitEvent has the same title and event date exist in HabitEventList
            String eventType = Types.getSelectedItem().toString();
            String eventDate = date_TextView.getText().toString();

            if (HabitHistoryActivity.getLogin_userProfile().getHabit_event_list().hasHabitEvent(eventType, eventDate)) {
                Toast.makeText(this, "The HabitEvent for this Type has already done on the selected date.", Toast.LENGTH_LONG).show();

                isValid = false;
            }

            if (isValid) {
                // Create a new Habit Event and add to user's HabitEventList
                HabitEvent new_HabitEvent = createNewHabitEvent();

                if (new_HabitEvent != null) {
                    // Add this new HabitEvent to the HabitEventList of the login User
                    HabitEventList mHabitEventList = HabitHistoryActivity.getLogin_userProfile().getHabit_event_list();
                    mHabitEventList.add(new_HabitEvent);
                    // Sort List
                    mHabitEventList.sortList();

                    // Update Data in HabitHistory Activity
                    HabitHistoryActivity.updateHabitEventList(mHabitEventList.getHabitEvents());
                    // Update Data in online and offline data storage
                    HabitHistoryActivity.updateDataStorage();
                }

                // Close HabitEvent Detail
                finish();
            }

        } else {
            // The user is about to edit an existing HabitEvent
            boolean isValid = checkValid();

            // Check if the HabitEvent has the same title and event date exist in HabitEventList other than the selected position
            String eventType = Types.getSelectedItem().toString();
            String eventDate = date_TextView.getText().toString();

            // Get the old position of the selected HabitEvent in all HabitEventList
            int old_position = HabitHistoryActivity.getOldPosition(old_HabitType, old_HabitDate);

            // Check if same habit event exists
            if (HabitHistoryActivity.getLogin_userProfile().getHabit_event_list().hasHabitEvent(eventType, eventDate, old_position)) {
                Toast.makeText(this, "The HabitEvent for this Type has already done on the selected date.", Toast.LENGTH_LONG).show();

                isValid = false;
            }

            if (isValid) {
                // Create a new Habit Event and add to user's HabitEventList
                HabitEvent new_HabitEvent = createNewHabitEvent();

                if (new_HabitEvent != null) {
                    // Replace this new HabitEvent to the HabitEvent at the selected position in HabitEventList
                    HabitEventList mHabitEventList = HabitHistoryActivity.getLogin_userProfile().getHabit_event_list();
                    mHabitEventList.getHabitEvents().set(old_position, new_HabitEvent);
                    // Sort List
                    mHabitEventList.sortList();

                    // Update Data in HabitHistory Activity
                    HabitHistoryActivity.updateHabitEventList(mHabitEventList.getHabitEvents());
                    // Update Data in online and offline data storage
                    HabitHistoryActivity.updateDataStorage();
                }

                // Close HabitEvent Detail
                finish();
            }
        }
    }

    /**
     * Check if the input field is valid
     *
     * @return Boolean: true, the input field is valid
     *                  false, the input field is valid
     */
    private boolean checkValid() {
        boolean isValid = true;

        String event_comment = WriteComment.getText().toString().trim();

        // comment no more than 20 characters
        if (event_comment.length() > 20) {
            WriteComment.setError("The length of comment cannot be more than 20 characters");
            isValid = false;
        }

        // Check date
        if (date_TextView.getText().toString().equals("Click to select event date")){
            Toast.makeText(this, "Please select event date", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Create a new Habit Event
     *
     * @return HabitEvent: the new HabitEvent object created
     */
    private HabitEvent createNewHabitEvent() {
        HabitEvent new_HabitEvent = null;

        String eventType = Types.getSelectedItem().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date eventDate = simpleDateFormat.parse(date_TextView.getText().toString());

            new_HabitEvent = new HabitEvent(eventType, eventDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (new_HabitEvent != null) {
            // Set comment
            if (WriteComment.getText().toString().trim().length() != 0) {
                try {
                    new_HabitEvent.setComments(WriteComment.getText().toString().trim());
                } catch (ArgTooLongException e) {
                    e.printStackTrace();
                }
            }

            // Set image
            if (eventImage != null) {
                new_HabitEvent.setEventPhoto(eventImage);
            }
        }

        return new_HabitEvent;
    }
}