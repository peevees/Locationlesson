package com.mikecoding.locationlesson;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class Mylocationlistener implements LocationListener {
    final String logTag = "MonitorLocation";

    public void onLocationChanged(Location location){

        String provider = location.getProvider();
        double lat = location.getLatitude();//latitude
        double lng = location.getLongitude();//longitud
        float accuracy = location.getAccuracy();//how accurate
        long time = location.getTime();//how old
        Log.d(logTag, "Provider: " + provider + " Lat: " + lat + " Lng: " + lng + " Accuracy: " + accuracy + " Time: " + time );

    }

    public void onStatusChanged(String s, int i, Bundle bundle){

    }
    public void onProviderEnabled(String s){
        //if gps is turned on

    }
    public void onProviderDisabled(String s){
        //if gps is turned off


    }
}
