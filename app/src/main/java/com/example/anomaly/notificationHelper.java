package com.example.anomaly;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Locale;

public class notificationHelper {
    private final Context mContext;
    private final NotificationManager mNotificationManager;
    private final Vibrator mVibrator;
    private TextToSpeech tts;

    public notificationHelper(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    private void initializeTextToSpeech() {
        tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.ENGLISH);
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    public void sendNotification(String title, String message) {
        // Create a notification channel for Android O and above
        createNotificationChannel();

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "notification_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        mNotificationManager.notify(1, builder.build());

        // Vibrate the device
        vibrateDevice();

        speakNotification(message);

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
    private void speakNotification(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "notification");
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}

