/*
 * Copyright (c) Team cmput301f17t19, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at University of Alberta
 */

package com.example.cmput301f17t19.echoes.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;

import com.example.cmput301f17t19.echoes.Models.SystemAdministrator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Process photo
 *
 * @author Shan Lu
 * @version 1.0
 * @since 1.0
 */
public class PhotoOperator {

    /**
     * Convert bitmap to byte array
     *
     * @param bitmap: Bitmap
     * @return byte[]: the byte array of the bitmap
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        // The output stream of the bitmap
        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpStream);

        // Output stream to byte array
        return bmpStream.toByteArray();
    }

    /**
     * Compress the image under the required max storage size
     *
     * Reference: https://stackoverflow.com/questions/28760941/compress-image-file-from-camera-to-certain-size
     *
     * @param bitmap: Bitmap, the bitmap to be compressed
     * @return Bitmap: the compressed bitmap
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        Log.d("Test", "Compress image");

        // The max stream length
        int max_stream_len = SystemAdministrator.getMax_img_storage_bytes();
        int streamLen = BitmapCompat.getAllocationByteCount(bitmap);

        // The output stream to write the compress data
        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();

        // while stream length >= max stream len, and compress quality >= 4, decrease compress quality by 4
        int compress_quality = 104;
        while (streamLen >= max_stream_len && compress_quality >= 4) {
            try {
                bmpStream.flush();//to avoid out of memory error
                bmpStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            compress_quality -= 4;

            bitmap.compress(Bitmap.CompressFormat.JPEG, compress_quality, bmpStream);

            byte[] outputByteArray = bmpStream.toByteArray();
            streamLen = outputByteArray.length;
        }

        // Convert output stream to byte array
        byte[] outputByteArray = bmpStream.toByteArray();

        Log.d("Test", Integer.toString(outputByteArray.length));

        // Return the compressed bitmap image
        return BitmapFactory.decodeByteArray(outputByteArray, 0, outputByteArray.length);
    }

    /**
     * Reduced the selected image bitmap to the required max storage size if size is larger
     *
     * Reference: https://stackoverflow.com/questions/8471226/how-to-resize-image-bitmap-to-a-given-size
     *
     * @param bitmap: Bitmap, the selected bitmap image
     * @param required_width: integer, the required width of the output bitmap
     * @param required_height: integer, the required height of the output bitmap
     *
     * @return Bitmap: the resized bitmap
     */
    public static Bitmap resizeImage(Bitmap bitmap, int required_width, int required_height){

        // The ratio between the bitmap's width and the required width
        float ratio_width = (float) required_width / (float) bitmap.getWidth();
        // The ratio between the bitmap's height and the required height
        float ratio_height = (float) required_height / (float) bitmap.getHeight();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Only scaled down the width or height
        if (ratio_width <= 1) {
            width = Math.round((float) ratio_width * width);
        }

        if (ratio_height <= 1) {
            height = Math.round((float) ratio_height * height);
        }

        // Scaled down bitmap
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        return scaledBitmap;
    }
}
