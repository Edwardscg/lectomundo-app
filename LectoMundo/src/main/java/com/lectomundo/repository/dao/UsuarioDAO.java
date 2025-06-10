package com.lectomundo.repository.dao;

import com.lectomundo.model.Administrador;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.helper.DBHelper;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) throws Exception {

        String sql = "INSERT INTO usuario (nombre, correo, contrasena, tipo, monedas) VALUES (?, ?, ?, ?, ?);";

        Object monedas = (usuario instanceof Administrador) ? null:0;

        int filasAfectadas= DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), monedas);

        if(filasAfectadas ==0){

            throw new Exception("No se pudo registrar al usuario");
        }
    }
}
