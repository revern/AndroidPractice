package com.googlemaps.template.myapplication.model;

/**
 * Created by Алмаз on 17.09.2015.
 */
public class Locality {
    int id;
    String locName;
    double lat;
    double lng;
    String info;
    public Locality(String name, Double lat, Double lng){
        locName=name;
        this.lat=lat;
        this.lng=lng;
    }
    public Locality(int id, String name, Double lat, Double lng, String info){
        this.id=id;
        locName=name;
        this.lat=lat;
        this.lng=lng;
        this.info=info;
    }
    public Locality(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
