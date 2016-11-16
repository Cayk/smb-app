package com.smb.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smb.R;
import com.smb.model.Aplicacao;
import com.smb.model.Pessoa;
import com.smb.util.Validador;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private String[] dados;
    private EditText edEmail;
    private EditText edSenha;
    Aplicacao aplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        aplicacao = (Aplicacao) getApplication();
        edEmail = (EditText) findViewById(R.id.email);
        edSenha = (EditText) findViewById(R.id.senha);
        Button bt = (Button) findViewById(R.id.entrar);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valEmail = Validador.validadeEmail(edEmail, "Email inválido");
                boolean valSenha = Validador.validateNotNull(edSenha, "Preencha o campo senha");

                if(valEmail && valSenha){
                    final String email = edEmail.getEditableText().toString();
                    final String senha = edSenha.getEditableText().toString();

                    dados = new String[]{email, senha};

                    LoginActivity.AsyncTaskLogin asyncTaskLogin = new LoginActivity.AsyncTaskLogin();
                    asyncTaskLogin.execute(dados);
                }else{
                    Toast.makeText(LoginActivity.this, "Paramêtros inválidos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class AsyncTaskLogin extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... dados) {

            OkHttpClient okHttpClient = new OkHttpClient();
            String resultado = null;

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("email", dados[0])
                    .add("senha", dados[1])
                    .build();

            Request request = new Request.Builder()
                    .url(aplicacao.getServidor() + "/pessoa/login")
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();

                resultado = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("Resultado", resultado);
            return resultado;
        }

        @Override
        protected void onPostExecute(String result){
            if(!result.equals("null")){

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Pessoa pessoa = gson.fromJson(result, Pessoa.class);

                aplicacao.setPessoa(pessoa);
                carregarTelaPrincipal();
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "Email ou senha inválido!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void carregarTelaCadastrar(View view){
        Intent intent;
        intent = new Intent(this, CadastrarActivity.class);
        startActivity(intent);
    }

    public void carregarTelaPrincipal(){
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
