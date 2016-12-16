package com.test.app.gps.Entity;

import android.content.ContentValues;

/**
 * Created by maycon on 15/12/16.
 */

public class LocationEntity extends AbstractionEntity {
    int _id;
    double _log, _lat;
    String _table_name = "location";

    public LocationEntity(){}
    public LocationEntity(int id, double lat, double log){
        this._id = id;
        this._lat = lat;
        this._log = log;
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public double getLat() {
        return this._lat;
    }

    public void setLat(double lat) {
        this._lat = lat;
    }

    public double getLog() {
        return this._log;
    }

    public void setLog(double log) {
        this._log = log;
    }

    public ContentValues getValues() {
        ContentValues values =  new ContentValues();

        values.put("id", this._id);
        values.put("lat", this._lat);
        values.put("log", this._log);

        return values;
    };

}
