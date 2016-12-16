package com.test.app.gps.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.test.app.gps.Entity.InterfaceEntity;
import com.test.app.gps.Entity.LocationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maycon on 15/12/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gpsLocation";

    // Contacts table name
    private static final String TABLE_CONTACTS = "location";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "lat";
    private static final String KEY_PH_NO = "log";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " REAL,"
                + KEY_PH_NO + " REAL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public int create(InterfaceEntity entity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = entity.getValues();
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection

        return entity.getId();
    }

    // Updating single contact
    public int update(InterfaceEntity entity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = entity.getValues();

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(entity.getId()) });
    }

    // Deleting single contact
    public void delete(InterfaceEntity entity) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(entity.getId()) });

        db.close();
    }

    // Getting contacts Count
    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Getting single contact
    public InterfaceEntity get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        // como até agora só tem 1 tabela, será feito assim pra ganhar tempo
        LocationEntity entity = new LocationEntity(Integer.parseInt(cursor.getString(0)), cursor.getDouble(1), cursor.getDouble(2));

        // return
        return entity;
    }

    // Getting All Contacts
    public List<LocationEntity> getAll() {
        List<LocationEntity> contactList = new ArrayList<LocationEntity>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // como até agora só tem 1 tabela, será feito assim pra ganhar tempo
                LocationEntity entity = new LocationEntity(Integer.parseInt(cursor.getString(0)), cursor.getDouble(1), cursor.getDouble(2));

                // Adding contact to list
                contactList.add(entity);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}
