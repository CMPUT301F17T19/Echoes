/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ToDoActivity extends AppCompatActivity {

    String[] nameArray = {
            "Wake up before 8:00",
            "Eat breakfast",
            "Get to class"};

    String[] reasonArray = {
            "Get ready for school",
            "Most important meal",
            "Class starts at 10:00"};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToDoListAdapter adapter = new ToDoListAdapter(this,
                nameArray,
                reasonArray);

        listView = (ListView) findViewById(R.id.listViewToDo);
        listView.setAdapter(adapter);
    }
}
