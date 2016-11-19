package com.smb.model;

/**
 * Created by Cayk Lima on 12/11/16.
 */

public class Pessoa {
    private String _id;
    private String nome;
    private String email;
    private String senha;
    private String bicicleta;

    public  Pessoa(){

    }
    public Pessoa(String _id, String nome, String email, String senha, String bicicleta) {
        this._id = _id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.bicicleta = bicicleta;
    }

    public String getId() {

        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(String bicicleta) {
        this.bicicleta = bicicleta;
    }
}