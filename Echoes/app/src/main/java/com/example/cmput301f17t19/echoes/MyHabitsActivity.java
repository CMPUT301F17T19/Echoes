/*
 * Class Name: MyHabitsActivity
 *
 * Version: Version 1.0
 *
 * Date: October 23rd, 2017
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.cmput301f17t19.echoes.LoginActivity.LOGIN_USERNAME;

/**
 * My Habits UI
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class MyHabitsActivity extends AppCompatActivity {

    private RecyclerView habitsRecyclerView;
    private static HabitOverviewAdapter habitOverviewAdapter;

    private static Context mContext;

    // The userName of the Logged-in user
    private static String login_userName;
    // The user profile of the logged-in user
    private static UserProfile login_userProfile;
    // The HabitList of the login user
    private static HabitList mHabitList;

    private Button addHabitButton;

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
        setContentView(R.layout.activity_myhabits);
        this.setTitle(R.string.my_habits);


        com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.btm1);

        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);

        bottomNavigationViewEx.enableAnimation(false);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mContext = this;

        // Get the login username and user Profile
        Intent intent = getIntent();
        if (intent.getStringExtra(LOGIN_USERNAME) == null) {
            // For test
            login_userName = "dummy3";
        } else {
            login_userName = intent.getStringExtra(LOGIN_USERNAME);
        }

        // Set up recycler view for habit event overview in the Habit History
        habitsRecyclerView = (RecyclerView) findViewById(R.id.habits_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(habitsRecyclerView.getContext(),
                layoutManager.getOrientation());
        habitsRecyclerView.addItemDecoration(mDividerItemDecoration);

        habitsRecyclerView.setHasFixedSize(true);

        addHabitButton = (Button) findViewById(R.id.habit_add_button);

        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open an empty Habit Detail Screen
                Intent habitDetail_Intent = new Intent(mContext, HabitDetail.class);
                mContext.startActivity(habitDetail_Intent);
            }
        });
    }

    /**
     * Set user profile and habit list
     */
    @Override
    protected void onStart() {
        super.onStart();

        // the User Profile of the login user
        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_userName);

        // The HabitList of the login user
        mHabitList = login_userProfile.getHabit_list();

        habitOverviewAdapter = new HabitOverviewAdapter(this);

        habitsRecyclerView.setAdapter(habitOverviewAdapter);

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

                mHabitList.remove(position);

                habitOverviewAdapter.notifyItemRemoved(position);

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
        itemTouchHelper.attachToRecyclerView(habitsRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mapp_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_menu:
                // Go back to main menu
                // Pass the username of the login user to the main menu
                Intent mainMenu_intent = new Intent(this, main_menu.class);
                mainMenu_intent.putExtra(LOGIN_USERNAME, login_userName);
                startActivity(mainMenu_intent);

                finish();

                break;

            case R.id.action_UserProfile:
                // Go to User Profile
                // Pass the username of the login user to the user profile
                Intent userProfile_intent = new Intent(this, UserProfileActivity.class);
                userProfile_intent.putExtra(UserProfileActivity.USERPROFILE_TAG, login_userName);
                startActivity(userProfile_intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the HabitList displayed in My Habits
     *
     * @return HabitList: the HabitList of the user
     */
    public static HabitList getMyHabitList(){
        // Get the HabitList of the logged-in user
        return mHabitList;
    }

    /**
     * Get the user profile of the login user
     *
     * @return UserProfile: the user profile of the user
     */
    public static UserProfile getLogin_userProfile() {
        return login_userProfile;
    }

    /**
     * Update the HabitList of the Logged-in User
     *
     * @param updated_HabitList: ArrayList<Habit>, the update HabitList of the logged-in User
     */
    public static void updateHabitList(ArrayList<Habit> updated_HabitList) {
        login_userProfile.getHabit_list().setHabits(updated_HabitList);
        mHabitList.setHabits(updated_HabitList);
    }

    /**
     * Update My Habits Screen and User Profile file and online data of the logged-in user
     */
    public static void updateDataStorage() {
        // Update Screen
        habitOverviewAdapter.notifyDataSetChanged();

        // Update offline file
        OfflineStorageController offlineStorageController = new OfflineStorageController(mContext, login_userProfile.getUserName());
        offlineStorageController.saveInFile(login_userProfile);

        // Update Online data
        ElasticSearchController.syncOnlineWithOffline(login_userProfile);
    }

    /**
     * For Robotium test, get the RecyclerView
     */
    public RecyclerView getHabitsRecyclerView() {
        return habitsRecyclerView;
    }
}
