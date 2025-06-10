package com.lectomundo.model;

public class Favorito {
    private int id_favorito;
    private Cliente cliente;
    private Documento documento;
    private boolean es_favorito;

    public Favorito(int id_favorito, Cliente cliente, Documento documento, boolean es_favorito) {
        this.id_favorito = id_favorito;
        this.cliente = cliente;
        this.documento = documento;
        this.es_favorito = es_favorito;
    }

    public int getId_favorito() {
        return id_favorito;
    }

    public void setId_favorito(int id_favorito) {
        this.id_favorito = id_favorito;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public boolean isEs_favorito() {
        return es_favorito;
    }

    public void setEs_favorito(boolean es_favorito) {
        this.es_favorito = es_favorito;
    }
}
