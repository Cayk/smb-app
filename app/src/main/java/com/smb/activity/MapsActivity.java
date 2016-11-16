package com.smb.activity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smb.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private BroadcastReceiver receiver;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-4.9707547,-39.0205224);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(mMap != null){
            LatLng l = new LatLng(-4.9707547,-39.0205224);

            MarkerOptions a = new MarkerOptions();
            a.position(l);
            mMap.addMarker(a);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
