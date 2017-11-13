/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.cmput301f17t19.echoes.SelectPhotoController.loadPhoto;

/**
 * Created by xinrui on 2017-11-08.
 */

public class HabitEventDetailActivity extends AppCompatActivity {
    HabitEventList habitEventList = new HabitEventList();
    Spinner Types;
    ArrayAdapter<String> spinnerAdapter;
    EditText WriteComment;
    TextView date_TextView;
    EditText Type_Location;
    private TextView btnDatePicker;
    private DatePickerDialog datePickerDialog;
    private Activity mActivity;
    private boolean isNewEvent;
    private HabitEvent select_event;

    private Button select_photo_button;
    private Button take_photo_button;
    private Button cancelButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);

        mActivity = this;
        this.setTitle("Habit Details");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        final Button Save = (Button)findViewById(R.id.Save);
        Types = (Spinner)findViewById(R.id.Types);
        date_TextView = (TextView) findViewById(R.id.Get_Date);
        WriteComment = (EditText)findViewById(R.id.WriteComment);
        Type_Location = (EditText)findViewById(R.id.Type_Location);

        select_photo_button = (Button) findViewById(R.id.Upload);
        take_photo_button = (Button) findViewById(R.id.Take_a_photo);

        imageView = (ImageView) findViewById(R.id.imageId);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("1");
        spinnerArray.add("2");

        spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Types.setAdapter(spinnerAdapter);

        //Typecasting the Button
        btnDatePicker = (TextView) findViewById(R.id.Get_Date);

        //Setting an OnclickListener on the Button
        btnDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Setting OnDateSetListener on the DatePickerDialog
                DatePickerDialog.OnDateSetListener dateCallback = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//Displaying a Toast with the date selected
                        Toast.makeText(mActivity, "The date is : " + dayOfMonth+"/"+  ++monthOfYear +"/"+  year, Toast.LENGTH_LONG).show();

                        date_TextView.setText(dayOfMonth+"/"+  ++monthOfYear +"/"+  year);
                    }
                };

//Creating an object of DatePickerDialog incontext of the Mainactivity
                //dateCallback is called which defined below
                datePickerDialog = new DatePickerDialog(mActivity, dateCallback, 2017, 11, 12);
                //Showing the DatePickerDialog
                datePickerDialog.show();
            }
        });
        cancelButton = (Button) findViewById(R.id.Cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to HabitHistory
                finish();
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



//https://www.edureka.co/blog/android-tutorials-event-listeners/
//            @Override
//            public void set(String WriteComment) throws ArgTooLongException {
//                if (WriteComment.length() > 30)
//                    throw new ArgTooLongException();
//                else


        this.WriteComment = WriteComment;

//        Save.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                Counter tmp = new Counter(name.getText().toString(), d, Integer.parseInt(c_value.getText().toString()),
//                        Integer.parseInt(i_value.getText().toString()),
//                        comment.getText().toString());
//
//                clist.addCounter(tmp);
//                clist.save(NewCounterActivity.this);
//                Toast.makeText(getApplicationContext(), name.getText().toString() +
//                        " has been added!", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(NewCounterActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            isNewEvent = true;
        }else{
            initializeUI(select_event);
//            // Get the position of the selected Habit object in the HabitList
//            selected_pos = bundle.getInt(HabitOverviewAdapter.SELECTED_HABIT_POSITION);
//            selected_Habit = MyHabitsActivity.getMyHabitList().getHabits().get(selected_pos);
            isNewEvent = false;
        }
    }
    private void initializeUI (HabitEvent select_event){
        String event_HabitType = select_event.getTitle();

        int spinnerPosition = spinnerAdapter.getPosition(event_HabitType);
        Types.setSelection(spinnerPosition);

        WriteComment.setText(select_event.getComments());
        date_TextView.setText(select_event.getStartDate().toString());

        if (select_event.getEventPhoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(select_event.getEventPhoto(), 0, select_event.getEventPhoto().length);
            imageView.setImageBitmap(bitmap);
        }
    }
    private void SaveChange() {
        // Check if the current values in the edit box are valid for create new event
        if (validEditText(WriteComment){
            // for newly created event
            if (isNewEvent) {
                HabitEvent new_event = createEvent();

                // Append this new event to the end of the array list
                ArrayList<HabitEvent> temp_events = HabitHistoryActivity.getHabitEventList();
                temp_events.add(new_event);

                // update the event list in the HabitHistoryActivity
                HabitHistoryActivity.setHabitEventList(temp_events);

                // Update the screen and data in the file
                HabitHistoryActivity.updateData();

                // close detail screen
                finish();

            } else {
                // for existing event
                // The old selected event object
                // Set the name, initial value and coumment
                selected_event.setWriteComment(WriteComment.getText().toString());
                int spinnerPosition = spinnerAdapter.getPosition(event_HabitType);
                selected_event.setTypes(Types.getSelection(spinnerPosition);
                selected_event.setText(select_event.getStartDate().toString());

                // Check if the Edit box is empty
                if (WriteComment.getText().toString().trim().equals("")) {
                    // Set current
                    WriteComment.setError("Comment cannot be empty");
                } else {
                    // Check if user changed the current value
                    String old_comment = selected_event.getComment();
                    String new_comment = WriteComment.getText().toString();

                    if (old_comment != new_comment) {
                        // Set the comment to new comment
                        selected_event.setComment(new_comment);
                    }

                    // The old array list of event in the event list
                    ArrayList<HabitEvent> temp_events = HabitHistoryActivity.getHabitEventList();

                    // set the event object at the position of the selected event object to the new one
                    temp_events.set(event_position, selected_event);

                    // update the event list in the EventHistoryActivity
                    HabitHistoryActivity.setCounterList(temp_counters);

                    // Update the screen and data in the file
                    HabitHistoryActivity.updateData();

                    // close detail screen
                    finish();
                }
            }
        }
    }
    private boolean validEditText(EditText editText) {

        if (editText.getError() != null) {
            // The edit text has error
            return false;

        } else if (editText.getText().toString().equals("") || editText.getText() == null) {
            // Current edit text are allowed to be empty
            if (editText.getId() == R.id.counter_curr_val_edit) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return true;
        }
    }

    /**
     * Create new Event object from the current detail screen
     */
    private HabitEvent createEvent() {
        HabitEvent new_counter = new HabitEvent(WriteComment.getText().toString(),
                Integer.parseInt(WriteComment.getText()));

        // Set new counter's current value and comment
        // Check if current value edit box is empty
        if (!mCounterCurrValEdit.getText().toString().trim().equals("")) {
            // if not, set the current value
            new_counter.setCountCurrVal(Integer.parseInt(mCounterCurrValEdit.getText().toString()));
        }

        new_counter.setCountComment(mCounterCommentEdit.getText().toString());

        return new_event;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SelectPhotoController.SELECT_PHOTO_GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = loadPhoto(this, data);

            // Resize the bitmap to user profile's size
            Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, imageView.getWidth(), imageView.getHeight());

            // Set the scaled profile photo to the view
            imageView.setImageBitmap(resizeBitmap);

        }
        else if (requestCode == TakePhotoController.TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = TakePhotoController.loadPhoto(data);

            if (bitmap != null) {
                // Resize the bitmap to user profile's size
                Bitmap resizeBitmap = PhotoOperator.resizeImage(bitmap, imageView.getWidth(), imageView.getHeight());

                // Set the scaled profile photo to the view
                imageView.setImageBitmap(resizeBitmap);
            }
        }
    }

}