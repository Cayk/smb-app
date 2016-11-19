package com.smb.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.smb.R;
import com.smb.model.Aplicacao;
import com.smb.model.Bicicleta;
import com.smb.listener.OnItemClickListener;
import com.smb.model.Localizacao;
import com.smb.model.Viagem;
import com.smb.adapter.ViagensRecyclerViewAdapter;
import com.smb.service.LocalizacaoService;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txNome;
    Aplicacao aplicacao;
    private List<Viagem> viagens;

    private RecyclerView mRecyclerView;
    private ViagensRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txNome = (TextView) findViewById(R.id.textNome);
        aplicacao = (Aplicacao) getApplication();

        if(aplicacao.getPessoa().getBicicleta() == null){
            carregarTelaCadastrarBike();
            finish();
        }else{
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            MainActivity.AsyncTaskBicicleta asyncTaskBicicleta = new MainActivity.AsyncTaskBicicleta();
            asyncTaskBicicleta.execute(aplicacao.getPessoa().getBicicleta());

        }
    }


    private class AsyncTaskBicicleta extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... dados) {

            OkHttpClient okHttpClient = new OkHttpClient();
            String resultado = null;

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("identificador", dados[0])
                    .build();

            Request request = new Request.Builder()
                    .url(aplicacao.getServidor() + "/bicicleta/viagens")
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
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Bicicleta bicicleta = gson.fromJson(result, Bicicleta.class);

                aplicacao.setBicicleta(bicicleta);

                Intent intent = new Intent(MainActivity.this, LocalizacaoService.class);
                startService(intent);

                if(bicicleta.getlistaViagens().size() > 0){
                    txNome.setText("Bem vindo "+aplicacao.getPessoa().getNome()+".");
                    viagens = bicicleta.getlistaViagens();
                    adapter = new ViagensRecyclerViewAdapter(MainActivity.this, viagens);
                    mRecyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Viagem viagem) {
                            Toast.makeText(MainActivity.this, viagem.getNome(),Toast.LENGTH_LONG).show();
                            carregarTelaViagemNoMapa(viagem.getListaLoc());
                        }
                    });
                }else{
                    txNome.setText("Bem vindo "+aplicacao.getPessoa().getNome()+". Você não possui viagens cadastradas. Inicie uma agora mesmo!");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.editar_perfil:
                Intent intent1 = new Intent(getApplicationContext(), EditarPerfilActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.localizacao_mapa:
                Intent intent2 = new Intent(getApplicationContext(), LocalizacaoActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void carregarTelaCadastrarBike(){
        Intent intent;
        intent = new Intent(this, BicicletaActivity.class);
        startActivity(intent);
    }

    public void carregarTelaMap(){
        Intent intent;
        intent = new Intent(this, LocalizacaoActivity.class);
        startActivity(intent);
    }

    public void carregarTelaViagemNoMapa(ArrayList<Localizacao> localizacoes){
        Log.i("Localizacoes", localizacoes.get(0).getBicicleta()+"");
        Intent intent;
        intent = new Intent(this, ViagemActivity.class);
        intent.putExtra("localizacoes", localizacoes);
        startActivity(intent);
    }
}
