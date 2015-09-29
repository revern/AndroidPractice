package com.example.revern.task5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    EditText mEditText;
    TextView mTextView1;
    TextView mTextView2;
    TextView mTextView3;
    TextView mTextView4;
    TextView mTextView5;
    TextView mTextView6;
    Countries countries;
    String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton= (Button) findViewById(R.id.test_btn);
        mEditText= (EditText) findViewById(R.id.test_et);
        mTextView1= (TextView) findViewById(R.id.part1);
        mTextView2= (TextView) findViewById(R.id.part2);
        mTextView3= (TextView) findViewById(R.id.part3);
        mTextView4= (TextView) findViewById(R.id.part4);
        mTextView5= (TextView) findViewById(R.id.part5);
        mTextView6= (TextView) findViewById(R.id.part6);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = mEditText.getText().toString();
                getStringFromAssetFile(file);
                takeCountriesFromJson();
                showCountriesWithMoreThan27cities();
                showCitiesWithPopulationOver45kk();
                showCountryWithBiggestPopulation();
                showCountryWithMostEvolvedCities();
                showFirstCountryUpper60lat();
                showCountryWithMoreThan2citiesHaveMoreThan7districts();
            }
        });
    }
    public void getStringFromAssetFile(String file)
    {
        byte[] buffer = null;
        InputStream is;
        try {
            is = getAssets().open(file);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        json = new String(buffer);
    }
    public void takeCountriesFromJson(){
        try {
            countries = new Gson().fromJson(json, Countries.class);

        }catch (Exception e){
            Log.d("Error", "error");
        }
        if (countries.getCountries() == null) {
            Log.d("Error","empty");
        }
    }
    public void showCountriesWithMoreThan27cities(){
        ArrayList<Country> list = new ArrayList<>();

        for (int i=0;i<countries.getCountries().size();i++) {
            if (countries.getCountries().get(i).getCities().size() > 27) {
                list.add(countries.getCountries().get(i));
            }
        }
        String text="";
        for(int i=0;i<list.size();i++)
            text+=list.get(i).getName();
        mTextView1.setText(text);
    }
    public void showCitiesWithPopulationOver45kk(){
        ArrayList<City> list = new ArrayList<>();
        for(int i=0; i<countries.getCountries().size();i++){
            Country c=countries.getCountries().get(i);
            for(int j=0;j<c.getCities().size();j++){
                if(c.getCities().get(j).getPopulation()>=45000000){
                    list.add(c.getCities().get(j));
                }
            }
        }
        String text="";
        for(int i=0;i<list.size();i++)
            text+=list.get(i).getName();
        mTextView2.setText(text);
    }
    public void showCountryWithBiggestPopulation(){
        int[] population=new int[countries.getCountries().size()];
        int max = 0;
        int index=0;
        for(int i=0;i<countries.getCountries().size();i++){
            Country c=countries.getCountries().get(i);
            for(int j=0;j<c.getCities().size();j++){
                population[i]+=c.getCities().get(j).getPopulation();
            }
            if(population[i]>max){
                max=population[i];
                index=i;
            }
        }
        mTextView3.setText(countries.getCountries().get(index).getName());
    }
    public void showCountryWithMostEvolvedCities(){
        int[] evolvedCities=new int[countries.getCountries().size()];
        int max = 0;
        int index=0;
        for(int i=0; i<countries.getCountries().size();i++){
            Country c = countries.getCountries().get(i);
            for(int j=0;j<c.getCities().size();j++){
                City city = c.getCities().get(j);
                if(city.getMarkers().contains(City.Marker.RESORT)&& city.getMarkers().contains(City.Marker.COUNTRY_CAPITAL)
                        && city.getMarkers().contains(City.Marker.WITH_AIRPORT)){
                    evolvedCities[i]++;
                }
                if(evolvedCities[i]>max){
                    max=evolvedCities[i];
                    index=i;
                }
            }
        }
        mTextView4.setText(countries.getCountries().get(index).getName());
    }
    public void showFirstCountryUpper60lat(){
        boolean upper60=false;
        for(int i = 0;i<countries.getCountries().size();i++){
            Country c = countries.getCountries().get(i);
            for(int j=0;j<c.getCities().size();j++){
                if(!(c.getCities().get(j).getLocation().getLatitude()>=60)){
                    upper60=true;
                    break;
                }
            }
            if(upper60){
                upper60=false;
            }else{
                mTextView5.setText(c.getName());
                break;
            }
        }
    }
    public void showCountryWithMoreThan2citiesHaveMoreThan7districts(){
        ArrayList<Country> list =new ArrayList<>();
        int[] count = new int[countries.getCountries().size()];
        for(int i=0;i<countries.getCountries().size();i++){
            Country c = countries.getCountries().get(i);
            for(int j=0; j<c.getCities().size();j++){
                if(c.getCities().get(j).getDistricts().size()>=7){
                    count[i]++;
                }
            }
            if(count[i]>=2){
                list.add(c);
            }
        }
        String text="";
        for(int i=0;i<list.size();i++)
            text+=list.get(i).getName();
        mTextView1.setText(text);
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
