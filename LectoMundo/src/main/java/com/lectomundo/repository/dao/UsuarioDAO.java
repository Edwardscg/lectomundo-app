package com.lectomundo.repository.dao;

import com.lectomundo.model.Administrador;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.helper.DBHelper;

import java.sql.ResultSet;
import java.util.List;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) throws Exception {

        String sql = "INSERT INTO usuario (nombre_usuario, correo, contraseña, tipo, monedas) VALUES (?, ?, ?, ?, ?);";

        Object monedas = (usuario instanceof Administrador) ? null:0;

        int filasAfectadas= DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), monedas);

        if(filasAfectadas ==0){

            throw new Exception("No se pudo registrar al usuario");
        }
    }

    public void actualizarUsuario(Usuario usuario) throws Exception {

        boolean es_admin = usuario instanceof Administrador;

        String sql = es_admin ? "UPDATE usuario SET nombre_usuario = ?, correo = ?, contraseña = ?, tipo = ? WHERE id_usuario = ?" : "UPDATE usuario SET nombre_usuario = ?, correo = ?, contraseña = ?, tipo = ?, monedas = ? WHERE id_usuario = ?";

        int fila_afectada = es_admin ? DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), usuario.getId_usuario()) : DBHelper.manejarEntidad(sql, usuario.getNombre_usuario(), usuario.getCorreo(), usuario.getContraseña(), usuario.getTipo_usuario(), ((Cliente) usuario).getMonedas(), usuario.getId_usuario());

        if (fila_afectada == 0) {

            throw new Exception("No se pudo actualizar el usuario.");
        }
    }

    public void actualizarMonedas(int id_usuario, int nuevas_monedas) throws Exception {

        String sql = "UPDATE usuario SET monedas = ? WHERE id_usuario = ?";

        DBHelper.manejarEntidad(sql, nuevas_monedas, id_usuario);
    }

    public void cambiarContraseña(int id_usuario, String contraseña_actual, String nueva_contrasena) throws Exception {

        String sql = "UPDATE usuario SET contraseña = ? WHERE id_usuario = ? AND contraseña = ?";

        DBHelper.manejarEntidad(sql, nueva_contrasena, id_usuario, contraseña_actual);
    }

    public void eliminarUsuario(int id_usuario) throws Exception {

        String sql = "DELETE FROM usuario WHERE id_usuario = ?;";

        int filasAfectadas = DBHelper.manejarEntidad(sql, id_usuario);

        if(filasAfectadas == 0){

            throw new Exception("No se pudo eliminar al usuario");
        }
    }

    public Usuario buscarUsuarioPorId(int id_usuario) throws Exception {
        String sql ="SELECT * FROM usuario WHERE id_usuario = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, id_usuario);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) throws Exception {
        String sql ="SELECT * FROM usuario WHERE correo = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, correo);
    }

    public Usuario loguearUsuario(String correo, String contrasena) throws Exception {

        String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, correo, contrasena);
    }

    public List<Usuario> buscarUsuariosPorTipo(String tipo_usuario) throws Exception {

        String sql ="SELECT * FROM usuario WHERE tipo = ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearFilaUsuario, tipo_usuario);
    }

    private Usuario mapearFilaUsuario(ResultSet rs) throws Exception{

        String tipo_usuario = rs.getString("tipo");

        if(tipo_usuario.equals("cliente")){

            return new Cliente(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("contraseña"), rs.getString("tipo"), rs.getInt("monedas"));

        } else{

            return new Administrador(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("correo"), rs.getString("contraseña"), rs.getString("tipo"));

        }
    }
}
