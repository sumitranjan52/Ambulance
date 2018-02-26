package com.ambulance;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int PERMISSION_REQUEST_CODE = 1997;
    private SharedPreferencesHandler preferencesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* SharedPreferenceHandler class object goes here below */

        preferencesHandler = new SharedPreferencesHandler(this);

        /* Location permission is requested here and appropriate action is taken according to the response of the user */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                /* If user denied for 1st time then this if block is called otherwise else block is called */

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    /* Proper information to user is given about why actually app need these permission. */

                    new AlertDialog.Builder(this)
                            .setTitle(R.string.permission_request_title)
                            .setMessage(R.string.permission_request_message)
                            .setPositiveButton("Grant!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    /* Permission dialog is prompted to the user */

                                    preferencesHandler.updateSharedPreferences("location_permission");
                                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

                                }
                            })
                            .setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    /* App exit */

                                    System.exit(0);

                                }
                            })
                            .create()
                            .show();

                } else if (!preferencesHandler.checkSharedPreferences("location_permission")) {

                    /* Permission dialog is prompted to the user */

                    preferencesHandler.updateSharedPreferences("location_permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

                } else {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.permission_request_title)
                            .setMessage(R.string.grant_permission_from_settings)
                            .setPositiveButton("Go to setting", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    /* App setting page is opened for grating permissions */

                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", SplashActivity.this.getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    /* App exit */

                                    System.exit(0);
                                }
                            })
                            .create()
                            .show();
                }
            }else{
                startActivity(new Intent(this,ImageSliderActivity.class));
            }
        }else{
            startActivity(new Intent(this,ImageSliderActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    /* if permission is granted */
                    startActivity(new Intent(this,ImageSliderActivity.class));

                } else {

                    /* if permission isn't granted app closes */

                    System.exit(0);
                }
            } else {

                    /* if permission isn't granted app closes */

                System.exit(0);
            }
        }
    }
}
