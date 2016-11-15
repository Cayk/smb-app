package com.smb.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smb.model.Aplicacao;
import com.smb.model.Localizacao;
import com.smb.model.Pessoa;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Cayk Lima on 15/11/16.
 */

public class LocalizacaoService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Aplicacao aplicacao = (Aplicacao) getApplication();
                while(true){

                    OkHttpClient okHttpClient = new OkHttpClient();

                    RequestBody requestBody = new FormEncodingBuilder()
                            .add("identificador", aplicacao.getBicicleta().getIdentificador())
                            .build();

                    Request request = new Request.Builder()
                            .url(aplicacao.getServidor() + "/localizacao/bike")
                            .post(requestBody)
                            .build();
                    try {
                        Response response = okHttpClient.newCall(request).execute();
                        String resultado = response.body().string();
                        Log.i("Script", resultado);

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        Localizacao localizacao = gson.fromJson(resultado, Localizacao.class);

                        String lat = String.valueOf(localizacao.getLatitude());
                        String lon = String.valueOf(localizacao.getLongitude());

                        Log.i("Latitude", lat);
                        Log.i("Longitude", lon);

                        aplicacao.setLocalizacao(localizacao);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return Service.START_STICKY;
    }
}
