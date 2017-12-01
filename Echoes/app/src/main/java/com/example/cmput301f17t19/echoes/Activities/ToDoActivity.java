/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f17t19.echoes.Adapters.ToDoListAdapter;
import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.FollowingSharingController;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.Models.Following;
import com.example.cmput301f17t19.echoes.Models.Habit;
import com.example.cmput301f17t19.echoes.Models.HabitEvent;
import com.example.cmput301f17t19.echoes.Models.HabitList;
import com.example.cmput301f17t19.echoes.Models.HabitStatus;
import com.example.cmput301f17t19.echoes.Models.UserFollowingList;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.example.cmput301f17t19.echoes.Models.UserReceivedRequestsList;
import com.example.cmput301f17t19.echoes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

/**
 * To Do Activity
 *
 * @author Hayden Bauder
 * @version 1.0
 * @since 1.0
 */
public class ToDoActivity extends AppCompatActivity {

    private static ArrayList<String> nameArray;
    private static ArrayList<String> reasonArray;

    ListView listView; // will display list of habits

    // The userName of the Logged-in user
    private static String login_userName;
    // The user profile of the logged-in user
    private static UserProfile login_userProfile;
    // The HabitList of the login user
    private static HabitList myHabitList;

    private static ToDoListAdapter adapter;

    private static Context mContext;

    private com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx;

    private TextView mon_day;
    private TextView tue_day;
    private TextView wed_day;
    private TextView thu_day;
    private TextView fri_day;
    private TextView sat_day;
    private TextView sun_day;

    private TextView year;

    private TextView month;

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
        setContentView(R.layout.activity_todo);

        mContext = this;

        Intent intent = getIntent();

