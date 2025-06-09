package com.lectomundo.model;

public class Administrador extends Usuario{

    public Administrador(int id_usuario, String nombre_usuario, String correo, String contraseña, String tipo_usuario) {
        super(id_usuario, nombre_usuario, correo, contraseña, tipo_usuario);
    }
}
