package com.example.revern.task4a.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;

import com.example.revern.task4a.MainActivity;

public class BatteryReceiver extends BroadcastReceiver {
    public BatteryReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (batteryLevel != -1 && batteryScale != -1) {
            int battery = batteryLevel /  batteryScale;
            if (battery < 30) {
                Intent i = new Intent(context, MainActivity.class);
                //didnt find how to only close it((
                Intent i1 = new Intent(context, BatteryReceiver.class);
                PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
                PendingIntent pi1 = PendingIntent.getActivity(context, 0, i1, PendingIntent.FLAG_CANCEL_CURRENT);
                Notification notification = new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_lock_idle_low_battery)
                        .setContentTitle("Battery Notification")
                        .setContentText("Test Battery")
                        .setAutoCancel(true)
                        .addAction(android.R.drawable.ic_lock_idle_low_battery, "attery settings", pi)
                        .addAction(android.R.drawable.ic_menu_close_clear_cancel, "OK", pi1).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(2, notification);
            }
        }
    }
}
