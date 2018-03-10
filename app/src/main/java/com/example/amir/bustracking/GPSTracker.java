package com.example.amir.bustracking;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

public class GPSTracker extends Service implements LocationListener {
   private final Context mContext;
   Boolean isGPSEnable=false;
    Boolean isNetworkEnable=false;
    Boolean canGetLocation=false;
    Location location;
    double latitude;
    double longtitude;
    private static final long MIN_UPDATE_DIS=10;
    private static final long MIN_TIME_UPDATE_BW_DIS=1000*60;
    protected LocationManager locationManager;
    public GPSTracker (Context context)
    {
        this.mContext=context;
        getLocation();
    }
    public Location getLocation(){
        try {
            locationManager=(LocationManager)mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnable=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnable&&!isNetworkEnable)
            {

            }else {
                this.canGetLocation=true;
                if (isNetworkEnable){
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED) {
                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_UPDATE_BW_DIS,MIN_UPDATE_DIS,this);
                        if (locationManager!=null) {
                            location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location!=null) {
                                latitude=location.getLatitude();
                                longtitude=location.getLongitude();
                            }
                        }
                }
                if (isGPSEnable) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_UPDATE_BW_DIS,MIN_UPDATE_DIS,this);
                        if (locationManager!=null) {
                            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location!=null) {
                                latitude=location.getLatitude();
                                longtitude=location.getLongitude();
                            }
                        }
                    }

                }
            }
        }catch (Exception e){ e.printStackTrace();}
        return location;
    }
    public void StopGPS()
    {
        if (locationManager!=null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.removeUpdates(GPSTracker.this);

        }
    }
    public double getLatitude()
    {
        if (location!=null)
            latitude=location.getLatitude();
        return latitude;
    }
    public double getLongtitude()
    {
        if (location!=null)
            longtitude=location.getLongitude();
        return longtitude;
    }
    public boolean canGetLocation(){
        return this.canGetLocation;
    }
    public void ShowSettingalert(){
        final AlertDialog.Builder alBuilder=new AlertDialog.Builder(mContext);
        alBuilder.setTitle("GPS Setting");
        alBuilder.setMessage("GPS is disable....GO Setting to enable GPS ");
        alBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);

            }
        });
        alBuilder.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alBuilder.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
