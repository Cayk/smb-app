package com.smb.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smb.R;
import com.smb.model.Aplicacao;
import com.smb.model.Bicicleta;
import com.smb.model.OnItemClickListener;
import com.smb.model.Viagem;
import com.smb.model.ViagensRecyclerViewAdapter;
import com.smb.service.LocalizacaoService;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

public class LocalizacaoActivity extends FragmentActivity implements OnMapReadyCallback {

    Aplicacao aplicacao;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        aplicacao = (Aplicacao) getApplication();
        final Button bt = (Button) findViewById(R.id.novaViagem);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aplicacao.isFlagViagem() == false){
                    aplicacao.setFlagViagem(true);
                    bt.setText("Finalizar");
                }else{
                    aplicacao.setFlagViagem(false);
                    bt.setText("Nova Viagem");
                    bt.setEnabled(false);
                    LocalizacaoActivity.AsyncTaskSalvarViagem asyncTaskSalvarViagem = new LocalizacaoActivity.AsyncTaskSalvarViagem();
                    asyncTaskSalvarViagem.execute();
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object object) {
        Log.i("Entrou", "entrou");
        aplicacao = (Aplicacao) getApplication();

        if(mMap != null){
            LatLng latLng = new LatLng(aplicacao.getLocalizacao().getLatitude(),aplicacao.getLocalizacao().getLongitude());

            mMap.clear();
            MarkerOptions marker = new MarkerOptions();
            marker.position(latLng);
            mMap.addMarker(marker).setTitle("Localização atual");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        }
    }

    private class AsyncTaskSalvarViagem extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... dados) {

            OkHttpClient okHttpClient = new OkHttpClient();

            aplicacao = (Aplicacao) getApplication();
            String resultado = null;

            Gson g = new Gson();
            String js = g.toJson(aplicacao.getViagem());

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("identificador", aplicacao.getBicicleta().getIdentificador())
                    .add("viagem", js)
                    .build();

            Request request = new Request.Builder()
                    .url(aplicacao.getServidor() + "/bicicleta/cadastrar-viagem")
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();

                resultado = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(String result){

            if(result != null) {
                final Button bt = (Button) findViewById(R.id.novaViagem);
                bt.setEnabled(true);
                Toast.makeText(LocalizacaoActivity.this, "Viagem finalizada com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
