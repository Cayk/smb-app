package com.smb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Cayk Lima on 12/11/16.
 */
public class Localizacao implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(bicicleta);
    }

    public static final Parcelable.Creator<Localizacao> CREATOR
            = new Parcelable.Creator<Localizacao>() {

        public Localizacao createFromParcel(Parcel in) {
            return new Localizacao(in);
        }

        public Localizacao[] newArray(int size) {
            return new Localizacao[size];
        }
    };

    private Localizacao(Parcel in) {
        _id = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        bicicleta = in.readString();
    }
}
