package com.lectomundo.model;

import java.time.LocalDateTime;

public class MovimientoMoneda {
    private int id_movimiento;
    private Cliente cliente;
    private String tipo_movimiento; // recarga, gasto
    private int monto;
    private LocalDateTime fecha_movimiento;

    public MovimientoMoneda(int id_movimiento, Cliente cliente, String tipo_movimiento, int monto, LocalDateTime fecha_movimiento) {
        this.id_movimiento = id_movimiento;
        this.cliente = cliente;
        this.tipo_movimiento = tipo_movimiento;
        this.monto = monto;
        this.fecha_movimiento = fecha_movimiento;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }
}
