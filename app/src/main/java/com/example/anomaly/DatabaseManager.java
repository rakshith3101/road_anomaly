package com.example.anomaly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;
    public DatabaseManager(Context ctx) {
        context = ctx;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper=new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    // CRUD operations
    public void insertData(String timestamp, int latitude,int longitude,int speed, String anomaly) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TIMESTAMP, timestamp);
        values.put(DatabaseHelper.LATITUDE, latitude);
        values.put(DatabaseHelper.LONGITUDE, longitude);
        values.put(DatabaseHelper.SPEED, speed);
        values.put(DatabaseHelper.ANOMALY, anomaly);
        database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }
    public Cursor getAllData() {
        String [] columns= new String[] {DatabaseHelper.TIMESTAMP,DatabaseHelper.LATITUDE,DatabaseHelper.LONGITUDE,DatabaseHelper.SPEED,DatabaseHelper.ANOMALY};
        Cursor cursor=database.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    //should change this method for real life application
    public int updateData(String timestamp, int latitude, int longitude,int speed,String anomaly) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TIMESTAMP, timestamp);
        values.put(DatabaseHelper.LATITUDE, latitude);
        values.put(DatabaseHelper.LONGITUDE, longitude);
        values.put(DatabaseHelper.SPEED, speed);
        values.put(DatabaseHelper.ANOMALY, anomaly);
        return database.update(DatabaseHelper.TABLE_NAME, values,
                DatabaseHelper.TIMESTAMP + " = " + timestamp, null);//change it to primary key attribute
    }
    public void deleteData(String timestamp) {
        database.delete(DatabaseHelper.TABLE_NAME,
                DatabaseHelper.TIMESTAMP + " = " + timestamp, null);
    }
}
