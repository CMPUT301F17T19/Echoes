package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.support.annotation.NonNull;
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

    private final Activity context;

    // Each entry in the list view has 2 text fields, Name and Reason
    private final ArrayList<String> nameArray;
    private final ArrayList<String> reasonArray;

    // Contruct the adaptor
    public ToDoListAdapter(Activity context,
                           ArrayList<String> nameArray,
                           ArrayList<String> reasonArray){

        super(context,R.layout.todo_list_layout, nameArray);

        this.context=context;
        this.nameArray = nameArray;
        this.reasonArray = reasonArray;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
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

        return rowView;

    };
}
