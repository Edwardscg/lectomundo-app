package com.lectomundo.model;

import java.time.LocalDateTime;

public class Notificacion {
    private int id_notificacion;
    private Cliente cliente;
    private String tipo; // vencimiento, recordatorio, compra (monedas, libro o membresia)
    private String mensaje;
    private LocalDateTime fecha_envio;
    private boolean es_leido;

    public Notificacion() {
    }

    public Notificacion(int id_notificacion, Cliente cliente, String tipo, String mensaje, LocalDateTime fecha_envio, boolean es_leido) {
        this.id_notificacion = id_notificacion;
        this.cliente = cliente;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fecha_envio = fecha_envio;
        this.es_leido = es_leido;
    }

    public int getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(int id_notificacion) {
        this.id_notificacion = id_notificacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(LocalDateTime fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public boolean isEs_leido() {
        return es_leido;
    }

    public void setEs_leido(boolean es_leido) {
        this.es_leido = es_leido;
    }
}
