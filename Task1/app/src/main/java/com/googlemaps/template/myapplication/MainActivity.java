package com.googlemaps.template.myapplication;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlemaps.template.myapplication.model.Locality;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.nio.channels.ConnectionPendingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    final Uri LOCALITY_URI = Uri
            .parse("com.googlemaps.template.myapplication.localitiesprovider");
    final String LOCALITY_NAME = "name";
    final String LOCALITY_INFO = "info";
    final String LOCALITY_LAT = "lat";
    final String LOCALITY_LNG = "lng";
    GoogleMap mMap;
    GPSTracker gps;
    private double lat=0;
    private double lng=0;
    private String httpAnswer="nothing";
    private ArrayList<Locality> mLocalities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateLocalities();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        takeGPSLocation();
        makeGetRequestOnNewThread();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap=map;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(lat, lng), 8));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for (int i=0;i<mLocalities.size();i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mLocalities.get(i).getLat(), mLocalities.get(i).getLng()))
                    .title(mLocalities.get(i).getLocName()).snippet(mLocalities.get(i).getInfo()));
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MainActivity.this, LocalityInfo.class);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                String json = gson.toJson(mLocalities.get(locIndex(marker.getTitle())));
                intent.putExtra(LocalityInfo.EXTRA_INFO, json);
                startActivityForResult(intent, 0);
            }
        });
    }
    public int locIndex(String name){
        for (int i = 0; i < mLocalities.size(); i++) {
            if(name.equals(mLocalities.get(i).getLocName())){
                return i;
            }
        }
        return 0;
    }
    public void updateLocalities(){
        Cursor cursor = getContentResolver().query(LOCALITY_URI, null, null,
                null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    mLocalities.add(new Locality(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4)));
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void takeGPSLocation(){
        gps = new GPSTracker(MainActivity.this);
        if(gps.canGetLocation){
            lat=gps.getLatitude();
            lng=gps.getLongitude();
        }
    }
    private void makeGetRequest() {
        String address="https://geocode-maps.yandex.ru/1.x/?geocode="+lat+","+lng+"&spn=0.7,0.7&kind=locality&format=json";
        HttpClient client = new DefaultHttpClient();
        ResponseHandler res = new BasicResponseHandler();
        HttpGet request = new HttpGet(address);
        try {
            httpAnswer=client.execute(request, res).toString();
            Log.d("Response of GET request", httpAnswer);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        takeLocalities();
    }
    public void makeGetRequestOnNewThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makeGetRequest();
            }
        });
        t.start();
        try {
            t.join();
        }catch (Exception e){
            Log.d("JOIN", "failed");
        }
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Data updated")
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void lInsert(String name, double lat, double lng, String info) {
        ContentValues cv = new ContentValues();
        cv.put(LOCALITY_NAME, name);
        cv.put(LOCALITY_INFO, info);
        cv.put(LOCALITY_LAT, lat);
        cv.put(LOCALITY_LNG, lng);
        getContentResolver().insert(LOCALITY_URI, cv);
    }
    public void lUpdate(int id, String name, double lat, double lng, String info) {
        ContentValues cv = new ContentValues();
        cv.put(LOCALITY_NAME, name);
        cv.put(LOCALITY_INFO, info);
        cv.put(LOCALITY_LAT, lat);
        cv.put(LOCALITY_LNG, lng);
        Uri uri = ContentUris.withAppendedId(LOCALITY_URI, id);
        getContentResolver().update(uri, cv, null, null);
    }
    public void takeLocalities(){
        String json=httpAnswer;
        Log.d("JSON", json);
        try {
            for (int i = 0; i < 10; i++) {
                int index = json.indexOf("LocalityName");
                json = json.substring(index + 15);
                index = json.indexOf("\"");
                String locName = json.substring(0, index);
                index = json.indexOf("pos\":\"");
                json = json.substring(index + 6);
                index = json.indexOf(" ");
                String coordinate1 = json.substring(0, index);
                json = json.substring(index + 1);
                index = json.indexOf("\"");
                String coordinate2 = json.substring(0, index);
                double locLat = Double.parseDouble(coordinate1);
                double locLng = Double.parseDouble(coordinate2);
                mLocalities.add(new Locality(locName, locLat, locLng));
                if(mLocalities.size()<10)
                    lInsert(locName, locLat, locLng ,"");
                else {
                    lUpdate(i, locName, locLat, locLng ,"");
                }
            }

        }catch(ConnectionPendingException e){
            Log.d("count of localities", mLocalities.size()+"");
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Data loading error!")
                    .setMessage("Do you wana retry?")
                    .setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    takeLocalities();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String updatedInfo = data.getStringExtra(LocalityInfo.EXTRA_INFO);
        Locality loc = new Gson().fromJson(updatedInfo, Locality.class);
        lUpdate(loc.getId(),loc.getLocName(),loc.getLat(),loc.getLng(),loc.getInfo());
    }


}