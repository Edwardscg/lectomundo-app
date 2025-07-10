package com.lectomundo.logic;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.model.Administrador;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.dao.UsuarioDAO;

import javafx.collections.ObservableList;

/**
 * Servicio de negocio que gestiona operaciones relacionadas con usuarios:
 * registro, login, actualización de contraseña y monedas.
 * <p>NOTA: mezcla actualmente lógica de negocio con interacción UI, se recomienda refactorizar.</p>
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Registra un nuevo usuario tras verificar que el correo no exista
     * y completar el proceso de validación por correo.
     *
     * @param nombre_usuario Nombre de usuario
     * @param correo         Correo electrónico
     * @param contraseña     Contraseña
     * @param tipo_usuario   "cliente" o "administrador"
     */
    public void registrarUsuario(String nombre_usuario, String correo, String contraseña, String tipo_usuario) {

        if (buscarUsuarioPorCorreo(correo) != null) {

            throw new RuntimeException("Ya existe un usuario con el correo ingresado.");
        }

        Usuario usuario;

        if (tipo_usuario.equals("cliente")) {

            String codigo = CorreoService.generarCodigoDeVerificacion();
            CorreoService.enviarCodigoPorCorreo(correo, codigo);

            boolean verificado = UIHelper.abrirVentanaDeVerificacion(correo, codigo);
            if(!verificado) return;

            usuario = new Cliente(0, nombre_usuario, correo, contraseña, tipo_usuario, 0);

        } else {

            usuario = new Administrador(0, nombre_usuario, correo, contraseña, tipo_usuario);
        }

        usuarioDAO.registrarUsuario(usuario);
    }

    /**
     * Inicia sesión del usuario tras verificación por correo.
     *
     * @param correo     Correo electrónico
     * @param contraseña Contraseña
     * @return Usuario autenticado
     */
    public Usuario loguearUsuario(String correo, String contraseña) {

        Usuario usuario = usuarioDAO.loguearUsuario(correo, contraseña);

        if (usuario == null) {

            throw new IllegalArgumentException("Correo o contraseña incorrecta.");
        }

        return usuario;
    }

    /**
     * Actualiza la contraseña de un usuario identificado por su correo.
     *
     * @param correo           Correo del usuario
     * @param nueva_contraseña Nueva contraseña
     * @return true si se actualizó correctamente; false si el usuario no existe o la entrada es inválida
     */
    public boolean actualizarContraseña(String correo, String nueva_contraseña) {


        if (correo.isBlank() || nueva_contraseña.isBlank()) {

            return false;
        }

        Usuario usuario = usuarioDAO.buscarUsuarioPorCorreo(correo);


        if (usuario == null) return false;

        usuario.setContraseña(nueva_contraseña);
        usuarioDAO.actualizarUsuario(usuario);

        return true;

    }

    /**
     * Actualiza la cantidad de monedas de un cliente.
     *
     * @param id_cliente ID del cliente
     * @param nuevas_monedas Nueva cantidad de monedas
     */
    public void actualizarMonedas(int id_cliente, int nuevas_monedas) {

        usuarioDAO.actualizarMonedas(id_cliente, nuevas_monedas);
    }

    /**
     * Busca un usuario por correo.
     *
     * @param correo Correo electrónico
     * @return Usuario si existe, null si no
     */
    public Usuario buscarUsuarioPorCorreo(String correo) {

        return usuarioDAO.buscarUsuarioPorCorreo(correo);
    }

    /**
     * Devuelve una lista observable con todos los usuarios registrados.
     *
     * @return Lista de usuarios
     */
    public ObservableList<Usuario> verUsuarios() {

        return usuarioDAO.verUsuarios();
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public Usuario buscarUsuarioPorId(int id_usuario) {

        return usuarioDAO.buscarUsuarioPorId(id_usuario);
    }
    */

    /*
    FUTURA IMPLEMENTACIÓN

    public void eliminarUsuario(int id_usuario) {

        usuarioDAO.eliminarUsuario(id_usuario);
    }
    */

    /*
    FUTURA IMPLEMENTACIÓN


    public void actualizarUsuario(Usuario usuario) {

        usuarioDAO.actualizarUsuario(usuario);

    }
    */

}
