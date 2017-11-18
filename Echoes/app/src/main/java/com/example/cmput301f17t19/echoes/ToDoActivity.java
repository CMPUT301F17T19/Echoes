package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.cmput301f17t19.echoes.LoginActivity.LOGIN_USERNAME;

/**
 * To Do Activity
 *
 * @author Hayden Bauder
 * @version 1.0
 * @since 1.0
 */
public class ToDoActivity extends AppCompatActivity {

    //TODO: add getToDo method to HabitList class?
    public ArrayList<String> nameArray;
    public ArrayList<String> reasonArray;
    public ArrayList<Habit> todoArray; // refactor two above arrays to just one

    ListView listView; // will display list of habits

    // The userName of the Logged-in user
    private static String login_userName;
    // The user profile of the logged-in user
    private static UserProfile login_userProfile;
    // The HabitList of the login user
    private static HabitList myHabitList;

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
        setContentView(R.layout.activity_todo);

        Intent intent = getIntent();

        login_userName = intent.getStringExtra(LOGIN_USERNAME);

        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_userName);

        myHabitList = login_userProfile.getHabit_list();

        nameArray = new ArrayList<String>();
        reasonArray = new ArrayList<String>();

        populateArrays(myHabitList); // populate the listView with the data

        ToDoListAdapter adapter = new ToDoListAdapter(this,
                nameArray,
                reasonArray);

        listView = (ListView) findViewById(R.id.listViewToDo);
        listView.setAdapter(adapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

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

                finish();

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
    //TODO: implement system for removing habits from this list once done
    public void populateArrays(HabitList habitList) {
        for (int index=0; index < myHabitList.getHabits().size(); index++) {

            Habit habit = myHabitList.getHabits().get(index);
            Calendar c = Calendar.getInstance(); // get current day
            int today = c.get(Calendar.DAY_OF_WEEK);

            if (habit.getPlan().getSchedule().get(today-1)){
                if (!habit.doneToday) {
                    nameArray.add(habit.getName());
                    reasonArray.add(habit.getReason());
                }
            }
        }
    }
}
