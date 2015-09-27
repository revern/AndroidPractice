package com.example.revern.task4a.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.revern.task4a.Receivers.OffReceiver;
import com.example.revern.task4a.Receivers.TimeReceiver;

import java.util.Calendar;

public class MyService extends Service {

    private AlarmManager manager;
    public static final String EXTRA_OFF_N ="EXTRA OFF N";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getExtras()!=null){
            setOffAlarm();
        } else {
            setTimeAlarm();
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
    public void setOffAlarm(){
        Intent i = new Intent(getApplicationContext(), OffReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
        manager.set(AlarmManager.RTC, System.currentTimeMillis()+60000, pi);
    }
    public void setTimeAlarm() {
        Intent i = new Intent(getApplicationContext(), TimeReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
        manager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
    }
}
