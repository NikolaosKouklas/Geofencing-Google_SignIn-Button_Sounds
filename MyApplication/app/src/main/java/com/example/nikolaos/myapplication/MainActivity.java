package com.example.nikolaos.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private final Handler handler;
    private ImageView imageView;
    private GoogleApiClient mGoogleApiClient;

    public MainActivity() {
        this.handler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load sounds for buttons, but is not necessary here, because there is no button in this activity
        Effects.getInstance().init(this);

        imageView = (ImageView) findViewById(R.id.imgview1);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();

        // after 2 seconds start the next activity WelcomeActivity
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // disappear the imageView "Ανάπτυξη Εφαρμογών σε Android"
                imageView.setVisibility(View.INVISIBLE);
                // disconnecting my app from google account every time this app starts
                revokeAccess();
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    // Code from Here
    // http://www.androidhive.info/2014/02/android-login-with-google-plus-account-1/
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
