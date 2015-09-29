package com.example.revern.task5;


import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Алмаз on 29.09.2015.
 */
public class City {
    String name;
    int population;
    Location location;
    ArrayList<District> districts;
    Set<Marker> markers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<District> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<District> districts) {
        this.districts = districts;
    }

    public Set<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(Set<Marker> markers) {
        this.markers = markers;
    }

    public enum Marker {
        COUNTRY_CAPITAL, STATE_CENTER, WITH_AIRPORT, BUSINESS_CENTER, RESORT
    }

}
