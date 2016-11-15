package com.smb.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smb.R;
import com.smb.model.Aplicacao;
import com.smb.model.Bicicleta;
import com.smb.model.Pessoa;
import com.smb.util.Validador;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class BicicletaActivity extends AppCompatActivity {

    private EditText txIdentificador;
    Aplicacao aplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicicleta);

        aplicacao = (Aplicacao) getApplication();
        txIdentificador = (EditText) findViewById(R.id.bike);
        Button bt = (Button) findViewById(R.id.cadastrar);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valSenha = Validador.validateNotNull(txIdentificador, "Preencha o campo corretamente.");
                if(valSenha){
                    final String bicicleta = txIdentificador.getEditableText().toString();

                    BicicletaActivity.AsyncTaskCadastrarBike asyncTaskCadastrarBike = new BicicletaActivity.AsyncTaskCadastrarBike();
                    asyncTaskCadastrarBike.execute(bicicleta);
                }
            }
        });
    }

    private class AsyncTaskCadastrarBike extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... dados) {
            String pessoa = aplicacao.getPessoa().getId();
            OkHttpClient okHttpClient = new OkHttpClient();
            String resultado = null;

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("bicicleta", dados[0])
                    .add("pessoa", pessoa)
                    .build();

            Request request = new Request.Builder()
                    .url(aplicacao.getServidor() + "/bicicleta/app-cadastrar")
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();

                resultado = response.body().string();
                Log.i("Pessoa", resultado);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(String result){
            if(result != null){

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Pessoa pessoa = gson.fromJson(result, Pessoa.class);

                aplicacao.setPessoa(pessoa);
                carregarTelaPrincipal();
                finish();
            }else{
                Toast.makeText(BicicletaActivity.this, "Erro ao cadastrar bicicleta! Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void carregarTelaPrincipal(){
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
