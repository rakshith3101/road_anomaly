package com.example.anomaly;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleService;

import java.util.Locale;

public class push_notification extends LifecycleService {
    private TextToSpeech tts;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeTextToSpeech();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    //initialiing text to speech
    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // TTS is initialized successfully
                    tts.setLanguage(Locale.ENGLISH);
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    private void sendPushNotification(String title, String message) {
        // Create a notification channel for Android O and above
        createNotificationChannel();

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "anomaly_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("app_package", getPackageName());
        intent.putExtra("app_uid", getApplicationInfo().uid);
        startActivity(intent);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());


    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "anomaly_channel",
                    "Anomaly Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void speakAnomalyDetails(String details) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(details, TextToSpeech.QUEUE_FLUSH, null, "anomaly");
        } else {
            tts.speak(details, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void vibrateDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        // Implement your anomaly detection logic here
        boolean anomalyDetected = detectAnomaly();

        if (anomalyDetected) {
            // Anomaly detected, send notifications
            sendPushNotification("Anomaly Detected", "Details of the anomaly...");
            speakAnomalyDetails("Anomaly detected. Please be cautious. Details: ...");
            vibrateDevice();
        }

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        // Release resources
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    private boolean detectAnomaly() {
        // Implement your anomaly detection logic here
        // Return true if an anomaly is detected, otherwise false
        return true;
    }
}

