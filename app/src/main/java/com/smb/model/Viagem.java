package com.smb.model;

import java.util.ArrayList;

/**
 * Created by Cayk Lima on 12/11/16.
 */
public class Viagem {
    private String _id;
    private String nome;
    private ArrayList<Localizacao> listaLoc;

    public Viagem() {

    }

    public Viagem(String _id, String nome, ArrayList<Localizacao> listaLoc) {
        this._id = _id;
        this.nome = nome;
        this.listaLoc = listaLoc;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Localizacao> getListaLoc() {
        if(listaLoc == null){
            listaLoc = new ArrayList<Localizacao>();
        }
        return listaLoc;
    }

    public void setListaLoc(ArrayList<Localizacao> listaLoc) {
        this.listaLoc = listaLoc;
    }
}
