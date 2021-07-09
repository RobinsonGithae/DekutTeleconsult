package com.example.dekutteleconsult.Service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StudentLocationTrackService extends Service implements LocationListener, GpsStatus.Listener {
    private final Context mContext;
    boolean checkGPS=false;
    boolean checkNetwork=false;
    boolean canGetLocation=false;

    Location loc;
    double Latitude;
    double Longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES=10;
    private static final long MIN_TIME_BW_UPDATES=1000*60;
    protected LocationManager locationManager;



//    public static final String LOG_TAG = LocationService.class.getSimpleName();
//
//    private final LocationServiceBinder binder = new LocationServiceBinder();
//
//    boolean isLocationManagerUpdatingLocation;
//
//
//
//    ArrayList<Location> locationList;
//
//    ArrayList<Location> oldLocationList;
//    ArrayList<Location> noAccuracyLocationList;
//    ArrayList<Location> inaccurateLocationList;
//    ArrayList<Location> kalmanNGLocationList;
//
//
//    boolean isLogging;
//
//    float currentSpeed = 0.0f; // meters/second
//
//    KalmanLatLong kalmanFilter;
//    long runStartTimeInMillis;
//
//    ArrayList<Integer> batteryLevelArray;
//    ArrayList<Float> batteryLevelScaledArray;
//    int batteryScale;
//    int gpsCount;
//
//
//
//





    public StudentLocationTrackService(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }


//
//    @Override
//    public void onCreate() {
//        isLocationManagerUpdatingLocation = false;
//        locationList = new ArrayList<>();
//        noAccuracyLocationList = new ArrayList<>();
//        oldLocationList = new ArrayList<>();
//        inaccurateLocationList = new ArrayList<>();
//        kalmanNGLocationList = new ArrayList<>();
//        kalmanFilter = new KalmanLatLong(3);
//
//        isLogging = false;
//
//        batteryLevelArray = new ArrayList<>();
//        batteryLevelScaledArray = new ArrayList<>();
//        registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//    }
//





    private Location getLocation() {


        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // get GPS status
            checkGPS = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // get network provider status
            checkNetwork = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!checkGPS && !checkNetwork) {
                Toast.makeText(mContext, "No Service Provider is available", Toast.LENGTH_SHORT).show();
            } else {
                this.canGetLocation = true;

                // if GPS Enabled get lat/long using GPS Services
                if (checkGPS) {

                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null) {
                            Latitude = loc.getLatitude();
                            Longitude = loc.getLongitude();
                        }
                    }


                }






                    /*if (checkNetwork) {


                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            loc = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        }

                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    }*/








            }
        } catch (Exception e) {
            e.printStackTrace();
        }

