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

import android.app.SearchManager;
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
import android.support.v7.widget.SearchView;
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
import android.widget.Spinner;
import android.widget.Toast;

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
    // The HabitEventList of the login user
    private static HabitEventList mHabitEventList;
    private static HabitEventList mTypeHabitEventList;

    private RecyclerView habitEventsRecyclerView;
    private static HabitEventOverviewAdapter habitEventOverviewAdapter;

    private static Context mContext;
    private static String type;

    private Button addEventButton;
    private Button habitEventsMapButton;
    private static Spinner Types;
    private ArrayList<String> spinnerTypes;


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
        setContentView(R.layout.activity_habit_history);
        this.setTitle(R.string.habithistory);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mContext = this;
        spinnerTypes.add("All");

        // Get the login username and user Profile
        Intent intent = getIntent();
        login_Username = intent.getStringExtra(LOGIN_USERNAME);

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
        login_userProfile = getLogin_UserProfile();

        Types = (Spinner)findViewById(R.id.habithistory_filter);

        // The HabitEventList of the login user
        mHabitEventList = login_userProfile.getHabit_event_list();

        habitEventOverviewAdapter = new HabitEventOverviewAdapter(this);

        habitEventsRecyclerView.setAdapter(habitEventOverviewAdapter);

        // Set the Spinner
        spinnerTypes =  getUserHabitTypes();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerTypes);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Types.setAdapter(spinnerAdapter);

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

                mHabitEventList.remove(position);

                habitEventOverviewAdapter.notifyItemRemoved(position);

                // Update the data saved in file
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

    // Reference: https://developer.android.com/training/search/setup.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.habithistory_app_bar, menu);

        // Setting up the Search Interface
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                if (!query.equals("") || query != null){
                    // Search the given words in the HabitEventList
                    Log.d("Search words", query);
                }

                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.clearFocus();

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

    private static String SpinnerTypeSelected(){
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

        for (Habit habit : mHabits) {
            if (!habitTypes.contains(habit.getName())) {
                // Add this Habit Type to habitTypes
                habitTypes.add(habit.getName());
            }
        }

        return habitTypes;
    }

    /**
     * Get the HabitEventList of the login user
     *
     * @return HabitEventList: the HabitEventList of the login user
     */
    public static HabitEventList getmHabitEventList(){
        type = SpinnerTypeSelected();
        if (type == "All"){
            return mHabitEventList;
        }
        else
            for (int i = 0; i < mHabitEventList.size(); i++) {
                if (mHabitEventList.get(i).getTitle() == type) {
                    mTypeHabitEventList.add(mHabitEventList.getHabitEvent(i));
                }
            }
            return mTypeHabitEventList;
        }
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
        login_userProfile.getHabit_event_list().setHabitEvents(updated_HabitEventList);
        mHabitEventList.setHabitEvents(updated_HabitEventList);
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
     * Get the Login user Profile from offline file
     *
     * @return UserProfile: the User Profile of the login User
     */
    private UserProfile getLogin_UserProfile() {
        OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_Username);

        return offlineStorageController.readFromFile();
    }
}