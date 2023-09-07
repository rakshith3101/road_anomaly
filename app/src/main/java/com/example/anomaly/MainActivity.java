package com.example.anomaly;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private DatabaseManager databaseManager;
    private notificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.displaytext);
        notificationHelper = new notificationHelper(this);
        //sendAutomaticNotification();

        //Database
        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        long rowIdToUpdate = 3;
        int updatedRows = databaseManager.updateData(rowIdToUpdate, "NewTimestamp", 41.7428, -75.0460, 100.0, 1);
        if (updatedRows > 0) {
            // Data updated successfully
        } else {
            // Error updating data or row with the given `_ID` doesn't exist
        }
        Cursor cursor = databaseManager.getAllData();

        if (cursor.moveToFirst()) {
            do {
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TIMESTAMP));
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.LONGITUDE));
                double speed = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.SPEED));
                int anomaly = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ANOMALY));

                // Use the fetched data as needed
            } while (cursor.moveToNext());
        }

        cursor.close();


        //notifications
        notificationHelper = new notificationHelper(this);



        //ml-model
        if(anomaly_model()) {
            sendAutomaticNotification();
        }
    }



    private void sendAutomaticNotification() {
        notificationHelper.sendNotification("Automatic Notification", "This notification was sent automatically.");
    }



    @Override
    protected void onPause() {
        super.onPause();
        databaseManager.close();
    }
    //Machine learning model
    private boolean anomaly_model(){
        return true;
    }
}
