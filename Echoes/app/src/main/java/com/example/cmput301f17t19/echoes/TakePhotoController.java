/*
 * Class Name: TakePhotoController
 *
 * Version: Version 1.0
 *
 * Date: November 8th, 2017
 *
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;

/**
 * Take a Photo using camera and upload
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class TakePhotoController {

    public final static int TAKE_PHOTO_CODE = 22;

    /**
     * Ask User's permission for writing to gallery at run time
     *
     * Reference: https://developer.android.com/training/permissions/requesting.html
     *
     * @param activity: Activity
     */
    public static void askPermission(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Request permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    TAKE_PHOTO_CODE);
        }
    }

    /**
     * Return the intent of taking a photo using camera
     *
     * @return Intent: takePhotoIntent, taking a photo using camera intent
     */
    public static Intent takePhotoIntent(){
        Intent takePhotoIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        return takePhotoIntent;
    }

    /**
     * Load the taken Photo
     *
     * Reference: https://stackoverflow.com/questions/8505647/how-to-capture-the-photo-from-camera-on-android-emulator
     *
     * @param data: Intent
     * @return Bitmap: the taken photo bitmap
     */
    public static Bitmap loadPhoto(Intent data){
        Bundle extras = data.getExtras();

        if(extras.containsKey("data")) {
            Bitmap bitmap = (Bitmap) extras.get("data");

            // Get the selected image's size in bytes
            int img_size = BitmapCompat.getAllocationByteCount(bitmap);

            Log.d("Test", Integer.toString(img_size));

            // Check if the image size exceeds the required storage size
            if (img_size >= SystemAdministrator.getMax_img_storage_bytes()) {
                // Compress the image under the required size
                bitmap = PhotoOperator.compressImage(bitmap);

            }
            // Return the bitmap satisfiying storage size constraint
            return bitmap;

        } else{
            return null;
        }
    }
}
