package com.test.app.gps.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by maycon on 16/12/16.
 */

public class StartGpsSchedule extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, LocalWordService.class);
        context.startService(service);

        Log.d("StartGpsSchedule", "Start service");
    }
}