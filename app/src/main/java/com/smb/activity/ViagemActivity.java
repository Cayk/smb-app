package com.smb.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smb.R;
import com.smb.model.Localizacao;

import java.util.ArrayList;

public class ViagemActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(mMap != null){

            Bundle bundle = getIntent().getExtras();
            ArrayList<Localizacao> localizacoes = bundle.getParcelableArrayList("localizacoes");
            Log.i("Loc", localizacoes.size()+"");
            if(localizacoes != null){
                for(Localizacao localizacao : localizacoes) {
                    LatLng ponto = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(ponto));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(ponto));
                }
            }
        }
    }
}
