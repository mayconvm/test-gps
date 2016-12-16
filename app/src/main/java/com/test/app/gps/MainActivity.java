package com.test.app.gps;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.test.app.gps.Database.DatabaseHandler;
import com.test.app.gps.Entity.InterfaceEntity;
import com.test.app.gps.Entity.LocationEntity;
import com.test.app.gps.Service.GpsRequest;
import com.test.app.gps.Service.LocalWordService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnShowLocation;
    GPSTracker gps;
    GpsRequest serviceGps;
    LocalWordService s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //linst invisible
        ListView listL = (ListView) findViewById(R.id.listLocation);
        listL.setVisibility(View.INVISIBLE);

        // use this to start and trigger a service
        Intent i = new Intent(this, GpsRequest.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        this.startService(i);

        bindService(i, mConnection, this.BIND_AUTO_CREATE);

        Log.d("Activy:onCreate", "Start service");

    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            LocalWordService.MyBinder b = (LocalWordService.MyBinder) binder;

            //s = b.getService();
            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
                    .show();
        }

        public void onServiceDisconnected(ComponentName className) {
            s = null;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.countIds) {
            DatabaseHandler db = new DatabaseHandler(this);

            Toast.makeText(getApplicationContext(), "Total registers: " + String.valueOf(db.getCount()), Toast.LENGTH_LONG).show();
        }

        if (id == R.id.listText) {
            TextView text = (TextView) findViewById(R.id.textLocation);
            text.setText("");

            DatabaseHandler db = new DatabaseHandler(this);
            List<LocationEntity> listLocation = db.getAll();
            int cnt = db.getCount();
            int i;

            for (i=0; i<cnt; i++) {
                LocationEntity entity = listLocation.get(i);

                text.setText(text.getText() + "\n" + String.valueOf(entity.getValues().toString()));
            }

            Log.d("MENU:listText", "Text setting");
        }



        if (id == R.id.updateList) {
            ListView listL = (ListView) findViewById(R.id.listLocation);
            String[] listEmpty = new String[]{};

            ArrayList<String> arrayList = new ArrayList<String>();

            Log.d("MENU:updateList", "0");
            arrayList.add("OK");

            Log.d("MENU:updateList", "1");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.id.listLocation, arrayList);

            listL.setAdapter(adapter);

            Log.d("MENU:updateList", "2");


            DatabaseHandler db = new DatabaseHandler(this);
            List<LocationEntity> listLocation = db.getAll();
            int cnt = db.getCount();
            int i;

            //for (i=0; i<cnt; i++) {
                //LocationEntity entity = listLocation.get(i);
                arrayList.add("OK2");
            Log.d("MENU:updateList", "3");
                //adapter.add("Ok");
            //}

            adapter.notifyDataSetChanged();

            Log.d("MENU:updateList", "4");

        }

        if (id == R.id.btnShowLocation) {

            gps = new GPSTracker(MainActivity.this);

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


        if (id == R.id.initService) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
