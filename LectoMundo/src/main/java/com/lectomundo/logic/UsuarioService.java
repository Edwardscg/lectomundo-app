package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.dao.UsuarioDAO;

public class UsuarioService {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(Usuario usuario) throws Exception{

        if(usuario instanceof Cliente){

            usuario.setTipo_usuario("Cliente");
        }

        if (usuario == null || usuario.getNombre_usuario().isBlank() || usuario.getCorreo().isBlank() || usuario.getContraseña().isBlank()) {

            throw new IllegalArgumentException("Datos del usuario incompletos.");
        }

        Usuario usuario_existente = usuarioDAO.buscarUsuarioPorCorreo(usuario.getCorreo());

        if (usuario_existente != null) {
            throw new Exception("Ya existe un usuario registrado con ese correo.");
        }

        usuarioDAO.registrarUsuario(usuario);
    }
}
