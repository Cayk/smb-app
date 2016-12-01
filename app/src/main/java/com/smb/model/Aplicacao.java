package com.smb.model;

import android.app.Application;

/**
 * Created by Cayk Lima on 13/11/16.
 */

public class Aplicacao extends Application{
    private Pessoa pessoa;
    private Bicicleta bicicleta;
    private Localizacao localizacao;
    private Viagem viagem;
    private boolean flagViagem = false;
    //private String servidor = "http://35.161.237.63:3000";
    private String servidor = "http://172.18.22.212:3000";

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

    public void setBicicleta(Bicicleta bicicleta) { this.bicicleta = bicicleta; }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public Viagem getViagem() {
        if(viagem == null){
            viagem = new Viagem();
        }
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public boolean isFlagViagem() {
        return flagViagem;
    }

    public void setFlagViagem(boolean flagViagem) {
        this.flagViagem = flagViagem;
    }
}