        login_userName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);

        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_userName);

        myHabitList = login_userProfile.getHabit_list();

        nameArray = new ArrayList<String>();
        reasonArray = new ArrayList<String>();

        populateArrays(); // populate the listView with the data

        adapter = new ToDoListAdapter(this,
                nameArray,
                reasonArray,
                login_userName);

        listView = (ListView) findViewById(R.id.listViewToDo);
        listView.setAdapter(adapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);




        bottomNavigationViewEx = findViewById(R.id.btm2);

        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);


        bottomNavigationViewEx.enableAnimation(false);


        //set the selected activity icon state true
        Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(0);

        menuItem.setChecked(true);

        //set up bottom navigation bar

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                switch (item.getItemId()){

                    case R.id.td:
                        /*
                        Intent intent_td = new Intent(ToDoActivity.this, ToDoActivity.class);
                        intent_td.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);
                        startActivity(intent_td);
                        finish();
                        */
                        break;


                    case R.id.myhabit:

                        // Pass the login User Name to the MyHabits Activity
                        Intent intent = new Intent(ToDoActivity.this, MyHabitsActivity.class);
                        intent.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);
                        startActivity(intent);

                        finish();

                        break;


                    case R.id.history:

                        // Pass the login User Name to the HabitHistory Activity
                        Intent intent_his = new Intent(ToDoActivity.this, HabitHistoryActivity.class);
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

                                Intent map_intent = new Intent(ToDoActivity.this, MapsActivity.class);
                                map_intent.putParcelableArrayListExtra(MapsActivity.HABIT_EVENT_SHOW_LOCATION_TAG, shownHabitEvents_Map);
                                map_intent.putExtra(LoginActivity.LOGIN_USERNAME, login_userName);
                                startActivity(map_intent);

                            } catch (InterruptedException e) {
                                Toast.makeText(ToDoActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                Toast.makeText(ToDoActivity.this, "You can only see habit events of your followings and yours on Map online.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
                        }



                        break;



                    case R.id.following:


                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            Intent intent_fol = new Intent(ToDoActivity.this, HabitsFollowingActivity.class);
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


        mon_day = findViewById(R.id.mon_day);
        tue_day = findViewById(R.id.tue_day);
        wed_day = findViewById(R.id.wed_day);
        thu_day = findViewById(R.id.thurs_day);
        fri_day = findViewById(R.id.fri_day);
        sat_day = findViewById(R.id.sat_day);
        sun_day = findViewById(R.id.sun_day);

        year = findViewById(R.id.year);

        month = findViewById(R.id.month);

        Calendar c = Calendar.getInstance(); // get current day
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int monthOfYear = c.get(Calendar.MONTH);
        int year_number = c.get(Calendar.YEAR);

        int daysInMonth;

        int daysInMonth2;

        Calendar mycal1 = new GregorianCalendar(year_number, monthOfYear, dayOfMonth);

        daysInMonth = mycal1.getActualMaximum(Calendar.DAY_OF_MONTH);

        //if current month is Jan
        if (monthOfYear != 0) {
            Calendar mycal2 = new GregorianCalendar(year_number, monthOfYear - 1, 1);
            daysInMonth2 = mycal1.getActualMaximum(Calendar.DAY_OF_MONTH);

        } else{
            Calendar mycal2 = new GregorianCalendar(year_number-1, 11, 1);
            daysInMonth2 = mycal1.getActualMaximum(Calendar.DAY_OF_MONTH);

        }


        year.setText(String.valueOf(year_number));

        if (monthOfYear == 0){
            //Jan
            month.setText("January");
        }

        if (monthOfYear == 1){
            //Feb
            month.setText("February");
        }

        if (monthOfYear == 2){
            //March
            month.setText("March");
        }


        if (monthOfYear == 3){
            //April
            month.setText("April");
        }

        if (monthOfYear == 4){
            //May
            month.setText("May");
        }

        if (monthOfYear == 5){
            //June
            month.setText("June");
        }

        if (monthOfYear == 6){
            //July
            month.setText("July");
        }

        if (monthOfYear == 7){
            //August
            month.setText("August");
        }

        if (monthOfYear == 8){
            //Sept
            month.setText("September");
        }

        if (monthOfYear == 9){
            //Oct
            month.setText("October");
        }

        if (monthOfYear == 10){
            //Nov
            month.setText("November");
        }

        if (monthOfYear == 11){
            //Dec
            month.setText("December");
        }



        if  (dayOfWeek == Calendar.MONDAY){
            mon_day.setText(String.valueOf(dayOfMonth));
            mon_day.setBackground(mContext.getDrawable(R.drawable.date_circle));

            tue_day.setText(String.valueOf((dayOfMonth+1)%daysInMonth));
            tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            wed_day.setText(String.valueOf((dayOfMonth+2)%daysInMonth));
            wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            thu_day.setText(String.valueOf((dayOfMonth+3)%daysInMonth));
            thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            fri_day.setText(String.valueOf((dayOfMonth+4)%daysInMonth));
            fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sat_day.setText(String.valueOf((dayOfMonth+5)%daysInMonth));
            sat_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sun_day.setText(String.valueOf((dayOfMonth+6)%daysInMonth));
            sun_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

        }



        if  (dayOfWeek == Calendar.TUESDAY){

            if (dayOfMonth-1 >= 1){
                mon_day.setText(String.valueOf(dayOfMonth-1));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }else{
                mon_day.setText(String.valueOf(daysInMonth2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }

            tue_day.setText(String.valueOf(dayOfMonth));
            tue_day.setBackground(mContext.getDrawable(R.drawable.date_circle));

            wed_day.setText(String.valueOf((dayOfMonth+1)%daysInMonth));
            wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            thu_day.setText(String.valueOf((dayOfMonth+2)%daysInMonth));
            thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            fri_day.setText(String.valueOf((dayOfMonth+3)%daysInMonth));
            fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sat_day.setText(String.valueOf((dayOfMonth+4)%daysInMonth));
            sat_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sun_day.setText(String.valueOf((dayOfMonth+5)%daysInMonth));
            sun_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

        }


        if  (dayOfWeek == Calendar.WEDNESDAY){

            if (dayOfMonth-2 >= 1) {
                mon_day.setText(String.valueOf(dayOfMonth - 2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == 0){
                mon_day.setText(String.valueOf(daysInMonth2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == -1){
                mon_day.setText(String.valueOf(daysInMonth2-1));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }

            if (dayOfMonth-1 >=1) {
                tue_day.setText(String.valueOf(dayOfMonth - 1));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            } else{
                tue_day.setText(String.valueOf(daysInMonth2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }

            wed_day.setText(String.valueOf(dayOfMonth));
            wed_day.setBackground(mContext.getDrawable(R.drawable.date_circle));

            thu_day.setText(String.valueOf((dayOfMonth+1)%daysInMonth));
            thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            fri_day.setText(String.valueOf((dayOfMonth+2)%daysInMonth));
            fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sat_day.setText(String.valueOf((dayOfMonth+3)%daysInMonth));
            sat_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sun_day.setText(String.valueOf((dayOfMonth+4)%daysInMonth));
            sun_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

        }



        if  (dayOfWeek == Calendar.THURSDAY){

            if(dayOfMonth-3 >=1){
                mon_day.setText(String.valueOf(dayOfMonth - 3));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == 0){
                mon_day.setText(String.valueOf(daysInMonth2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -1){
                mon_day.setText(String.valueOf(daysInMonth2-1));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -2){
                mon_day.setText(String.valueOf(daysInMonth2-2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }

            if (dayOfMonth-2 >=1) {
                tue_day.setText(String.valueOf(dayOfMonth - 2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == 0){
                tue_day.setText(String.valueOf(daysInMonth2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == -1){
                tue_day.setText(String.valueOf(daysInMonth2-1));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }

            if (dayOfMonth-1 >= 1) {
                wed_day.setText(String.valueOf(dayOfMonth - 1));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }else{
                wed_day.setText(String.valueOf(daysInMonth2));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }

            thu_day.setText(String.valueOf(dayOfMonth));
            thu_day.setBackground(mContext.getDrawable(R.drawable.date_circle));

            fri_day.setText(String.valueOf((dayOfMonth+1)%daysInMonth));
            fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sat_day.setText(String.valueOf((dayOfMonth+2)%daysInMonth));
            sat_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sun_day.setText(String.valueOf((dayOfMonth+3)%daysInMonth));
            sun_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

        }



        if  (dayOfWeek == Calendar.FRIDAY){

            if (dayOfMonth-4 >=1){
                mon_day.setText(String.valueOf(dayOfMonth-4));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 ==0 ){
                mon_day.setText(String.valueOf(daysInMonth2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -1){
                mon_day.setText(String.valueOf(daysInMonth2-1));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -2) {
                mon_day.setText(String.valueOf(daysInMonth2 - 2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -3) {
                mon_day.setText(String.valueOf(daysInMonth2 - 3));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-3 >= 1) {
                tue_day.setText(String.valueOf(dayOfMonth - 3));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == 0){
                tue_day.setText(String.valueOf(daysInMonth2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -1){
                tue_day.setText(String.valueOf(daysInMonth2-1));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -2){
                tue_day.setText(String.valueOf(daysInMonth2-2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-2 >= 1) {
                wed_day.setText(String.valueOf(dayOfMonth - 2));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == 0){
                wed_day.setText(String.valueOf(daysInMonth2));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == -1){
                wed_day.setText(String.valueOf(daysInMonth2-1));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-1 >=1 ) {
                thu_day.setText(String.valueOf(dayOfMonth - 1));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }else{
                thu_day.setText(String.valueOf(daysInMonth2));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            fri_day.setText(String.valueOf(dayOfMonth));
            fri_day.setBackground(mContext.getDrawable(R.drawable.date_circle));

            sat_day.setText(String.valueOf((dayOfMonth+1)%daysInMonth));
            sat_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

            sun_day.setText(String.valueOf((dayOfMonth+2)%daysInMonth));
            sun_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

        }



        if  (dayOfWeek == Calendar.SATURDAY){

            if (dayOfMonth-5 >= 1){
                mon_day.setText(String.valueOf(dayOfMonth-5));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == 0){
                mon_day.setText(String.valueOf(daysInMonth2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -1){
                mon_day.setText(String.valueOf(daysInMonth2-1));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -2){
                mon_day.setText(String.valueOf(daysInMonth2-2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -3){
                mon_day.setText(String.valueOf(daysInMonth2-3));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -4){
                mon_day.setText(String.valueOf(daysInMonth2-4));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-4 >= 1) {
                tue_day.setText(String.valueOf(dayOfMonth - 4));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == 0){
                tue_day.setText(String.valueOf(daysInMonth2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -1){
                tue_day.setText(String.valueOf(daysInMonth2-1));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -2){
                tue_day.setText(String.valueOf(daysInMonth2-2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -3){
                tue_day.setText(String.valueOf(daysInMonth2-3));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-3 >= 1) {
                wed_day.setText(String.valueOf(dayOfMonth - 3));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == 0){
                wed_day.setText(String.valueOf(daysInMonth2));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -1){
                wed_day.setText(String.valueOf(daysInMonth2-1));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -2){
                wed_day.setText(String.valueOf(daysInMonth2-2));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-2 >= 1) {
                thu_day.setText(String.valueOf(dayOfMonth - 2));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == 0){
                thu_day.setText(String.valueOf(daysInMonth2));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == -1){
                thu_day.setText(String.valueOf(daysInMonth2-1));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-1 >=1 ) {
                fri_day.setText(String.valueOf(dayOfMonth - 1));
                fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }else{
                fri_day.setText(String.valueOf(daysInMonth2));
                fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            sat_day.setText(String.valueOf(dayOfMonth));
            sat_day.setBackground(mContext.getDrawable(R.drawable.date_circle));


            sun_day.setText(String.valueOf((dayOfMonth+1)%daysInMonth));
            sun_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));

        }




        if  (dayOfWeek == Calendar.SUNDAY){

            if (dayOfMonth-6 >=1) {
                mon_day.setText(String.valueOf(dayOfMonth - 6));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-6 == 0){
                mon_day.setText(String.valueOf(daysInMonth2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-6 == -1){
                mon_day.setText(String.valueOf(daysInMonth2-1));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-6 == -2){
                mon_day.setText(String.valueOf(daysInMonth2-2));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-6 == -3){
                mon_day.setText(String.valueOf(daysInMonth2-3));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-6 == -4){
                mon_day.setText(String.valueOf(daysInMonth2-4));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-6 == -5){
                mon_day.setText(String.valueOf(daysInMonth2-5));
                mon_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-5 >= 1) {
                tue_day.setText(String.valueOf(dayOfMonth - 5));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == 0){
                tue_day.setText(String.valueOf(daysInMonth2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -1){
                tue_day.setText(String.valueOf(daysInMonth2-1));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -2){
                tue_day.setText(String.valueOf(daysInMonth2-2));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -3){
                tue_day.setText(String.valueOf(daysInMonth2-3));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-5 == -4){
                tue_day.setText(String.valueOf(daysInMonth2-4));
                tue_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-4 >=1) {
                wed_day.setText(String.valueOf(dayOfMonth - 4));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == 0){
                wed_day.setText(String.valueOf(daysInMonth2));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -1){
                wed_day.setText(String.valueOf(daysInMonth2-1));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -2){
                wed_day.setText(String.valueOf(daysInMonth2-2));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-4 == -3){
                wed_day.setText(String.valueOf(daysInMonth2-3));
                wed_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }



            if (dayOfMonth-3 >=1) {
                thu_day.setText(String.valueOf(dayOfMonth - 3));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == 0){
                thu_day.setText(String.valueOf(daysInMonth2));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -1){
                thu_day.setText(String.valueOf(daysInMonth2-1));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-3 == -2){
                thu_day.setText(String.valueOf(daysInMonth2-2));
                thu_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-2 >=1 ) {
                fri_day.setText(String.valueOf(dayOfMonth - 2));
                fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == 0){
                fri_day.setText(String.valueOf(daysInMonth2));
                fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else if (dayOfMonth-2 == -1){
                fri_day.setText(String.valueOf(daysInMonth2-1));
                fri_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }


            if (dayOfMonth-1 >= 1) {
                sat_day.setText(String.valueOf(dayOfMonth - 1));
                sat_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }
            else {
                sat_day.setText(String.valueOf(daysInMonth2));
                sat_day.setBackground(mContext.getDrawable(R.drawable.white_circle_button));
            }

            sun_day.setText(String.valueOf(dayOfMonth));
            sun_day.setBackground(mContext.getDrawable(R.drawable.date_circle));

        }



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

                finish();

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

//    // gets the user's preofile so we can look at the HabitList
//    private UserProfile getLogin_UserProfile() {
//        OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_userName);
//
//        return offlineStorageController.readFromFile();
//    }

    // Check all of the users habits, only add ones that are scheduled for today.
    public void populateArrays() {
        for (int index=0; index < myHabitList.getHabits().size(); index++) {

            Habit habit = myHabitList.getHabits().get(index);

            Calendar c = Calendar.getInstance(); // get current day
            int today = c.get(Calendar.DAY_OF_WEEK);

            if (habit.getPlan().getSchedule().get(today-1)){
                Boolean isDone = false;

                // Today
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String Today_str = simpleDateFormat.format(Calendar.getInstance().getTime());

                // Check if this habit has done today
                ArrayList<HabitEvent> habitEvents = login_userProfile.getHabit_event_list().getHabitEvents();
                for (HabitEvent habitEvent : habitEvents) {

                    if (habitEvent.getTitle().equals(habit.getName()) &&
                            simpleDateFormat.format(habitEvent.getStartDate()).equals(Today_str)) {
                        // No need to add this habit type into TO DO LIST
                        isDone = true;
                    }
                }

                if (!isDone) {
                    nameArray.add(habit.getName());
                    reasonArray.add(habit.getReason());
                }
            }
        }
    }

    /**
     * Get the name array
     *
     * @return ArrayList<String>: name array
     */
    public static ArrayList<String> getNameArray(){
        return nameArray;
    }

    /**
     * Get the reason array
     *
     * @return ArrayList<String>: reason array
     */
    public static ArrayList<String> getReasonArray() {
        return reasonArray;
    }

    /**
     * Get the login user profile
     *
     * @return UserProfile: login user profile
     */
    public static UserProfile getLogin_userProfile() {
        return login_userProfile;
    }

    /**
     * Update TO DO list Screen and User Profile file and online data of the logged-in user
     */
    public static void updateDataStorage() {
        adapter.notifyDataSetChanged();

        // Update the Habit Status for the login user for all habits
        login_userProfile = HabitStatus.updateAllHabitsStatus(login_userProfile);

        // Update offline file
        OfflineStorageController offlineStorageController = new OfflineStorageController(mContext, login_userProfile.getUserName());
        offlineStorageController.saveInFile(login_userProfile);

        // Update Online data
        ElasticSearchController.syncOnlineWithOffline(login_userProfile);
    }
}
