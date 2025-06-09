package com.lectomundo.model;

import java.time.LocalDate;

public class Valoracion {
    private int id_valoracion;
    private Cliente cliente;
    private Documento documento;
    private int puntuacion;
    private String comentario;
    private LocalDate fecha_valoracion;

    public Valoracion(int id_valoracion, Cliente cliente, Documento documento, int puntuacion, String comentario, LocalDate fecha_valoracion) {
        this.id_valoracion = id_valoracion;
        this.cliente = cliente;
        this.documento = documento;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha_valoracion = fecha_valoracion;
    }

    public int getId_valoracion() {
        return id_valoracion;
    }

    public void setId_valoracion(int id_valoracion) {
        this.id_valoracion = id_valoracion;
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

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha_valoracion() {
        return fecha_valoracion;
    }

    public void setFecha_valoracion(LocalDate fecha_valoracion) {
        this.fecha_valoracion = fecha_valoracion;
    }
}
