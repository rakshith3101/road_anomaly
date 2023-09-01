package com.example.anomaly;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.displaytext);
        databaseManager = new DatabaseManager(this);
    }
    protected void onResume() {
        super.onResume();
        databaseManager.open();
        displayData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseManager.close();
    }
    private void displayData() {
        Cursor cursor = databaseManager.getAllData();
        StringBuilder data = new StringBuilder();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String timestamp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIMESTAMP));
                int latitude = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LATITUDE));
                int longitude = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LONGITUDE));
                int speed = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SPEED));
                int anomaly = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ANOMALY));

                data.append("TIMESTAMP: ").append(timestamp).append(", LATITUDE ").append(latitude).append(", SPEED ").append(speed).append(", ANOMALY ").append(anomaly).append("\n");

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        textView.setText(data.toString());
    }
}