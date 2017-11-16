/*
 * Class Name: HabitHistoryActivity
 *
 * Version: Version 1.0
 *
 * Date: October 19th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.cmput301f17t19.echoes.LoginActivity.LOGIN_USERNAME;

/**
 * Habit History UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class HabitHistoryActivity extends AppCompatActivity {

    // The username of the login user
    private static String login_Username;
    // The user profile of the login user
    private static UserProfile login_userProfile;
    // The HabitEventList displayed of the login user
    private static HabitEventList mTypeHabitEventList;

    private RecyclerView habitEventsRecyclerView;
    private static HabitEventOverviewAdapter habitEventOverviewAdapter;

    private static Context mContext;
    private static String type;

    private Button addEventButton;
    private Button habitEventsMapButton;
    private static Spinner Types;
    private ArrayList<String> spinnerTypes;

    // Search editText
    private static EditText search_EditText;
    // Search button
    private Button search_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("T", "oncreate");


        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history);
        this.setTitle(R.string.habithistory);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mContext = this;
        spinnerTypes = new ArrayList<String>();

        // Get the login username and user Profile
        Intent intent = getIntent();
        if (intent.getStringExtra(LOGIN_USERNAME) != null) {
            login_Username = intent.getStringExtra(LOGIN_USERNAME);
        } else {
            // For test
            login_Username = "dummy3";
        }

        addEventButton = (Button) findViewById(R.id.habitevents_add_button);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Habit Event Detail
                Intent habitEventDetail_Intent = new Intent(mContext, HabitEventDetailActivity.class);
                startActivity(habitEventDetail_Intent);
            }
        });

        habitEventsMapButton = (Button) findViewById(R.id.habitevents_map);

        habitEventsMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Will be enabled in Project Part 5", Toast.LENGTH_SHORT).show();
            }
        });

        // Search Edit Text
        search_EditText = (EditText) findViewById(R.id.search_comment_edittext);
        // Search Button
        search_Button = (Button) findViewById(R.id.search_comment_button);

        // Set up recycler view for habit event overview in the Habit History
        habitEventsRecyclerView = (RecyclerView) findViewById(R.id.habitevents_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitEventsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(habitEventsRecyclerView.getContext(),
                layoutManager.getOrientation());
        habitEventsRecyclerView.addItemDecoration(mDividerItemDecoration);

        habitEventsRecyclerView.setHasFixedSize(true);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // the User Profile of the login user
        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_Username);

        Types = (Spinner) findViewById(R.id.habithistory_filter);

        // The HabitEventList displayed of the login user
        mTypeHabitEventList = new HabitEventList();
        mTypeHabitEventList.setHabitEvents((ArrayList<HabitEvent>) login_userProfile.getHabit_event_list().getHabitEvents().clone());

        habitEventOverviewAdapter = new HabitEventOverviewAdapter(this);

        habitEventsRecyclerView.setAdapter(habitEventOverviewAdapter);

        // Set the Spinner
        spinnerTypes = getUserHabitTypes();
        type = spinnerTypes.get(0);
        search_EditText.setText("");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerTypes);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Types.setAdapter(spinnerAdapter);

        // setOnItemSelectedListener for spinner
        // Reference: https://stackoverflow.com/questions/12108893/set-onclicklistener-for-spinner-item
        Types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(!selectedItem.equals(type))
                {
                    // Update the HabitEventList to be displayed
                    type = selectedItem;
                    mTypeHabitEventList.setHabitEvents((ArrayList<HabitEvent>) filterHabitEvent().getHabitEvents().clone());

                    habitEventOverviewAdapter.notifyDataSetChanged();

                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the searched word
                String enteredWord = search_EditText.getText().toString().trim();
                // Filter the Recycler View
                mTypeHabitEventList.setHabitEvents((ArrayList<HabitEvent>) filterHabitEvent().getHabitEvents().clone());

                habitEventsRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        // Implement swipe to left to delete for recycler view
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Remove the swiped item from the list and screen
                int position = viewHolder.getAdapterPosition();

                // Move the swiped item in original list
                String oldType = mTypeHabitEventList.get(position).getTitle();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String oldDate = simpleDateFormat.format(mTypeHabitEventList.get(position).getStartDate());

                int old_position = getOldPosition(oldType, oldDate);

                login_userProfile.getHabit_event_list().remove(old_position);

                mTypeHabitEventList.remove(position);

                // Update the data saved in file and screen
                updateDataStorage();
            }

            // Reference: https://stackoverflow.com/questions/30820806/adding-a-colored-background-with-text-icon-under-swiped-row-when-using-androids
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                    View itemView = viewHolder.itemView;
                    if (dX < 0) {
                        // Show the delete icon
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.delete_drawable);
                        Paint paint = new Paint();
                        paint.setARGB(255, 255, 0, 0);
                        c.drawBitmap(bitmap, dX + (float) itemView.getWidth(),
                                (float) itemView.getTop() + (float) itemView.getHeight() / 2 - (float) bitmap.getHeight() / 2,
                                paint);
                    }

                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        // Attach item touch helper to the recycler view
        itemTouchHelper.attachToRecyclerView(habitEventsRecyclerView);
    }

    // Reference: https://developer.android.com/guide/topics/search/search-dialog.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.habithistory_app_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_menu:

                // Go back to main menu
                Intent mainMenu_intent = new Intent(this, main_menu.class);
                mainMenu_intent.putExtra(LOGIN_USERNAME, login_Username);
                startActivity(mainMenu_intent);

                finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static String SpinnerTypeSelected() {
        return type = Types.getSelectedItem().toString();
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

        habitTypes.add("All");

        for (Habit habit : mHabits) {
            if (!habitTypes.contains(habit.getName())) {
                // Add this Habit Type to habitTypes
                habitTypes.add(habit.getName());
            }
        }

        return habitTypes;
    }

    /**
     * Get the displayed HabitEventList of the login user
     *
     * @return HabitEventList: the displayed HabitEventList of the login user
     */
    public static HabitEventList getDisplayedHabitEventList() {
        return mTypeHabitEventList;
    }

    /**
     * Get the user Profile of the login user
     *
     * @return UserProfile: the user Profile of the login user
     */
    public static UserProfile getLogin_userProfile() {
        return login_userProfile;
    }

    /**
     * Update the HabitEventList of the Logged-in User
     *
     * @param updated_HabitEventList: ArrayList<HabitEvent>, the update HabitEventList of the logged-in User
     */
    public static void updateHabitEventList(ArrayList<HabitEvent> updated_HabitEventList) {
        login_userProfile.getHabit_event_list().setHabitEvents((ArrayList<HabitEvent>) updated_HabitEventList.clone());
        mTypeHabitEventList.setHabitEvents((ArrayList<HabitEvent>) updated_HabitEventList.clone());
        type = "All";
        search_EditText.setText("");
    }

    /**
     * Update Habits History Screen and User Profile file and online data of the logged-in user
     */
    public static void updateDataStorage() {
        // Update Screen
        habitEventOverviewAdapter.notifyDataSetChanged();

        // Update offline file
        OfflineStorageController offlineStorageController = new OfflineStorageController(mContext, login_userProfile.getUserName());
        offlineStorageController.saveInFile(login_userProfile);

        // Update Online data
        ElasticSearchController.syncOnlineWithOffline(login_userProfile);
    }

    /**
     * Get the old position of the selected HabitEvent in all HabitEventList
     *
     * @param old_HabitType: String, the old habit type
     * @param old_HabitDate: String, the old habit date in string
     *
     * @return Integer: the old position of selected HabitEvent in user's HabitEventList
     */
    public static int getOldPosition(String old_HabitType, String old_HabitDate) {
        int old_position = 0;
        HabitEventList allHabitEvents = HabitHistoryActivity.getLogin_userProfile().getHabit_event_list();

        for (int i = 0; i < allHabitEvents.size(); i++) {
            String thisType = allHabitEvents.get(i).getTitle();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String thisDate = simpleDateFormat.format(allHabitEvents.get(i).getStartDate());

            if (old_HabitType.equals(thisType) && old_HabitDate.equals(thisDate)) {
                // Find the old_position
                old_position = i;
            }
        }

        return old_position;
    }

    /**
     * Filter Habit Event type and searched word
     */
    private HabitEventList filterHabitEvent() {
        HabitEventList filteredHabitEventList = new HabitEventList();

        // Get the word filter
        String searchedWord = search_EditText.getText().toString().trim();
        // Get the type filter
        String filteredType = SpinnerTypeSelected();

        // The HabitEventList of login user
        HabitEventList user_HabitEventList = login_userProfile.getHabit_event_list();

        for (HabitEvent habitEvent : user_HabitEventList.getHabitEvents()) {
            if (filteredType.equals("All")) {
                // All types
                if (searchedWord.equals("")) {
                    return user_HabitEventList;
                } else {
                    if (habitEvent.getComments().contains(searchedWord)) {
                        filteredHabitEventList.add(habitEvent);
                    }
                }
            } else {
                // Filtered type
                if (habitEvent.getComments().contains(searchedWord) && habitEvent.getTitle().equals(filteredType)) {
                    filteredHabitEventList.add(habitEvent);
                }
            }
        }

        return filteredHabitEventList;
    }
}