package com.example.revern.task5;

import java.util.ArrayList;

/**
 * Created by Алмаз on 29.09.2015.
 */
public class Country {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    String name;
    ArrayList<City> cities;
}
