package com.googlemaps.template.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;
import com.googlemaps.template.myapplication.model.Locality;

public class LocalityInfo extends AppCompatActivity {
    public static final String EXTRA_INFO = "EXTRA INFO";
    private EditText mETInfo;
    private Locality locality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locality_info);
        mETInfo= (EditText) findViewById(R.id.et_info);
        String json=getIntent().getStringExtra(EXTRA_INFO);
        locality = new Gson().fromJson(json, Locality.class);
        mETInfo.setText(locality.getInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_locality_info, menu);
        return true;
    }
    @Override
    protected void onStop(){
        super.onStop();
        locality.setInfo(mETInfo.getText().toString());
        Intent data = new Intent();
        data.putExtra(EXTRA_INFO,mETInfo.getText());
        setResult(RESULT_OK, data);
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
