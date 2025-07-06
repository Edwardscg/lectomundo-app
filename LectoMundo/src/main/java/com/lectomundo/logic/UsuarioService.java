package com.lectomundo.logic;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.model.Administrador;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.dao.UsuarioDAO;

import javafx.collections.ObservableList;

public class UsuarioService {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(String nombre_usuario, String correo, String contraseña, String tipo_usuario) {

        if (buscarUsuarioPorCorreo(correo) != null) {

            throw new RuntimeException("Ya existe un usuario con el correo ingresado.");
        }

        String codigo = CorreoService.generarCodigoDeVerificacion();
        CorreoService.enviarCodigoPorCorreo(correo, codigo);

        boolean verificado = UIHelper.abrirVentanaDeVerificacion(correo, codigo);

        if (!verificado) {

            throw new IllegalArgumentException("No se completó la verificación");
        }

        Usuario usuario;

        if (tipo_usuario.equals("cliente")) {

            usuario = new Cliente(0, nombre_usuario, correo, contraseña, tipo_usuario, 0);
        } else {

            usuario = new Administrador(0, nombre_usuario, correo, contraseña, tipo_usuario);
        }

        usuarioDAO.registrarUsuario(usuario);
    }

    public Usuario loguearUsuario(String correo, String contraseña) {

        Usuario usuario = usuarioDAO.loguearUsuario(correo, contraseña);

        if (usuario == null) {

            throw new IllegalArgumentException("Correo o contraseña incorrecta.");
        }

        String codigo = CorreoService.generarCodigoDeVerificacion();
        CorreoService.enviarCodigoPorCorreo(correo, codigo);

        boolean verificado = UIHelper.abrirVentanaDeVerificacion(correo, codigo);

        if (!verificado) {

            throw new IllegalArgumentException("Código incorrecto");
        }

        return usuario;
    }

    public boolean actualizarContraseña(String correo, String nueva_contraseña) {


        if (correo == null || correo.isBlank() || nueva_contraseña == null || nueva_contraseña.isBlank()) {

            return false;
        }

        Usuario usuario = usuarioDAO.buscarUsuarioPorCorreo(correo);

        if (usuario == null) {

            return false;
        } else {

            usuario.setContraseña(nueva_contraseña);
            usuarioDAO.actualizarUsuario(usuario);

            return true;
        }
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public void actualizarUsuario(Usuario usuario) {

        usuarioDAO.actualizarUsuario(usuario);
    }
    */


    public void actualizarMonedas(int id_cliente, int nuevas_monedas) {

        usuarioDAO.actualizarMonedas(id_cliente, nuevas_monedas);
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public Usuario buscarUsuarioPorId(int id_usuario) {

        return usuarioDAO.buscarUsuarioPorId(id_usuario);
    }
    */

    public Usuario buscarUsuarioPorCorreo(String correo) {

        return usuarioDAO.buscarUsuarioPorCorreo(correo);
    }

    /*
    FUTURA IMPLEMENTACIÓN

    public void eliminarUsuario(int id_usuario) {

        usuarioDAO.eliminarUsuario(id_usuario);
    }
    */

    public ObservableList<Usuario> verUsuarios() {

        return usuarioDAO.verUsuarios();
    }
}
