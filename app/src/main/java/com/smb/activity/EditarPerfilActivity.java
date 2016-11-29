package com.smb.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class EditarPerfilActivity extends AppCompatActivity {

    private String[] dados;
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfSenha;
    Aplicacao aplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        aplicacao = (Aplicacao) getApplication();
        editNome = (EditText) findViewById(R.id.edit_nome);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editSenha = (EditText) findViewById(R.id.edit_senha);
        editConfSenha = (EditText) findViewById(R.id.edit_confirmarSenha);

        Button cadastrar = (Button) findViewById(R.id.editar);

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

                    EditarPerfilActivity.AsyncTaskEditarPerfil asyncTaskEditarPerfil = new EditarPerfilActivity.AsyncTaskEditarPerfil();
                    asyncTaskEditarPerfil.execute(dados);
                }
            }
        });
    }

    private class AsyncTaskEditarPerfil extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... dados) {

            aplicacao = (Aplicacao) getApplication();
            OkHttpClient okHttpClient = new OkHttpClient();
            String resultado = null;
            Pessoa pessoa = new Pessoa();

            pessoa.setId(aplicacao.getPessoa().getId());

            pessoa.setNome(dados[0]);

            pessoa.setEmail(dados[1]);

            pessoa.setSenha(dados[2]);

            Gson g = new Gson();
            String json = g.toJson(pessoa);
            Log.i("JSON", json);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("pessoa", json)
                    .build();

            Request request = new Request.Builder()
                    .url(aplicacao.getServidor() + "/pessoa/editar")
                    .put(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();

                resultado = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("Result", resultado);
            return resultado;
        }

        @Override
        protected void onPostExecute(String result){

            String ok = result.toString();
            if(ok != null){
                aplicacao.getPessoa().setNome(editNome.getEditableText().toString());
                aplicacao.getPessoa().setEmail(editEmail.getEditableText().toString());
                aplicacao.getPessoa().setSenha(editSenha.getEditableText().toString());

                editNome.setText("");
                editEmail.setText("");
                editSenha.setText("");
                editConfSenha.setText("");

                Toast.makeText(EditarPerfilActivity.this, "Perfil editado com sucesso!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(EditarPerfilActivity.this, "Erro ao editar perfil.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditarPerfilActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
