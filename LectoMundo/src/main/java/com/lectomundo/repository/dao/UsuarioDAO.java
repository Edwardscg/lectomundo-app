package com.lectomundo.repository.dao;

import com.lectomundo.model.*;
import com.lectomundo.repository.helper.DBHelper;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

import java.util.List;

/**
 * Clase DAO encargada de realizar operaciones CRUD sobre la tabla "usuario".
 * Maneja tanto usuarios de tipo Cliente como Administrador.
 */
public class UsuarioDAO {

    private static final String cliente = "cliente";

    /**
     * Registra un nuevo usuario en la base de datos.
     * Si es administrador, no se asignan monedas.
     *
     * @param usuario Objeto Usuario a registrar.
     */
    public void registrarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuario (nombre_usuario, correo, contraseña, tipo, monedas) VALUES (?, ?, ?, ?, ?);";

        Object monedas = (usuario instanceof Administrador) ? null : 0;

        DBHelper.manejarEntidad(sql,
                usuario.getNombre_usuario(),
                usuario.getCorreo(),
                usuario.getContraseña(),
                usuario.getTipo_usuario(),
                monedas);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * Si es cliente, también actualiza el campo monedas.
     *
     * @param usuario Objeto Usuario a actualizar.
     */
    public void actualizarUsuario(Usuario usuario) {

        boolean es_admin = usuario instanceof Administrador;

        String sql = es_admin
                ? "UPDATE usuario SET nombre_usuario = ?, correo = ?, contraseña = ?, tipo = ? WHERE id_usuario = ?;"
                : "UPDATE usuario SET nombre_usuario = ?, correo = ?, contraseña = ?, tipo = ?, monedas = ? WHERE id_usuario = ?;";

        if (es_admin) {

            DBHelper.manejarEntidad(sql,
                    usuario.getNombre_usuario(),
                    usuario.getCorreo(),
                    usuario.getContraseña(),
                    usuario.getTipo_usuario(),
                    usuario.getId_usuario());
        } else {

            DBHelper.manejarEntidad(sql,
                    usuario.getNombre_usuario(),
                    usuario.getCorreo(),
                    usuario.getContraseña(),
                    usuario.getTipo_usuario(),
                    ((Cliente) usuario).getMonedas(),
                    usuario.getId_usuario());
        }
    }

    /**
     * Actualiza el valor de monedas de un cliente.
     *
     * @param id_cliente     ID del usuario cliente.
     * @param nuevas_monedas Nuevo valor de monedas.
     */
    public void actualizarMonedas(int id_cliente, int nuevas_monedas) {

        String sql = "UPDATE usuario SET monedas = ? WHERE id_usuario = ?;";

        DBHelper.manejarEntidad(sql,
                nuevas_monedas,
                id_cliente);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id_usuario ID del usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    public Usuario buscarUsuarioPorId(int id_usuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, id_usuario);
    }

    /**
     * Busca un usuario por su correo.
     *
     * @param correo Correo electrónico a buscar.
     * @return Usuario correspondiente o null si no existe.
     */
    public Usuario buscarUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, correo);
    }

    /**
     * Intenta iniciar sesión con correo y contraseña.
     *
     * @param correo     Correo del usuario.
     * @param contrasena Contraseña ingresada.
     * @return Usuario si las credenciales coinciden, null si no.
     */
    public Usuario loguearUsuario(String correo, String contrasena) {

        String sql = "SELECT * FROM usuario WHERE correo = ? AND contraseña = ?;";

        return DBHelper.obtenerEntidad(sql, this::mapearFilaUsuario, correo, contrasena);
    }

    /**
     * Devuelve todos los usuarios registrados en la base de datos.
     * Se utiliza para llenar tablas en interfaces gráficas(TableView).
     *
     * @return Lista observable con usuarios.
     */
    public ObservableList<Usuario> verUsuarios() {

        String sql = "SELECT * FROM usuario;";

        return DBHelper.llenarTabla(sql, rs -> {

            String tipo_usuario = rs.getString("tipo");

            if (tipo_usuario.equals("cliente")) {

                return new Cliente(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        tipo_usuario,
                        rs.getInt("monedas"));
            } else {

                return new Administrador(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        tipo_usuario);
            }
        });
    }

    /**
     * Convierte una fila del ResultSet en un objeto Usuario (Cliente o Administrador).
     *
     * @param rs ResultSet con los datos de la consulta.
     * @return Objeto Usuario correspondiente.
     */
    private Usuario mapearFilaUsuario(ResultSet rs) {

        try {

            String tipo_usuario = rs.getString("tipo");

            if (tipo_usuario.equals("cliente")) {

                return new Cliente(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        rs.getString("tipo"),
                        rs.getInt("monedas"));
            } else {

                return new Administrador(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        rs.getString("tipo"));
            }

        } catch (Exception e) {

            throw new RuntimeException("Error al mapear datos del alquiler desde la Base de Datos: " + e.getMessage());
        }
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public void eliminarUsuario(int id_usuario) {

        String sql = "DELETE FROM usuario WHERE id_usuario = ?;";

        DBHelper.manejarEntidad(sql, id_usuario);
    }
    */

    /*
    FUTURA IMPLEMETACIÓN

    public List<Usuario> buscarUsuariosPorTipo(String tipo_usuario) {

        String sql = "SELECT * FROM usuario WHERE tipo = ?;";

        return DBHelper.obtenerListaEntidad(sql, this::mapearFilaUsuario, tipo_usuario);
    }
    */
}
