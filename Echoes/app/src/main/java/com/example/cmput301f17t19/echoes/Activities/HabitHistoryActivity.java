/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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

import com.example.cmput301f17t19.echoes.Adapters.HabitEventOverviewAdapter;
import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.Following;
import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.Models.HabitEventList;
import com.example.cmput301f17t19.echoes.Models.HabitStatus;
import com.example.cmput301f17t19.echoes.Models.UserFollowingList;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.example.cmput301f17t19.echoes.Models.UserReceivedRequestsList;
import com.example.cmput301f17t19.echoes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    private com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx;


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
        setContentView(R.layout.activity_habit_history);
        this.setTitle(R.string.habithistory);


        // Get the login username and user Profile
        Intent intent = getIntent();
        if (intent.getStringExtra(LoginActivity.LOGIN_USERNAME) != null) {
            login_Username = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);
        }



        bottomNavigationViewEx = findViewById(R.id.btm3);

        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);


        bottomNavigationViewEx.enableAnimation(false);


        //set the selected activity icon state true
        Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(2);

        menuItem.setChecked(true);


        //set up bottom navigation bar

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                switch (item.getItemId()){

                    case R.id.td:

                        Intent intent_td = new Intent(HabitHistoryActivity.this, ToDoActivity.class);
                        intent_td.putExtra(LoginActivity.LOGIN_USERNAME, login_Username);
                        startActivity(intent_td);
                        finish();

                        break;


                    case R.id.myhabit:
                        // Pass the login User Name to the MyHabits Activity
                        Intent intent = new Intent(HabitHistoryActivity.this, MyHabitsActivity.class);
                        intent.putExtra(LoginActivity.LOGIN_USERNAME, login_Username);
                        startActivity(intent);

                        finish();

                        break;


                    case R.id.history:
                        /*
                        // Pass the login User Name to the HabitHistory Activity
                        Intent intent_his = new Intent(HabitHistoryActivity.this, HabitHistoryActivity.class);
                        intent_his.putExtra(LoginActivity.LOGIN_USERNAME, login_Username);

                        startActivity(intent_his);

                        finish();

                        */
                        break;



                    case R.id.maps:

                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            // Show my habit events and my followings' most recent habit events for each habit on map
                            // My habit events in habit history
                            ArrayList<HabitEvent> shownHabitEvents_Map = login_userProfile.getHabit_event_list().getHabitEvents();
                            // Get My followings
                            ElasticSearchController.GetUserFollowingListTask getUserFollowingListTask = new ElasticSearchController.GetUserFollowingListTask();
                            getUserFollowingListTask.execute(login_Username);
                            try {
                                UserFollowingList userFollowingList = getUserFollowingListTask.get();

                                if (userFollowingList != null) {
                                    ArrayList<Following> myFollowings = userFollowingList.getFollowings();

                                    // My followings most recent habit events for each habit
                                    ArrayList<HabitEvent> myFollowingRecentHabitEvents = FollowingSharingController.createFollowingRecentHabitEvents(myFollowings);

                                    // Add this array list to habit events shown on map
                                    for (HabitEvent habitEvent : myFollowingRecentHabitEvents) {
                                        shownHabitEvents_Map.add(habitEvent);
                                    }
                                }

                                Intent map_intent = new Intent(HabitHistoryActivity.this, MapsActivity.class);

                                map_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_Username);

                                map_intent.putParcelableArrayListExtra(MapsActivity.HABIT_EVENT_SHOW_LOCATION_TAG, shownHabitEvents_Map);

                                startActivity(map_intent);

                                finish();

                            } catch (InterruptedException e) {
                                Toast.makeText(HabitHistoryActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                Toast.makeText(HabitHistoryActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                        }



                        break;



                    case R.id.following:


                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            Intent intent_fol = new Intent(HabitHistoryActivity.this, HabitsFollowingActivity.class);
                            intent_fol.putExtra(LoginActivity.LOGIN_USERNAME, login_Username);

                            startActivity(intent_fol);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                        }


                        break;


                }


                return false;
            }
        });






        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mContext = this;
        spinnerTypes = new ArrayList<String>();



        addEventButton = (Button) findViewById(R.id.habitevents_add_button);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Habit Event Detail
                Intent habitEventDetail_Intent = new Intent(mContext, HabitEventDetailActivity.class);
                // Pass the username to the Habit Event
                habitEventDetail_Intent.putExtra(HabitEventDetailActivity.UserNameHE_TAG, login_Username);
                startActivity(habitEventDetail_Intent);
            }
        });

        habitEventsMapButton = (Button) findViewById(R.id.habitevents_map);

        habitEventsMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map_intent = new Intent(mContext, MapsActivity.class);
                map_intent.putParcelableArrayListExtra(MapsActivity.HABIT_EVENT_SHOW_LOCATION_TAG, mTypeHabitEventList.getHabitEvents());

                startActivity(map_intent);
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




    //check network
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
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

        habitEventOverviewAdapter = new HabitEventOverviewAdapter(this, login_Username);

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

        // Check if the user received following request
        ElasticSearchController.GetUserReceivedRequestsTask getUserReceivedRequestsTask = new ElasticSearchController.GetUserReceivedRequestsTask();
        getUserReceivedRequestsTask.execute(login_Username);

        try {
            UserReceivedRequestsList userReceivedRequestsList = getUserReceivedRequestsTask.get();

            if (userReceivedRequestsList != null) {
                if (userReceivedRequestsList.getReceivedRequests().size() != 0) {
                    inflater.inflate(R.menu.nonempty_message_appbar, menu);
                } else {
                    inflater.inflate(R.menu.mapp_bar, menu);
                }

            } else {
                inflater.inflate(R.menu.mapp_bar, menu);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_UserProfile:
                // Go to User Profile
                // Pass the username of the login user to the user profile
                Intent userProfile_intent = new Intent(this, UserProfileActivity.class);
                userProfile_intent.putExtra(UserProfileActivity.USERPROFILE_TAG, login_Username);
                startActivity(userProfile_intent);

                break;

            case R.id.action_UserMessage:
                // Pass the username of the login user to the user message activity
                Intent userMessage_intent = new Intent(this, UserMessageActivity.class);
                userMessage_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_Username);
                startActivity(userMessage_intent);

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

        // Update the Habit Status for the login user for all habits
        login_userProfile = HabitStatus.updateAllHabitsStatus(login_userProfile);

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