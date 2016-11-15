package com.smb.model;

import java.util.List;

/**
 * Created by Cayk Lima on 12/11/16.
 */

public class Bicicleta {
    private String _id;
    private String identificador;
    private List<Viagem> listaViagens;

    public Bicicleta(String _id, String identificador, List<Viagem> listaViagens) {
        this._id = _id;
        this.identificador = identificador;
        this.listaViagens = listaViagens;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public List<Viagem> getlistaViagens() {
        return listaViagens;
    }

    public void setlistaViagens(List<Viagem> viagens) {
        this.listaViagens = viagens;
    }
}
