package com.smb.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smb.R;
import com.smb.model.Aplicacao;
import com.smb.util.Validador;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class CadastrarActivity extends AppCompatActivity {

    private String[] dados;
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfSenha;
    Aplicacao aplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aplicacao = (Aplicacao) getApplication();
        editNome = (EditText) findViewById(R.id.nome);
        editEmail = (EditText) findViewById(R.id.email);
        editSenha = (EditText) findViewById(R.id.senha);
        editConfSenha = (EditText) findViewById(R.id.confirmarSenha);

        Button cadastrar = (Button) findViewById(R.id.criar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valnome = Validador.validateNotNull(editNome, "Preencha o campo nome");
                boolean valsenha = Validador.validateNotNull(editSenha, "Preencha o campo senha");
                boolean valCsenha =  Validador.validateNotNull(editConfSenha, "Preencha o campo confirmar senha");
                boolean valSenhas = Validador.validateSenha(editSenha, editConfSenha, "Senhas diferentes!");
                boolean valEmail = Validador.validadeEmail(editEmail, "Email inválido");

                if (valnome && valsenha && valCsenha && valSenhas && valEmail) {
                    final String nome = editNome.getEditableText().toString();
                    final String email = editEmail.getEditableText().toString();
                    final String senha = editSenha.getEditableText().toString();

                    dados = new String[]{nome, email, senha};

                    AsyncTaskCadastrar asyncTaskCadastrar = new AsyncTaskCadastrar();
                    asyncTaskCadastrar.execute(dados);
                }
            }
        });
    }

    private class AsyncTaskCadastrar extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... dados) {

            OkHttpClient okHttpClient = new OkHttpClient();
            String resultado = null;
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("nome", dados[0])
                    .add("email", dados[1])
                    .add("senha", dados[2])
                    .build();

            Request request = new Request.Builder()
                    .url(aplicacao.getServidor() + "/pessoa/cadastrar")
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
            if(result != null && result.equals("ok")){
                Toast.makeText(CadastrarActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(CadastrarActivity.this, "Email já cadastrado", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
