package com.example.revern.task4a.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.revern.task4a.MainActivity;

public class OffReceiver extends BroadcastReceiver {
    public OffReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_lock_power_off)
                .setContentTitle("Off Notification")
                .setContentText("Test off")
                .setAutoCancel(true)
                .setContentIntent(pi).build();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, notification);
    }
}
