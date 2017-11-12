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
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Habit History UI
 *
 * @author Shan Lu
 * @author Mitchell Ballou
 *
 * @version 1.0
 * @since 1.0
 */
public class HabitHistoryActivity extends AppCompatActivity {


    private RecyclerView habitEventsRecyclerView;
    public static HabitEventOverviewAdapter habitEventOverviewAdapter;

    private static Context mContext;

    // The userName of the Logged-in user
    private static String login_userName;
    // The user profile of the logged-in user
    private static UserProfile login_userProfile;
    // The HabitList of the login user
    private static HabitEventList habitEvents_HabitHistory;

    private Button addHabitEventButton;
    private Button habitEventsMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


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

        // Set up recycler view for habit event overview in the Habit History
        habitEventsRecyclerView = (RecyclerView) findViewById(R.id.habitevents_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitEventsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(habitEventsRecyclerView.getContext(),
                layoutManager.getOrientation());
        habitEventsRecyclerView.addItemDecoration(mDividerItemDecoration);

        habitEventsRecyclerView.setHasFixedSize(true);

        habitEventsMapButton = (Button) findViewById(R.id.habitevents_map);

        habitEventsMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Will be enabled in Project Part 5", Toast.LENGTH_SHORT).show();
            }
        });

        addHabitEventButton = (Button) findViewById(R.id.habitevents_add_button);

        addHabitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open an empty Habit Event Detail Screen
                Intent newHabitEvent_Intent = new Intent(mContext, HabitEventDetailActivity.class);
                mContext.startActivity(newHabitEvent_Intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        // the User Profile of the login user
        login_userProfile = getLogin_UserProfile();

        // The HabitEventList of the login user
        habitEvents_HabitHistory = login_userProfile.getHabit_event_list();

        habitEventOverviewAdapter = new HabitEventOverviewAdapter(this);

        habitEventsRecyclerView.setAdapter(habitEventOverviewAdapter);

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

                habitEvents_HabitHistory.remove(position);

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
                        c.drawBitmap(bitmap, dX + (float)itemView.getWidth(),
                                (float) itemView.getTop() + (float) itemView.getHeight()/2 - (float) bitmap.getHeight()/2,
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
                if (query != "" || query != null){
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
                finish();

                // Go back to main menu
                Intent mainMenu_intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(mainMenu_intent);

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    /**
     * Get the HabitEventList displayed in Habit History
     */
    public static HabitEventList getMyHabitEventList(){
        // Get the HabitList of the logged-in user
        return habitEvents_HabitHistory;
    }

    /**
     * Get the user profile of the login user
     */
    public static UserProfile getLogin_userProfile() {
        return login_userProfile;
    }

    /**
     * Update the HabitList of the Logged-in User
     *
     * @param updated_HabitEventList: ArrayList<HabitEvent>, the update HabitEventList of the logged-in User
     */
    public static void updateHabitEventList(ArrayList<HabitEvent> updated_HabitEventList) {
        login_userProfile.getHabit_event_list().setHabitEvents(updated_HabitEventList);
        habitEvents_HabitHistory.setHabitEvents(updated_HabitEventList);
    }

    /**
     * Update My Habits Screen and User Profile file and online data of the logged-in user
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
        OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_userName);

        return offlineStorageController.readFromFile();
    }
}