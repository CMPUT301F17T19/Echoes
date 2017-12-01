/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.animation.Animator;
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
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Adapters.HabitOverviewAdapter;
import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.Following;
import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.Models.HabitList;
import com.example.cmput301f17t19.echoes.Models.UserFollowingList;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.example.cmput301f17t19.echoes.Models.UserReceivedRequestsList;
import com.example.cmput301f17t19.echoes.R;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    private FloatingActionButton addHabitButton;

    private View animateView;


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
        setContentView(R.layout.activity_myhabits);
        this.setTitle(R.string.my_habits);



        // Get the login username and user Profile
        Intent intent = getIntent();
        if (intent.getStringExtra(LoginActivity.LOGIN_USERNAME) != null) {
            login_userName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);
        }




        bottomNavigationViewEx = findViewById(R.id.btm1);

        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);


        bottomNavigationViewEx.enableAnimation(false);


        //set the selected activity icon state true
        Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(1);

        menuItem.setChecked(true);


        //set up bottom navigation bar

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                switch (item.getItemId()){

                    case R.id.td:

                        Intent intent_td = new Intent(MyHabitsActivity.this, ToDoActivity.class);
                        intent_td.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);
                        startActivity(intent_td);
                        finish();

                        break;


                    case R.id.myhabit:
                        /*
                        // Pass the login User Name to the MyHabits Activity
                        Intent intent = new Intent(this, MyHabitsActivity.class);
                        intent.putExtra(LoginActivity.LOGIN_USERNAME, login_UserName);

                        startActivity(intent);

                        finish();
                        */
                        break;


                    case R.id.history:

                        // Pass the login User Name to the HabitHistory Activity
                        Intent intent_his = new Intent(MyHabitsActivity.this, HabitHistoryActivity.class);
                        intent_his.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);

                        startActivity(intent_his);

                        finish();

                        break;



                    case R.id.maps:

                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            // Show my habit events and my followings' most recent habit events for each habit on map
                            // My habit events in habit history
                            ArrayList<HabitEvent> shownHabitEvents_Map = login_userProfile.getHabit_event_list().getHabitEvents();
                            // Get My followings
                            ElasticSearchController.GetUserFollowingListTask getUserFollowingListTask = new ElasticSearchController.GetUserFollowingListTask();
                            getUserFollowingListTask.execute(login_userName);
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

                                Intent map_intent = new Intent(MyHabitsActivity.this, MapsActivity.class);
                                map_intent.putParcelableArrayListExtra(MapsActivity.HABIT_EVENT_SHOW_LOCATION_TAG, shownHabitEvents_Map);

                                map_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);

                                startActivity(map_intent);

                                finish();

                            } catch (InterruptedException e) {
                                Toast.makeText(MyHabitsActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                Toast.makeText(MyHabitsActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                        }



                        break;



                    case R.id.following:


                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            Intent intent_fol = new Intent(MyHabitsActivity.this, HabitsFollowingActivity.class);
                            intent_fol.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);

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

        mContext = this;


        animateView = findViewById(R.id.animate_view_3);



        // Set up recycler view for habit event overview in the Habit History
        habitsRecyclerView = (RecyclerView) findViewById(R.id.habits_recyclerView);
        /*
        Display display = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int height = display.getHeight();

        ViewGroup.LayoutParams params=habitsRecyclerView.getLayoutParams();
        params.height=height - 55;
        habitsRecyclerView.setLayoutParams(params);
        */

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(habitsRecyclerView.getContext(),
                layoutManager.getOrientation());
        habitsRecyclerView.addItemDecoration(mDividerItemDecoration);

        habitsRecyclerView.setHasFixedSize(true);

        addHabitButton = (FloatingActionButton) findViewById(R.id.habit_add_button);

        addHabitButton.attachToRecyclerView(habitsRecyclerView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                addHabitButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrollUp() {
                addHabitButton.setVisibility(View.INVISIBLE);
            }
        }, new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }


        });

        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open an empty Habit Detail Screen
                //Intent habitDetail_Intent = new Intent(mContext, HabitDetailActivity.class);
                //mContext.startActivity(habitDetail_Intent);
                toNextPage();
            }
        });
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






    /**
     * Set user profile and habit list
     */
    @Override
    protected void onStart() {
        super.onStart();

        animateView.setVisibility(View.INVISIBLE);
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

        // Check if the user received following request
        ElasticSearchController.GetUserReceivedRequestsTask getUserReceivedRequestsTask = new ElasticSearchController.GetUserReceivedRequestsTask();
        getUserReceivedRequestsTask.execute(login_userName);

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

            case R.id.action_menu:
                // Go back to main menu
                // Pass the username of the login user to the main menu
                Intent mainMenu_intent = new Intent(this, MainMenuActivity.class);
                mainMenu_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);
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

            case R.id.action_UserMessage:
                // Pass the username of the login user to the user message activity
                Intent userMessage_intent = new Intent(this, UserMessageActivity.class);
                userMessage_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);
                startActivity(userMessage_intent);

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









    private void toNextPage(){

        // Open an empty Habit Detail Screen
        final  Intent habitDetail_Intent = new Intent(MyHabitsActivity.this, HabitDetailActivity.class);


        //int cx = 380;
        //int cy = 830;

        int cx = (addHabitButton.getLeft() + addHabitButton.getRight()) / 2;
        int cy = (addHabitButton.getTop() + addHabitButton.getBottom()) / 2;

        Animator animator = ViewAnimationUtils.createCircularReveal(animateView,cx,cy,0,getResources().getDisplayMetrics().heightPixels * 1.2f);
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animateView.setVisibility(View.VISIBLE);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                animateView.setVisibility(View.VISIBLE);
                //ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity, Username_sign_in_button, "transition");
                startActivity(habitDetail_Intent);


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


}
