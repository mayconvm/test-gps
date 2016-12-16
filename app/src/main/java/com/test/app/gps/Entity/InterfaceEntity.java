package com.test.app.gps.Entity;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by maycon on 15/12/16.
 */

public interface InterfaceEntity {

    ContentValues getValues();

    int getId();

    void setEntityValues(Cursor cursor);
}
