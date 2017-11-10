/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.cmput301f17t19.echoes.SelectPhotoController.loadPhoto;

/**
 * Created by xinrui on 2017-11-08.
 */

public class HabitEventDetailActivity extends AppCompatActivity {
    HabitEventList habitEventList = new HabitEventList();
    Spinner Types;
    EditText WriteComment;
    TextView date_TextView;
    EditText Type_Location;
    private TextView btnDatePicker;
    private DatePickerDialog datePickerDialog;
    private Activity mActivity;
    Date date;

    private Button select_photo_button;
    private Button take_photo_button;

    private ImageView imageView;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Types.setAdapter(adapter);

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