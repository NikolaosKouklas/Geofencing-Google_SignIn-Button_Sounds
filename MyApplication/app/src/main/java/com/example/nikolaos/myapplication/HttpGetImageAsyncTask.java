package com.example.nikolaos.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGetImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {

    private TaskCompletedInterface listener;
    private ProgressDialog progressDialog;

    public HttpGetImageAsyncTask(TaskCompletedInterface ac)
    {
        listener = ac;
        progressDialog = new ProgressDialog((Context) ac, R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Φόρτωση...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void onPreExecute() {

        progressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        Bitmap result = null; // return null in case of failure

        // http://stackoverflow.com/questions/11950042/android-how-to-make-a-http-request-to-get-image-link-for-imageview
        // http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android?noredirect=1&lq=1
        URL url = null;
        try {
            url = new URL("https://unsplash.it/200/300/?random");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection  = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = null;
        try {
            is = connection.getInputStream();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        result = BitmapFactory.decodeStream(is);

        return result;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

        listener.onGetImageCompleted(result);
        progressDialog.dismiss();
    }
}
