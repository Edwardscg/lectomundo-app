package com.lectomundo.model;

import java.time.LocalDate;

public class Membresia {

    private int id_membresia;
    private Cliente cliente;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private int precio;
    private Estado estado_membresia;

    public Membresia(int id_membresia, Cliente cliente, LocalDate fecha_inicio, LocalDate fecha_fin, int precio, Estado estado_membresia) {
        this.id_membresia = id_membresia;
        this.cliente = cliente;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.precio = precio;
        this.estado_membresia = estado_membresia;
    }

    public int getId_membresia() {
        return id_membresia;
    }

    public void setId_membresia(int id_membresia) {
        this.id_membresia = id_membresia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Estado getEstado_membresia() {
        return estado_membresia;
    }

    public void setEstado_membresia(Estado estado_membresia) {
        this.estado_membresia = estado_membresia;
    }
}