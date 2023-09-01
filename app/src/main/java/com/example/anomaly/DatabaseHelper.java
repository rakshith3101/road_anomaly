package com.example.anomaly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //database creation
    static final String DATABASE_NAME="road_anomalyDB";
    static final int DATABASE_VERSION=1;

    //Defining the database
    static final String TABLE_NAME="anomaly";
    static final String TIMESTAMP="timestamp";
    static final String LATITUDE="latitude";
    static final String LONGITUDE="longitude";
    static final String SPEED="speed";
    static final String ANOMALY="anomaly";


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_query= "CREATE TABLE "+TABLE_NAME+ " (" +
                TIMESTAMP+ " VARCHAR, " +
                LATITUDE + " DOUBLE, " +
                LONGITUDE + "DOUBLE," +
                SPEED + "FLOAT," +
                ANOMALY + "TEXT)";
        db.execSQL(table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
