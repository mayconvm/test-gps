package com.test.app.gps.Entity;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by maycon on 15/12/16.
 */

public class AbstractionEntity implements InterfaceEntity {

    public ContentValues getValues() {
        return new ContentValues();
    }

    public int getId() {
        return 0;
    }

    public void setEntityValues(Cursor cursor) {

    }

}
