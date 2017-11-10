/*
 * Class Name: OfflineStorageController
 *
 * Version: Version 1.0
 *
 * Date: October 31st, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * Offline Storage Controller
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class OfflineStorageController {

    private String FILENAME;

    private Context mContext;

    /**
     * Constructor for OfflineStorageController
     * @param context: Context
     * @param userName: UserName of the login user
     */
    public OfflineStorageController(Context context, String userName) {
        mContext = context;

        // The filename for each userProfile is phone hashing + unique name of the user
        FILENAME = userName + ".sav";
    }

    /**
     * Check if the User has signed up or logged in using this phone before
     * That is The Json file whose name is phone hashing + unique username.json exist
     *
     * @return Boolean: true: the file exists
     *                  false: the file does not exist
     */
    public boolean isFileExist() {
        File file = mContext.getFileStreamPath(FILENAME);
        if(file.exists()){
            Log.d("File", "File exist");
            return true;
        }else{
            Log.d("File", "File does not exist");
            return false;
        }
    }

    /**
     * Read the UserProfile saved in the file
     *
     * @return The UserProfile saved in the file
     */
    public UserProfile readFromFile() {

        try {
            FileInputStream fis = mContext.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type dataType = new TypeToken<UserProfile>() {}.getType();

            UserProfile userProfile = gson.fromJson(in, dataType);

            return userProfile;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;

        } catch (IOException e) {

            throw new RuntimeException();
        }
    }

    /**
     * Save updated UserProfile that logged in using this phone to corresponding file
     *
     * @param userProfile: Updated UserProfile
     */
    public void saveInFile(UserProfile userProfile){
        try {
            FileOutputStream fos = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            Gson gson = new Gson();
            gson.toJson(userProfile, writer);

            writer.flush();

            fos.close();

        } catch (FileNotFoundException e) {

            throw new RuntimeException();

        } catch (IOException e) {

            throw new RuntimeException();
        }
    }
}
