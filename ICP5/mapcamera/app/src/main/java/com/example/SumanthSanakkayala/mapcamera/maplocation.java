package com.example.SumanthSanakkayala.mapcamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class maplocation extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final String TAG = "maplocation";
    private static final int Error_Dialog_Request = 9001;
    private FusedLocationProviderClient mfusedlocationproviderclient;
    private boolean mLocationGranted = false;
    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapdetails);
        getLocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    getDeviceLocation();
        mapFragment.getMapAsync(maplocation.this);

    }

public void home(View v){
    Intent redirect=new Intent(maplocation.this,MainPage.class);
    startActivity(redirect);
}


    public void getDeviceLocation() {
        mfusedlocationproviderclient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationGranted) {
                final Task location = mfusedlocationproviderclient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d("loc","recieved location");
                            Location location1 = (Location) task.getResult();
                            moveCamera(new LatLng(location1.getLatitude(), location1.getLongitude()), 15f);
                        } else {
                            Toast.makeText(maplocation.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException s) {
            Log.e("error","some error"+ s.getMessage());
            Log.d("err",s.getMessage());
        }
    }

    private void moveCamera(LatLng latlg, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlg, zoom));
        mMap.addMarker(new MarkerOptions().position(latlg)
                .title("My Location "+latlg.latitude+" "+latlg.longitude));
    }

    public void getLocationPermission() {
        Log.d("location", "getting location permissions");
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
              //  MapInit();
                mLocationGranted=true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("result", "response recieved");

        mLocationGranted = false;
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationGranted = false;
                            return;
                        }
                    }
                }
                mLocationGranted = true;
                //MapInit();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("map", "map is ready");
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        if (mLocationGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }


    }


}