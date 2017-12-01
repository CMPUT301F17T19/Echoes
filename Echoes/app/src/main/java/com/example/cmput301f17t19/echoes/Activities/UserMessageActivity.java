/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cmput301f17t19.echoes.Controllers.ElasticSearchController;
import com.example.cmput301f17t19.echoes.Controllers.OfflineStorageController;
import com.example.cmput301f17t19.echoes.R;
import com.example.cmput301f17t19.echoes.Models.ReceivedRequest;
import com.example.cmput301f17t19.echoes.Adapters.RequestOverviewAdapter;
import com.example.cmput301f17t19.echoes.Models.UserProfile;
import com.example.cmput301f17t19.echoes.Models.UserReceivedRequestsList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class UserMessageActivity extends AppCompatActivity {

    private String login_userName;
    private static UserProfile login_userProfile;

    private static ArrayList<ReceivedRequest> receivedRequests;

    private RecyclerView receivedRequest_RecyclerView;
    private static RequestOverviewAdapter requestOverviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        this.setTitle(R.string.userMessage);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // Get the login username and user Profile
        Intent intent = getIntent();
        if (intent.getStringExtra(LoginActivity.LOGIN_USERNAME) != null) {
            login_userName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);
        }

        // Set up recycler view
        receivedRequest_RecyclerView = (RecyclerView) findViewById(R.id.receivedRequestRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        receivedRequest_RecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(receivedRequest_RecyclerView.getContext(),
                layoutManager.getOrientation());
        receivedRequest_RecyclerView.addItemDecoration(mDividerItemDecoration);

        receivedRequest_RecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        login_userProfile = OfflineStorageController.getLogin_UserProfile(this, login_userName);

        // Get the received request list of the login user
        ElasticSearchController.GetUserReceivedRequestsTask getUserReceivedRequestsTask = new ElasticSearchController.GetUserReceivedRequestsTask();
        getUserReceivedRequestsTask.execute(login_userName);

        // Update login_userProfile
        try {
            UserReceivedRequestsList login_UserReceivedRequestsList = getUserReceivedRequestsTask.get();

            if (login_UserReceivedRequestsList != null) {
                ((TextView) findViewById(R.id.userMessage_networkError)).setVisibility(View.GONE);

                login_userProfile.setReceivedRequest(login_UserReceivedRequestsList.getReceivedRequests());

                // Update offline and sync with online
                OfflineStorageController offlineStorageController = new OfflineStorageController(this, login_userName);
                offlineStorageController.saveInFile(login_userProfile);
                ElasticSearchController.syncOnlineWithOffline(login_userProfile);

                receivedRequests = login_UserReceivedRequestsList.getReceivedRequests();

                requestOverviewAdapter = new RequestOverviewAdapter(this);
                receivedRequest_RecyclerView.setAdapter(requestOverviewAdapter);
            } else {
                // Show the network error
                ((LinearLayout) findViewById(R.id.receivedRequest_LinearLayout)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.userMessage_networkError)).setVisibility(View.VISIBLE);
            }

        } catch (InterruptedException e) {
            // Show the network error
            ((LinearLayout) findViewById(R.id.receivedRequest_LinearLayout)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.userMessage_networkError)).setVisibility(View.VISIBLE);

            e.printStackTrace();
        } catch (ExecutionException e) {
            // Show the network error
            ((LinearLayout) findViewById(R.id.receivedRequest_LinearLayout)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.userMessage_networkError)).setVisibility(View.VISIBLE);

            e.printStackTrace();
        }


    }

    /**
     * Get the array list of received request displayed in the user message
     */
    public static ArrayList<ReceivedRequest> getReceivedRequests() {
        return receivedRequests;
    }

    /**
     * Get the userProfile of the login user
     */
    public static UserProfile getLogin_userProfile() {
        return login_userProfile;
    }

    /**
     * Get the recycler view adapter
     */
    public static RequestOverviewAdapter getRequestOverviewAdapter() {
        return requestOverviewAdapter;
    }
}
