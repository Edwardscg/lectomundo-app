package com.lectomundo.model;

public class Cliente extends Usuario{
    private int monedas;

    public Cliente(int id_usuario, String nombre_usuario, String correo, String contraseña, String tipo_usuario, int monedas) {
        super(id_usuario, nombre_usuario, correo, contraseña, tipo_usuario);
        this.monedas = monedas;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }
}
