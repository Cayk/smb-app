package com.smb.model;

import android.app.Application;

/**
 * Created by Cayk Lima on 13/11/16.
 */

public class Aplicacao extends Application{
    private Pessoa pessoa;
    private Bicicleta bicicleta;
    private Localizacao localizacao;
    //192.168.1.18
    private String servidor = "http://200.129.38.16:3000";
    private String caminho = "/pessoa/cadastrar";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Bicicleta getBicicleta() { return bicicleta; }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public void setBicicleta(Bicicleta bicicleta) { this.bicicleta = bicicleta; }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
