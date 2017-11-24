package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ToDoListAdapter
 *
 * @author Hayden Bauder
 * @version 1.0
 * @since 1.0
 * Reference: https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc
 */


public class ToDoListAdapter extends ArrayAdapter {
    public static final String TODO_Pos_TAG = "TODO_Pos_TAG";

    private final Activity context;

    // Each entry in the list view has 2 text fields, Name and Reason
    private final ArrayList<String> nameArray;
    private final ArrayList<String> reasonArray;

    private String mUserName;

    // Contruct the adaptor
    public ToDoListAdapter(Activity context,
                           ArrayList<String> nameArray,
                           ArrayList<String> reasonArray,
                           String userName){

        super(context,R.layout.todo_list_layout, nameArray);

        this.context=context;
        this.nameArray = nameArray;
        this.reasonArray = reasonArray;
        this.mUserName = userName;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.todo_list_layout,
                null,
                true);

        // get TextViews from todo_list_layout xml
        TextView nameText   = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView reasonText = (TextView) rowView.findViewById(R.id.reasonTextView);
        // set the values
        nameText.setText(nameArray.get(position));
        reasonText.setText(reasonArray.get(position));

        // Click the View to open the Habit Event Detail Activity that allow the user to add corresponding Habit Event
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Habit Event Detail Activity
                Intent addHabitEvent = new Intent(context, HabitEventDetailActivity.class);
                // Pass the username
                addHabitEvent.putExtra(HabitEventDetailActivity.UserNameHE_TAG, mUserName);
                // Pass the position of the selected Habit Type
                addHabitEvent.putExtra(TODO_Pos_TAG, position);

                context.startActivity(addHabitEvent);
            }
        });

        return rowView;

    };
}
