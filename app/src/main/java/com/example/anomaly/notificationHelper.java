package com.example.anomaly;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

public class notificationHelper {
    private final Context mContext;
    private final NotificationManager mNotificationManager;
    private final Vibrator mVibrator;

    public notificationHelper(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void sendNotification(String title, String message) {
        // Create a notification channel for Android O and above
        createNotificationChannel();

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "notification_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("this")
                .setContentText("this")
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        mNotificationManager.notify(1, builder.build());

        // Vibrate the device
        vibrateDevice();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "notification_channel",
                    "Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel description");
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    private void vibrateDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mVibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            mVibrator.vibrate(500);
        }
    }
}

