package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.List;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuario (nombre_usuario, correo, contraseña, tipo, monedas) VALUES (?, ?, ?, ?, ?);";

        Object monedas = (usuario instanceof Administrador) ? null : 0;

        DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), monedas);
    }

    public void actualizarUsuario(Usuario usuario) {

        boolean es_admin = usuario instanceof Administrador;

        String sql = es_admin ? "UPDATE usuario SET nombre_usuario = ?, correo = ?, contraseña = ?, tipo = ? WHERE id_usuario = ?" : "UPDATE usuario SET nombre_usuario = ?, correo = ?, contraseña = ?, tipo = ?, monedas = ? WHERE id_usuario = ?";

        if (es_admin) {
            DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), usuario.getId_usuario());
        }else {
            DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), ((Cliente) usuario).getMonedas(), usuario.getId_usuario());
        }
    }

    public void actualizarMonedas(int id_usuario, int nuevas_monedas) {

        String sql = "UPDATE usuario SET monedas = ? WHERE id_usuario = ?";

        DBHelper.manejarEntidad(sql, nuevas_monedas, id_usuario);
    }

    public void cambiarContraseña(int id_usuario, String contraseña_actual, String nueva_contrasena) {

        String sql = "UPDATE usuario SET contraseña = ? WHERE id_usuario = ? AND contraseña = ?";

        DBHelper.manejarEntidad(sql, nueva_contrasena, id_usuario, contraseña_actual);
    }

    public void eliminarUsuario(int id_usuario) {

        String sql = "DELETE FROM usuario WHERE id_usuario = ?;";

        DBHelper.manejarEntidad(sql, id_usuario);
    }

    public Usuario buscarUsuarioPorId(int id_usuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, id_usuario);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, correo);
    }

    public Usuario loguearUsuario(String correo, String contrasena) {

        String sql = "SELECT * FROM usuario WHERE correo = ? AND contraseña = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, correo, contrasena);
    }

    public List<Usuario> buscarUsuariosPorTipo(String tipo_usuario) {

        String sql = "SELECT * FROM usuario WHERE tipo = ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearFilaUsuario, tipo_usuario);
    }

    public List<Usuario> obtenerUsuarios() {

        String sql = "select * from usuario";

        return DBHelper.obtenerListaEntidad(sql, this::mapearFilaUsuario);
    }

    public ObservableList<Usuario> verUsuarios() {

        String sql = "SELECT * FROM usuario";

        return DBHelper.llenarTabla(sql, rs -> {
            String tipo_usuario = rs.getString("tipo");

            if (tipo_usuario.equals("cliente")) {

                int monedas = rs.getInt("monedas");

                return new Cliente(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("contraseña"), tipo_usuario, monedas);
            } else {

                return new Administrador(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("contraseña"), tipo_usuario);
            }
        });
    }

    private Usuario mapearFilaUsuario(ResultSet rs) {

        try {

            String tipo_usuario = rs.getString("tipo");

            if (tipo_usuario.equals("cliente")) {

                return new Cliente(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("contraseña"), rs.getString("tipo"), rs.getInt("monedas"));

            } else {

                return new Administrador(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("contraseña"), rs.getString("tipo"));

            }

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del alquiler desde la Base de Datos.");
        }
    }
}
