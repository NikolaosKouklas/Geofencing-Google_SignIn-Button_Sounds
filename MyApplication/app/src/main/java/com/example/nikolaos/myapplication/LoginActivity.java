package com.example.nikolaos.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

// https://github.com/sourcey/materiallogindemo/blob/master/app/src/main/java/com/sourcey/materiallogindemo/LoginActivity.java
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";

    private Button loginButton;
    private Button nextButton;
    private GoogleSignInAccount acct;
    private TextView authText;

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set the hardware buttons to control the music
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Load sounds for buttons
        Effects.getInstance().init(this);

        loginButton = (Button) findViewById(R.id.btn_login);
        nextButton = (Button) findViewById(R.id.btn_next);
        nextButton.setVisibility(View.GONE);
        authText = (TextView) findViewById(R.id.auth_text);
        authText.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Effects.getInstance().playSound(Effects.SELECT_BIT);
                v.setEnabled(false);
                signIn();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Effects.getInstance().playSound(Effects.METAL_HIT);
                Intent intent = new Intent(LoginActivity.this, ShowImageActivity.class);
                intent.putExtra("GoogleSignInAccount", acct);
                startActivity(intent);
                finish();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            loginButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
            authText.setText(getString(R.string.auth_success_text));
            authText.setVisibility(View.VISIBLE);

        } else {
            // Signed out, show unauthenticated UI.
            authText.setVisibility(View.VISIBLE);
            loginButton.setEnabled(true);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
