package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import static com.example.cmput301f17t19.echoes.LoginActivity.LOGIN_USERNAME;

public class ToDoActivity extends AppCompatActivity {

    // Dummy arrays for now
    //TODO: add getToDo method to HabitList class
    String[] nameArray = {
            "Wake up before 8:00",
            "Eat breakfast",
            "Get to class"};

    String[] reasonArray = {
            "Get ready for school",
            "Most important meal",
            "Class starts at 10:00"};

    ListView listView;

    // The userName of the Logged-in user
    private static String login_userName;
    // The user profile of the logged-in user
    private static UserProfile login_userProfile;
    // The HabitList of the login user
    private static HabitList mHabitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Intent intent = getIntent();

        ToDoListAdapter adapter = new ToDoListAdapter(this,
                nameArray,
                reasonArray);

        listView = (ListView) findViewById(R.id.listViewToDo);
        listView.setAdapter(adapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        login_userName = intent.getStringExtra(LOGIN_USERNAME);
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
}
