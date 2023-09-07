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

        //textView = findViewById(R.id.displaytext);
        //databaseManager = new DatabaseManager(this);
        notificationHelper = new notificationHelper(this);
    }

    private void startAnomalyDetectionService() {
        Intent serviceIntent = new Intent(this, push_notification.class);
        startService(serviceIntent);

    }

    protected void onResume() {
        super.onResume();
        databaseManager.open();
        //displayData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseManager.close();
    }
}