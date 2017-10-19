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
                Intent habitHistory_intent = new Intent(getBaseContext(), HabitHistoryActivity.class);
                startActivity(habitHistory_intent);
            }
        });
    }
}
