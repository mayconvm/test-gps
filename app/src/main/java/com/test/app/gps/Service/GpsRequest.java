package com.test.app.gps.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.test.app.gps.Database.DatabaseHandler;
import com.test.app.gps.Entity.LocationEntity;
import com.test.app.gps.GPSTracker;
import com.test.app.gps.MainActivity;

/**
 * Created by maycon on 16/12/16.
 */

public class GpsRequest extends IntentService {

    public GpsRequest(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("Service:GpsRequest", "Start");

        //TODO do something useful
        return Service.START_NOT_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getDataString();

        Log.d("Service:GpsRequest", "onHandleIntent");

        GPSTracker gps = new GPSTracker(getApplicationContext());

        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            //stop request position
            gps.stopUsingGPS();

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            // new register
            DatabaseHandler db = new DatabaseHandler(this);

            int newPosition = db.getCount();

            db.create(new LocationEntity(++newPosition, latitude, longitude));
        } else {
            gps.showSettingsAlert();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
