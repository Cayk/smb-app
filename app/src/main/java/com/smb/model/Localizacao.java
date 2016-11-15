package com.smb.model;

import java.util.Date;

/**
 * Created by Cayk Lima on 12/11/16.
 */
public class Localizacao {
    private String _id;
    private double latitude;
    private double longitude;
    private String bicicleta;

    public Localizacao(String _id, double longitude, double latitude, String bicicleta) {
        this._id = _id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.bicicleta = bicicleta;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(String bicicleta) {
        this.bicicleta = bicicleta;
    }
}
