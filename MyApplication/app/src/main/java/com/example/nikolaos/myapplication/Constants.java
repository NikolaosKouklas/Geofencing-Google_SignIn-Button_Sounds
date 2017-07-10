package com.example.nikolaos.myapplication;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public final class Constants {

    private Constants() {
    }

    public static final String PACKAGE_NAME = "com.example.nikolaos.myapplication";
    public static final String GEOFENCE_REQ_ID = PACKAGE_NAME + ".MY_GEOFENCE_#1";
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 4;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 200;
    public static final int UPDATE_INTERVAL = 10 * 1000; // 10 seconds
    public static final int FASTEST_INTERVAL = 5 * 1000;  // 5 secs
    public static final LatLng STATHMOS_NEOS_KOSMOS = new LatLng(37.957709, 23.728598);

    public static final LatLng MY_POSITION_1 = new LatLng(37.958098, 23.725369);
    public static final LatLng MY_POSITION_2 = new LatLng(37.958056, 23.725755);
    public static final LatLng MY_POSITION_3 = new LatLng(37.958014, 23.726077);
    public static final LatLng MY_POSITION_4 = new LatLng(37.957988, 23.726248);
    public static final LatLng MY_POSITION_5 = new LatLng(37.957997, 23.726270);
    public static final LatLng MY_POSITION_6 = new LatLng(37.957954, 23.726506);
    public static final LatLng MY_POSITION_7 = new LatLng(37.957937, 23.726795);
    public static final LatLng MY_POSITION_8 = new LatLng(37.957844, 23.727493);

}
