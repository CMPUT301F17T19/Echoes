package com.example.cmput301f17t19.echoes;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Hayden Bauder on 09/11/2017.
 */

// following https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc

public class ToDoListAdapter extends ArrayAdapter {

    private final Activity context;

    private final String[] nameArray;
    private final String[] reasonArray;

    public ToDoListAdapter(Activity context,
                           String[] nameArray,
                           String[] reasonArray){

        super(context,R.layout.todo_list_layout, nameArray);

        this.context=context;
        this.nameArray = nameArray;
        this.reasonArray = reasonArray;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.todo_list_layout,
                null,
                true);

        // get TextViews from todo_list_layout xml
        TextView nameText   = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView reasonText = (TextView) rowView.findViewById(R.id.reasonTextView);
        nameText.setText(nameArray[position]);
        reasonText.setText(reasonArray[position]);

        return rowView;

    };
}
