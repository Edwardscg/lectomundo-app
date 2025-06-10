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

    public void actualizarUsuario(Usuario usuario) throws Exception {

        boolean es_admin = usuario instanceof Administrador;

        String sql = es_admin ? "UPDATE usuario SET nombre = ?, correo = ?, contrasena = ?, tipo = ? WHERE id_usuario = ?" : "UPDATE usuario SET nombre = ?, correo = ?, contrasena = ?, tipo = ?, monedas = ? WHERE id_usuario = ?";

        int fila_afectada = es_admin ? DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), usuario.getId_usuario()) : DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), ((Cliente) usuario).getMonedas(), usuario.getId_usuario());

        if (fila_afectada == 0) {

            throw new Exception("No se pudo actualizar el usuario.");
        }
    }

    public void actualizarMonedas(int id_usuario, int nuevas_monedas) throws Exception {

        String sql = "UPDATE usuario SET monedas = ? WHERE id_usuario = ?";

        DBHelper.manejarEntidad(sql, nuevas_monedas, id_usuario);
    }
}
