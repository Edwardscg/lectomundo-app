package com.lectomundo.model;

public class Favorito {
    private int id_favorito;
    private Cliente cliente;
    private Documento documento;

    public Favorito(int id_favorito, Cliente cliente, Documento documento) {
        this.id_favorito = id_favorito;
        this.cliente = cliente;
        this.documento = documento;
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
}
