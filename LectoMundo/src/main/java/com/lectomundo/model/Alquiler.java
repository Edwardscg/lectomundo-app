package com.lectomundo.model;

import java.time.LocalDateTime;

public class Alquiler {
    private int id_alquiler;
    private Cliente cliente;
    private Documento documento;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    private Estado estado_alquiler; // activo, finalizado

    public Alquiler() {
    }

    public Alquiler(int id_alquiler, Cliente cliente, Documento documento, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, Estado estado_alquiler) {
        this.id_alquiler = id_alquiler;
        this.cliente = cliente;
        this.documento = documento;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado_alquiler = estado_alquiler;
    }

    public int getId_alquiler() {
        return id_alquiler;
    }

    public void setId_alquiler(int id_alquiler) {
        this.id_alquiler = id_alquiler;
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

    public LocalDateTime getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDateTime fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDateTime getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDateTime fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Estado getEstado_alquiler() {
        return estado_alquiler;
    }

    public void setEstado_alquiler(Estado estado_alquiler) {
        this.estado_alquiler = estado_alquiler;
    }
}
