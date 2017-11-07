package com.example.cmput301f17t19.echoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mHabitHistoryButton = (Button) findViewById(R.id.habithistory_button);
        mHabitHistoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open HabitHistory UI
                Intent habitHistory_intent = new Intent(getApplicationContext(), HabitHistoryActivity.class);
                startActivity(habitHistory_intent);
            }
        });

        Button mMyHabitsButton = (Button) findViewById(R.id.myhabits_button);
        mMyHabitsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open My Habits UI
                Intent myHabits_intent = new Intent(getApplicationContext(), MyHabitsActivity.class);
                startActivity(myHabits_intent);
            }
        });

        Button mProfileTestButton = (Button) findViewById(R.id.profileTest);
        mProfileTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open My Habits UI
                Intent profile_intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                // Dummy1 login
                profile_intent.putExtra(UserProfileActivity.USERPROFILE_TAG, "dummy1");
                startActivity(profile_intent);
            }
        });


        Button mLoginTestButton = (Button) findViewById(R.id.loginTest);
        mLoginTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open login ui
                Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(login_intent);
            }
        });



    }
}
