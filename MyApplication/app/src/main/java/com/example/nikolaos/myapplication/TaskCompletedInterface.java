package com.example.nikolaos.myapplication;

/*
 * helper interface to allow the asynchronous tasks to pass results back to the
 * calling activities
 */

import android.graphics.Bitmap;

public interface TaskCompletedInterface {

    void onGetImageCompleted(Bitmap image);
}
