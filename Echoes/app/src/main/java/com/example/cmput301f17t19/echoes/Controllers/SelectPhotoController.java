/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;

import com.example.cmput301f17t19.echoes.Models.SystemAdministrator;
import com.example.cmput301f17t19.echoes.Utils.PhotoOperator;

/**
 * Select a Photo from Gallery Controller
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class SelectPhotoController {

    public final static int SELECT_PHOTO_GALLERY_CODE = 11;

    /**
     * Ask User's permission for reading from gallery at run time
     *
     * Reference: https://developer.android.com/training/permissions/requesting.html
     *
     * @param activity: Activity
     */
    public static void askPermission(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Request permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_PHOTO_GALLERY_CODE);
        }
    }

    /**
     * Return the intent of selecting a photo from gallery
     *
     * @return Intent: selectPhotoIntent, selecting a photo from gallery intent
     */
    public static Intent selectPhotoFromGallery(){
        Intent selectPhotoIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return selectPhotoIntent;
    }

    /**
     * Load the selected Photo from gallery and show it on the Image View
     *
     * Reference: https://stackoverflow.com/questions/13023788/how-to-load-an-image-in-image-view-from-gallery
     *
     * @param context: Context
     * @param data: Intent
     *
     * @return Bitmap: the uploaded bitmap image
     */
    public static Bitmap loadPhoto(Context context, Intent data){
        Uri selectedImage = data.getData();

        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

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
    }
}
