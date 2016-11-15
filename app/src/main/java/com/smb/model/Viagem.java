package com.smb.model;

import java.util.List;

/**
 * Created by Cayk Lima on 12/11/16.
 */
public class Viagem {
    private String _id;
    private String nome;
    private List<Localizacao> listaLoc;

    public Viagem(String _id, String nome, List<Localizacao> listaLoc) {
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

    public List<Localizacao> getListaLoc() {
        return listaLoc;
    }

    public void setListaLoc(List<Localizacao> listaLoc) {
        this.listaLoc = listaLoc;
    }
}
