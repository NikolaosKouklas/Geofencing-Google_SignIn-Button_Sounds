package com.example.nikolaos.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import static com.example.nikolaos.myapplication.Utils.isNetworkAvailable;

public class ShowImageActivity extends AppCompatActivity implements TaskCompletedInterface {

    private ImageView imageView;
    private Button nextButton;
    private Button nextImageButton;
    private GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        // Set the hardware buttons to control the music
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Load sounds for buttons
        Effects.getInstance().init(this);

        acct = getIntent().getExtras().getParcelable("GoogleSignInAccount");

        imageView = (ImageView) findViewById(R.id.image_view);
        nextButton = (Button) findViewById(R.id.btn_next);
        nextImageButton = (Button) findViewById(R.id.btn_next_image);

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Effects.getInstance().playSound(Effects.SMALL_EXPLOSION_BIT);
                Intent intent = new Intent(ShowImageActivity.this, GeoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nextImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Effects.getInstance().playSound(Effects.DRAIN_MAGIC);
                if(isNetworkAvailable(ShowImageActivity.this))
                {
                    HttpGetImageAsyncTask getImageTask = new HttpGetImageAsyncTask(ShowImageActivity.this);
                    getImageTask.execute();
                }
                else {

                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowImageActivity.this, R.style.AppTheme_Dark_Dialog);

                    // Add the buttons
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    // Set other dialog properties
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.dialog_message)
                            .setTitle(R.string.dialog_title);

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });

        if(isNetworkAvailable(this))
        {
            HttpGetImageAsyncTask getImageTask = new HttpGetImageAsyncTask(this);
            getImageTask.execute();
        }
        else {

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowImageActivity.this, R.style.AppTheme_Dark_Dialog);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            // Set other dialog properties
            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title);

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    public void onGetImageCompleted(Bitmap image) {

        imageView.setImageBitmap(image);
    }
}
