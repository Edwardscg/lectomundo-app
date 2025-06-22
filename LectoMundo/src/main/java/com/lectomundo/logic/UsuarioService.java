package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.dao.UsuarioDAO;
import javafx.collections.ObservableList;

import java.util.List;

public class UsuarioService {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(Usuario usuario) {

        if (usuario instanceof Cliente) {

            usuario.setTipo_usuario("cliente");
        }

        Usuario usuario_existente = usuarioDAO.buscarUsuarioPorCorreo(usuario.getCorreo());

        if (usuario_existente != null) {

            throw new RuntimeException("Ya existe un usuario registrado con ese correo.");
        }
        usuarioDAO.registrarUsuario(usuario);
    }

    public Usuario loguearUsuario(String correo, String contraseña) {

        Usuario usuario = usuarioDAO.loguearUsuario(correo, contraseña);

        if (usuario != null) {

            return usuario;
        }
        return null;
    }

    public boolean actualizarContraseña(String correo, String nueva_contraseña) {


        if (correo == null || correo.isBlank() || nueva_contraseña == null || nueva_contraseña.isBlank()) {

            return false;
        }

        Usuario usuario = usuarioDAO.buscarUsuarioPorCorreo(correo);

        if (usuario == null) {

            return false;
        }else {

            usuario.setContraseña(nueva_contraseña);
            usuarioDAO.actualizarUsuario(usuario);

            return true;
        }
    }

    public void actualizarUsuario(Usuario usuario) {

        usuarioDAO.actualizarUsuario(usuario);
    }

    public void actualizarMonedas(int id_usuario, int nuevas_monedas) {

        usuarioDAO.actualizarMonedas(id_usuario, nuevas_monedas);
    }

    // POSIBLE BORRADO, RAZON: SIN USO
    public Usuario buscarUsuarioPorId(int id_usuario) {

        return usuarioDAO.buscarUsuarioPorId(id_usuario);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {

        return usuarioDAO.buscarUsuarioPorCorreo(correo);
    }


    // CAMBIAR A QUE RETORNE UN OBSERVABLE LIST Y ACTUALIZAR UML
    public List<Usuario> buscarUsuarioPorTipo(String tipo_usuario) {

        return usuarioDAO.buscarUsuariosPorTipo(tipo_usuario);
    }

    public void eliminarUsuario(int id_usuario) {

        usuarioDAO.eliminarUsuario(id_usuario);
    }

    public ObservableList<Usuario> verUsuarios() {

        return usuarioDAO.verUsuarios();
    }
}
