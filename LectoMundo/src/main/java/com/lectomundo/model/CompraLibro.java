package com.lectomundo.model;

import java.time.LocalDateTime;

public class CompraLibro {
    private int id_compra;
    private Cliente cliente;
    private Documento documento;
    private LocalDateTime fecha_compra;
    private int costo;

    public CompraLibro(int id_compra, Cliente cliente, Documento documento, LocalDateTime fecha_compra, int costo) {
        this.id_compra = id_compra;
        this.cliente = cliente;
        this.documento = documento;
        this.fecha_compra = fecha_compra;
        this.costo = documento.getPrecio() * 3;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
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

    public LocalDateTime getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(LocalDateTime fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
