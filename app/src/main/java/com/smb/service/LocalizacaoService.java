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
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

    TimerTask timerLocalizacao = new TimerTask() {
        @Override
        public void run() {

            Aplicacao aplicacao = (Aplicacao) getApplication();

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

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Localizacao localizacao = gson.fromJson(resultado, Localizacao.class);

                aplicacao.setLocalizacao(localizacao);

                Log.i("Flag", aplicacao.isFlagViagem()+"");
                if(aplicacao.isFlagViagem() == true){
                    if(aplicacao.getViagem().getNome() == null){
                        int qtd = aplicacao.getBicicleta().getlistaViagens().size();
                        aplicacao.getViagem().setNome("Viagem "+ qtd++);
                        Log.i("Nome Viagem", aplicacao.getViagem().getNome());
                    }
                    aplicacao.getViagem().getListaLoc().add(localizacao);
                }

                EventBus.getDefault().post(new Object());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer t = new Timer();
        t.schedule(timerLocalizacao, 1000L, 30000L);

        return Service.START_STICKY;
    }
}
