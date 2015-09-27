package com.example.revern.task4a;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.revern.task4a.Receivers.BatteryReceiver;
import com.example.revern.task4a.Receivers.RebootReceiver;
import com.example.revern.task4a.Service.MyService;

public class MainActivity extends AppCompatActivity {
    Button mTestButton;
    RebootReceiver rebootReceiver;
    BatteryReceiver batteryReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeNotification();
        mTestButton= (Button) findViewById(R.id.btn_test);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(android.R.drawable.ic_menu_report_image)
                        .setContentTitle("Notification")
                        .setContentText("Test text")
                        .setAutoCancel(true)
                        .build();
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification);
            }
        });
        rebootReceiver = new RebootReceiver();
        registerReceiver(rebootReceiver, new IntentFilter(Intent.ACTION_BOOT_COMPLETED));
        batteryReceiver = new BatteryReceiver();
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void timeNotification(){
        Intent i = new Intent(MainActivity.this, MyService.class);
        startService(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent i = new Intent(this, MyService.class);
        i.putExtra(MyService.NOTIFICATION_SERVICE, "off");
        startService(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
