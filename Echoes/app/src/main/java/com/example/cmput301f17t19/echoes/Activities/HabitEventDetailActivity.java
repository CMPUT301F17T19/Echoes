/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Adapters.HabitEventOverviewAdapter;
import com.example.cmput301f17t19.echoes.Adapters.ToDoListAdapter;
import com.example.cmput301f17t19.echoes.Controllers.SelectPhotoController;
import com.example.cmput301f17t19.echoes.Controllers.TakePhotoController;
import com.example.cmput301f17t19.echoes.Exceptions.ArgTooLongException;
import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.Models.HabitEventList;
import com.example.cmput301f17t19.echoes.R;
import com.example.cmput301f17t19.echoes.Utils.LocationUtil;
import com.example.cmput301f17t19.echoes.Utils.PhotoOperator;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * HabitEventDetail Activity
 *
 * @author Xinrui Lu, Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitEventDetailActivity extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST = 1;
    public static final String UserNameHE_TAG = "USERNAMEHE_TAG";

    // Check if the user wants to create a new HabitEvent or select a existed HabitEvent
    private boolean isNewHabitEvent;

    // Check if the user wants to add a new HabitEvent from TO do list
    private boolean isFromToDoList;
    // The position of the selected Habit Type in to do list
    private int todo_selected_pos;

    // The username of the user creating this habit event
    private String loginUserName;

    // The position of the selected HabitEvent
    private int selected_pos;
    // The selected Habit object
    private HabitEvent selected_HabitEvent;

    private Spinner Types;
    private ArrayList<String> spinnerTypes;
    private EditText WriteComment;
    private TextView date_TextView;
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

    // location raido group
    private RadioGroup loc_RadioGroup;
    private RadioButton loc_yes_RadioButton;
    private RadioButton loc_no_RadioButton;

    private TextView lat_loc_TextView;
    private TextView lon_loc_TextView;

    private Button select_loc_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.redPink));
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);

        // Get the username of the user creating this habit event
        Intent intent = getIntent();

        if (intent.getStringExtra(UserNameHE_TAG) != null) {
            loginUserName = intent.getStringExtra(UserNameHE_TAG);
        }

        // Check if the user enter this activity from to do list
        if (intent.getIntExtra(ToDoListAdapter.TODO_Pos_TAG, -1) == -1) {
            isFromToDoList = false;
        } else {
            isFromToDoList = true;
        }

        mActivity = this;

        save_button = (Button) findViewById(R.id.Save);
        cancel_button = (Button) findViewById(R.id.Cancel);

        Types = (Spinner) findViewById(R.id.Types);
        date_TextView = (TextView) findViewById(R.id.Get_Date);
        WriteComment = (EditText) findViewById(R.id.WriteComment);

        select_photo_button = (Button) findViewById(R.id.Upload);
        take_photo_button = (Button) findViewById(R.id.Take_a_photo);

        loc_RadioGroup = (RadioGroup) findViewById(R.id.loc_radiogroup);
        loc_yes_RadioButton = (RadioButton) findViewById(R.id.Yes);
        loc_no_RadioButton = (RadioButton) findViewById(R.id.No);

        lat_loc_TextView = (TextView) findViewById(R.id.lat_location);
        lon_loc_TextView = (TextView) findViewById(R.id.lon_location);
        select_loc_Button = (Button) findViewById(R.id.habitevent_select_loc_button);

        imageView = (ImageView) findViewById(R.id.imageId);

        // Set the Spinner
        spinnerTypes = getUserHabitTypes();


        this.setTitle("Habit Event Detail");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));



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
                        date_TextView.setText(String.format(Locale.getDefault(), "%04d", year) + "-" + String.format(Locale.getDefault(), "%02d", ++monthOfYear) + "-" + String.format(Locale.getDefault(), "%02d", dayOfMonth));
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
                cal1.set(Calendar.MILLISECOND, 999);
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

        // Click radio button
        loc_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(i);

                switch (i) {
                    case R.id.Yes:
                        boolean yesIsChecked = checkedRadioButton.isChecked();

                        if (yesIsChecked) {
                            if (lat_loc_TextView.getText().toString().trim().length() == 0 ||
                                    lon_loc_TextView.getText().toString().trim().length() == 0) {
                                // Set current location as default
                                FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);

                                // Reference: https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
                                boolean isLocPermissionAllowed = LocationUtil.checkLocationPermission(mActivity);

                                if (isLocPermissionAllowed) {
                                    mFusedLocationClient.getLastLocation()
                                            .addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {
                                                @Override
                                                public void onSuccess(Location location) {
                                                    // Got last known location. In some rare situations this can be null.
                                                    if (location != null) {
                                                        lat_loc_TextView.setText(Double.toString(location.getLatitude()));
                                                        lon_loc_TextView.setText(Double.toString(location.getLongitude()));

                                                    } else {
                                                        Toast.makeText(mActivity, "Please select a location.", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                } else {
                                    Toast.makeText(mActivity, "Access Location Permission Denied. Please allow location accession.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        break;

                    case R.id.No:
                        boolean noIsChecked = checkedRadioButton.isChecked();

                        if (noIsChecked) {
                            lat_loc_TextView.setText("");
                            lon_loc_TextView.setText("");
                        }

                        break;
                }
            }
        });

        // Click select location button to open map to select Habit event location
        select_loc_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if yes button clicked
                if (loc_yes_RadioButton.isChecked()) {
                    // Open Place Picker
                    // Reference: https://developers.google.com/places/android-api/placepicker
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        startActivityForResult(builder.build(mActivity), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mActivity, "Please check attach location first", Toast.LENGTH_LONG).show();
                }
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


        // if the user does not enter this activity from to do list
        if (! isFromToDoList) {
            // Check if the user selected an existed Habit
            if (intent.getIntExtra(HabitEventOverviewAdapter.SELECTED_HABIT_EVENT_POSITION, -1) == -1) {
                // Open an empty HabitEvent UI
                isNewHabitEvent = true;
            } else {
                isNewHabitEvent = false;

                // Get the position of the selected HabitEvent object in the HabitEventList
                selected_pos = intent.getIntExtra(HabitEventOverviewAdapter.SELECTED_HABIT_EVENT_POSITION, -1);
                selected_HabitEvent = HabitHistoryActivity.getDisplayedHabitEventList().getHabitEvents().get(selected_pos);

                // Initialize the Habit UI with the selected_HabitEvent info
                initializeHabitEventUI();
            }

        } else {
            // The user enter this page from to do list
            isNewHabitEvent = true;
            // Get the position of the selected Habit Type in the To do list
            todo_selected_pos = intent.getIntExtra(ToDoListAdapter.TODO_Pos_TAG, -1);
            // Set the Habit Event's Habit Type and Date
            String habitType = ToDoActivity.getNameArray().get(todo_selected_pos);

            if (!spinnerTypes.contains(habitType)) {
                // the Habit Type is deleted, add it to the end of types list and Spinner
                spinnerTypes.add(habitType);
            }

            // Set the spinner at this position
            int type_pos = spinnerTypes.indexOf(habitType);
            Types.setSelection(type_pos);

            // Set the current day
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date_TextView.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
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
            Bitmap bitmap = SelectPhotoController.loadPhoto(this, data);

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
        else if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(data, this);
            LatLng selectedLoc = place.getLatLng();
            // Set UI
            lat_loc_TextView.setText(Double.toString(selectedLoc.latitude));
            lon_loc_TextView.setText(Double.toString(selectedLoc.longitude));
        }
    }

    /**
     * Get the login user's all habit types
     *
     * @return ArrayList<String>: an arraylist of user's habit types
     */
    private ArrayList<String> getUserHabitTypes() {
        ArrayList<Habit> mHabits;

        if (!isFromToDoList) {
            // The arraylist of all habits that the login user has
            mHabits = HabitHistoryActivity.getLogin_userProfile().getHabit_list().getHabits();
        } else {
            // Get Habits from to do activity
            mHabits = ToDoActivity.getLogin_userProfile().getHabit_list().getHabits();
        }

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

        // Set Event Location
        if (selected_HabitEvent.getLocation() != null) {
            lat_loc_TextView.setText(Double.toString(selected_HabitEvent.getLocation().getLatitude()));
            lon_loc_TextView.setText(Double.toString(selected_HabitEvent.getLocation().getLongitude()));

            loc_yes_RadioButton.setChecked(true);
        } else {
            loc_no_RadioButton.setChecked(true);
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
            String eventType;
            if (Types.getSelectedItem() == null) {
                eventType = "";
                isValid = false;
                Toast.makeText(this, "You have no habit. Please create a habit.", Toast.LENGTH_LONG).show();

            } else {
                eventType = Types.getSelectedItem().toString();
            }

            String eventDate = date_TextView.getText().toString();

            if (!isFromToDoList) {
                if (HabitHistoryActivity.getLogin_userProfile().getHabit_event_list().hasHabitEvent(eventType, eventDate)) {
                    Toast.makeText(this, "The HabitEvent for this Type has already done on the selected date.", Toast.LENGTH_LONG).show();

                    isValid = false;
                }
            } else {
                if (ToDoActivity.getLogin_userProfile().getHabit_event_list().hasHabitEvent(eventType, eventDate)) {
                    Toast.makeText(this, "The HabitEvent for this Type has already done on the selected date.", Toast.LENGTH_LONG).show();

                    isValid = false;
                }
            }

            if (isValid) {
                // Create a new Habit Event and add to user's HabitEventList
                HabitEvent new_HabitEvent = createNewHabitEvent();

                if (new_HabitEvent != null) {
                    if (!isFromToDoList) {
                        // Add this new HabitEvent to the HabitEventList of the login User
                        HabitEventList mHabitEventList = HabitHistoryActivity.getLogin_userProfile().getHabit_event_list();
                        mHabitEventList.add(new_HabitEvent);
                        // Sort List
                        mHabitEventList.sortList();

                        // Update Data in HabitHistory Activity
                        HabitHistoryActivity.updateHabitEventList(mHabitEventList.getHabitEvents());
                        // Update Data in online and offline data storage
                        HabitHistoryActivity.updateDataStorage();
                    } else {
                        // Add this new HabitEvent to the HabitEventList of the login User
                        HabitEventList mHabitEventList = ToDoActivity.getLogin_userProfile().getHabit_event_list();
                        mHabitEventList.add(new_HabitEvent);
                        // Sort List
                        mHabitEventList.sortList();

                        // Update Data in User Profile
                        ToDoActivity.getLogin_userProfile().getHabit_event_list().setHabitEvents(mHabitEventList.getHabitEvents());
                        // Remove the view of the selected position in TO DO LIST
                        ToDoActivity.getNameArray().remove(todo_selected_pos);
                        ToDoActivity.getReasonArray().remove(todo_selected_pos);
                        // Update Data in online and offline data storage
                        ToDoActivity.updateDataStorage();
                    }
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

            new_HabitEvent = new HabitEvent(eventType, eventDate, loginUserName);

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

            // Set whether attach location
            if (loc_yes_RadioButton.isChecked()) {
                new_HabitEvent.setLocationIndicator(true);
            } else {
                new_HabitEvent.setLocationIndicator(false);
            }

            // Set location
            if (lat_loc_TextView.getText().toString().length() != 0 && lon_loc_TextView.getText().toString().length() != 0) {
                Double loc_Lat = Double.parseDouble(lat_loc_TextView.getText().toString());
                Double loc_Lon = Double.parseDouble(lon_loc_TextView.getText().toString());

                Location location = new Location(new_HabitEvent.getTitle() + " " + simpleDateFormat.format(new_HabitEvent.getStartDate()));
                location.setLatitude(loc_Lat);
                location.setLongitude(loc_Lon);

                new_HabitEvent.setLocation(location);
            }
        }

        return new_HabitEvent;
    }
}
