/*
 * Class Name: ElasticSearchController
 *
 * Version: Version 1.0
 *
 * Date: October 29th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Elastic Search Controller
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class ElasticSearchController {
    private static JestDroidClient client;

    private static final String SEARCH_INDEX = "cmput301f17t19";
    private static final String SEARCH_TYPE = "userprofile";

    /**
     *  Add new user profile to online database
     */
    public static class AddNewUserProfileTask extends AsyncTask<UserProfile, Void, Void> {

        @Override
        protected Void doInBackground(UserProfile... userProfiles) {
            verifySettings();

            UserProfile userProfile = userProfiles[0];

            // Set the unique ID for the document of this userProfile to be the uniqueUsername of this user
            String doc_ID = userProfile.getUserName();

            // Add this new user to online database
            Index index = new Index.Builder(userProfile)
                    .index(SEARCH_INDEX)
                    .type(SEARCH_TYPE)
                    .id(doc_ID)
                    .build();

            try {
                DocumentResult execute = client.execute(index);

                if(execute.isSucceeded()){
                    Log.d("addUser", "Add userProfile Success");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the tweets");
            }

            return null;
        }
    }

    /**
     * Method from lonelyTwitter class
     * Reference: https://github.com/AmyShanLu/lonelyTwitter/blob/elasticsearch/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
