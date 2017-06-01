package com.mikecoding.locationlesson;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LocationListener networkListener;
    LocationListener gpsListener;

    private static final int REQUEST_LOCATION = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onStartListening(MenuItem item){

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        }else {

            try {
                //                                 Typecast▽
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                networkListener = new Mylocationlistener();
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkListener);
            }catch(Exception e){
                e.printStackTrace();
            }
        }


    }

    public void onStopListening(MenuItem item){
        doStopListening();
    }
    public void onRecentLocation(MenuItem item){
        Location networkLocation;
        Location gpsLocation;

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //translates a gps coordinate into a readable adress
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(networkLocation.getLatitude(), networkLocation.getLongitude(), 5);// gps longitud and latitude and amount of results
        }catch(IOException e){
            e.printStackTrace();
        }

        if(addressList != null) {
            Log.d("RecentLocation", "adresser: " + addressList.get(0).getCountryCode());
        }else{
            Log.d("RecentLocation", "adresser är tom");
        }

    }
    public void onSingleLocation(MenuItem item){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        networkListener = new Mylocationlistener();
        //have to handle if permission denied
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, networkListener,null);

        gpsListener = new Mylocationlistener();
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, gpsListener,null);
    }

    void doStopListening(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //if(networkListener != null){
            locationManager.removeUpdates(networkListener);
            networkListener = null;
        //}
    }
}