return loc;
    }

    public Context getmContext() {
        return mContext;
    }

    public boolean isCheckGPS() {
        return checkGPS;
    }

    public void setCheckGPS(boolean checkGPS) {
        this.checkGPS = checkGPS;
    }

    public boolean isCheckNetwork() {
        return checkNetwork;
    }

    public void setCheckNetwork(boolean checkNetwork) {
        this.checkNetwork = checkNetwork;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public void setCanGetLocation(boolean canGetLocation) {
        this.canGetLocation = canGetLocation;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public double getLatitude() {
        if (loc != null) {
            Latitude = loc.getLatitude();
        }
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {

        if (loc != null) {
            Longitude = loc.getLongitude();
        }

        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public boolean canGetLocation() {

        //recheck this method
        return this.canGetLocation;
    }



    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);


        alertDialog.setTitle("GPS is not Enabled!");

        alertDialog.setMessage("Do you want to turn on GPS?");


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }


    public void stopListener() {
        if (locationManager != null) {

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(StudentLocationTrackService.this);
        }
    }




    @Override
    public int onStartCommand(Intent i, int flags, int startId) {
        super.onStartCommand(i, flags, startId);
        return Service.START_STICKY;

    //non-generated method. comment not related to functionality
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
    public void onStatusChanged(String provider, int status, Bundle extras) {


//        if (provider.equals(LocationManager.GPS_PROVIDER)) {
//            if (status == LocationProvider.OUT_OF_SERVICE) {
//                notifyLocationProviderStatusUpdated(false);
//            } else {
//                notifyLocationProviderStatusUpdated(true);
//            }
//        }
//
//

    }






//    //This is where we detect the app is being killed, thus stop service.
//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        Log.d(LOG_TAG, "onTaskRemoved ");
//        this.stopUpdatingLocation();
//
//        stopSelf();
//    }
//



//    public class LocationServiceBinder extends Binder {
//        public LocationService getService() {
//            return LocationService.this;
//        }
//    }
//


    @Override
    public void onProviderEnabled(String provider) {

        if (provider.equals(LocationManager.GPS_PROVIDER)) {
//            notifyLocationProviderStatusUpdated(true);
        }

    }

    @Override
    public void onProviderDisabled(String provider) {

        if (provider.equals(LocationManager.GPS_PROVIDER)) {
//            notifyLocationProviderStatusUpdated(false);
        }

    }


    @Override
    public void onGpsStatusChanged(int event) {

    }



    private void notifyLocationProviderStatusUpdated(boolean isLocationProviderAvailable) {
        //Broadcast location provider status change here
    }



//    public void startLogging(){
//        isLogging = true;
//    }
//
//    public void stopLogging(){
//        if (locationList.size() > 1 && batteryLevelArray.size() > 1){
//            long currentTimeInMillis = (long)(SystemClock.elapsedRealtimeNanos() / 1000000);
//            long elapsedTimeInSeconds = (currentTimeInMillis - runStartTimeInMillis) / 1000;
//            float totalDistanceInMeters = 0;
//            for(int i = 0; i < locationList.size() - 1; i++){
//                totalDistanceInMeters +=  locationList.get(i).distanceTo(locationList.get(i + 1));
//            }
//            int batteryLevelStart = batteryLevelArray.get(0).intValue();
//            int batteryLevelEnd = batteryLevelArray.get(batteryLevelArray.size() - 1).intValue();
//
//            float batteryLevelScaledStart = batteryLevelScaledArray.get(0).floatValue();
//            float batteryLevelScaledEnd = batteryLevelScaledArray.get(batteryLevelScaledArray.size() - 1).floatValue();
//
//            saveLog(elapsedTimeInSeconds, totalDistanceInMeters, gpsCount, batteryLevelStart, batteryLevelEnd, batteryLevelScaledStart, batteryLevelScaledEnd);
//        }
//        isLogging = false;
//    }
//
//
//
//    public void startUpdatingLocation() {
//        if(this.isLocationManagerUpdatingLocation == false){
//            isLocationManagerUpdatingLocation = true;
//            runStartTimeInMillis = (long)(SystemClock.elapsedRealtimeNanos() / 1000000);
//
//
//            locationList.clear();
//
//            oldLocationList.clear();
//            noAccuracyLocationList.clear();
//            inaccurateLocationList.clear();
//            kalmanNGLocationList.clear();
//
//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            //Exception thrown when GPS or Network provider were not available on the user's device.
//            try {
//                Criteria criteria = new Criteria();
//                criteria.setAccuracy(Criteria.ACCURACY_FINE); //setAccuracyは内部では、https://stackoverflow.com/a/17874592/1709287の用にHorizontalAccuracyの設定に変換されている。
//                criteria.setPowerRequirement(Criteria.POWER_HIGH);
//                criteria.setAltitudeRequired(false);
//                criteria.setSpeedRequired(true);
//                criteria.setCostAllowed(true);
//                criteria.setBearingRequired(false);
//
//                //API level 9 and up
//                criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
//                criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
//                //criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
//                //criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
//
//                Integer gpsFreqInMillis = 5000;
//                Integer gpsFreqInDistance = 5;  // in meters
//
//                locationManager.addGpsStatusListener(this);
//
//                locationManager.requestLocationUpdates(gpsFreqInMillis, gpsFreqInDistance, criteria, this, null);
//
//                /* Battery Consumption Measurement */
//                gpsCount = 0;
//                batteryLevelArray.clear();
//                batteryLevelScaledArray.clear();
//
//            } catch (IllegalArgumentException e) {
//                Log.e(LOG_TAG, e.getLocalizedMessage());
//            } catch (SecurityException e) {
//                Log.e(LOG_TAG, e.getLocalizedMessage());
//            } catch (RuntimeException e) {
//                Log.e(LOG_TAG, e.getLocalizedMessage());
//            }
//        }
//    }
//
//
//    public void stopUpdatingLocation(){
//        if(this.isLocationManagerUpdatingLocation == true){
//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//            locationManager.removeUpdates(this);
//            isLocationManagerUpdatingLocation = false;
//        }
//    }





//
//    @Override
//    public void onLocationChanged(final Location newLocation) {
//        Log.d(TAG, "(" + newLocation.getLatitude() + "," + newLocation.getLongitude() + ")");
//
//        gpsCount++;
//
//        if(isLogging){
//            //locationList.add(newLocation);
//            filterAndAddLocation(newLocation);
//        }
//
//        Intent intent = new Intent("LocationUpdated");
//        intent.putExtra("location", newLocation);
//
//        LocalBroadcastManager.getInstance(this.getApplication()).sendBroadcast(intent);
//    }
//
//    @SuppressLint("NewApi")
//    private long getLocationAge(Location newLocation){
//        long locationAge;
//        if(android.os.Build.VERSION.SDK_INT >= 17) {
//            long currentTimeInMilli = (long)(SystemClock.elapsedRealtimeNanos() / 1000000);
//            long locationTimeInMilli = (long)(newLocation.getElapsedRealtimeNanos() / 1000000);
//            locationAge = currentTimeInMilli - locationTimeInMilli;
//        }else{
//            locationAge = System.currentTimeMillis() - newLocation.getTime();
//        }
//        return locationAge;
//    }
//
//
//    private boolean filterAndAddLocation(Location location){
//
//        long age = getLocationAge(location);
//
//        if(age > 5 * 1000){ //more than 5 seconds
//            Log.d(TAG, "Location is old");
//            oldLocationList.add(location);
//            return false;
//        }
//
//        if(location.getAccuracy() <= 0){
//            Log.d(TAG, "Latitidue and longitude values are invalid.");
//            noAccuracyLocationList.add(location);
//            return false;
//        }
//
//        //setAccuracy(newLocation.getAccuracy());
//        float horizontalAccuracy = location.getAccuracy();
//        if(horizontalAccuracy > 10){ //10meter filter
//            Log.d(TAG, "Accuracy is too low.");
//            inaccurateLocationList.add(location);
//            return false;
//        }
//
//
//        /* Kalman Filter */
//        float Qvalue;
//
//        long locationTimeInMillis = (long)(location.getElapsedRealtimeNanos() / 1000000);
//        long elapsedTimeInMillis = locationTimeInMillis - runStartTimeInMillis;
//
//        if(currentSpeed == 0.0f){
//            Qvalue = 3.0f; //3 meters per second
//        }else{
//            Qvalue = currentSpeed; // meters per second
//        }
//
//        kalmanFilter.Process(location.getLatitude(), location.getLongitude(), location.getAccuracy(), elapsedTimeInMillis, Qvalue);
//        double predictedLat = kalmanFilter.get_lat();
//        double predictedLng = kalmanFilter.get_lng();
//
//        Location predictedLocation = new Location("");//provider name is unecessary
//        predictedLocation.setLatitude(predictedLat);//your coords of course
//        predictedLocation.setLongitude(predictedLng);
//        float predictedDeltaInMeters =  predictedLocation.distanceTo(location);
//
//        if(predictedDeltaInMeters > 60){
//            Log.d(TAG, "Kalman Filter detects mal GPS, we should probably remove this from track");
//            kalmanFilter.consecutiveRejectCount += 1;
//
//            if(kalmanFilter.consecutiveRejectCount > 3){
//                kalmanFilter = new KalmanLatLong(3); //reset Kalman Filter if it rejects more than 3 times in raw.
//            }
//
//            kalmanNGLocationList.add(location);
//            return false;
//        }else{
//            kalmanFilter.consecutiveRejectCount = 0;
//        }
//
//        /* Notifiy predicted location to UI */
//        Intent intent = new Intent("PredictLocation");
//        intent.putExtra("location", predictedLocation);
//        LocalBroadcastManager.getInstance(this.getApplication()).sendBroadcast(intent);
//
//        Log.d(TAG, "Location quality is good enough.");
//        currentSpeed = location.getSpeed();
//        locationList.add(location);
//
//
//        return true;
//    }
//
//
//
//    /* Battery Consumption */
//    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver(){
//        @Override
//        public void onReceive(Context ctxt, Intent intent) {
//            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//
//            float batteryLevelScaled = batteryLevel / (float)scale;
//
//
//
//            batteryLevelArray.add(Integer.valueOf(batteryLevel));
//            batteryLevelScaledArray.add(Float.valueOf(batteryLevelScaled));
//            batteryScale = scale;
//        }
//    };
//
//    /* Data Logging */
//    public synchronized void saveLog(long timeInSeconds, double distanceInMeters, int gpsCount, int batteryLevelStart, int batteryLevelEnd, float batteryLevelScaledStart, float batteryLevelScaledEnd) {
//        SimpleDateFormat fileNameDateTimeFormat = new SimpleDateFormat("yyyy_MMdd_HHmm");
//        String filePath = this.getExternalFilesDir(null).getAbsolutePath() + "/"
//                + fileNameDateTimeFormat.format(new Date()) + "_battery" + ".csv";
//
//        Log.d(TAG, "saving to " + filePath);
//
//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter(filePath, false);
//            fileWriter.append("Time,Distance,GPSCount,BatteryLevelStart,BatteryLevelEnd,BatteryLevelStart(/" + batteryScale + ")," + "BatteryLevelEnd(/" + batteryScale + ")" + "\n");
//            String record = "" + timeInSeconds + ',' + distanceInMeters + ',' + gpsCount + ',' + batteryLevelStart + ',' + batteryLevelEnd + ',' + batteryLevelScaledStart + ',' + batteryLevelScaledEnd + '\n';
//            fileWriter.append(record);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (fileWriter != null) {
//                try {
//                    fileWriter.close();
//                } catch (IOException ioe) {
//                    ioe.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//
//





















}
