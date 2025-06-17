package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Usuario;
import com.lectomundo.repository.dao.UsuarioDAO;
import javafx.collections.ObservableList;

import java.util.List;

public class UsuarioService {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(Usuario usuario) throws Exception {

        if (usuario instanceof Cliente) {

            usuario.setTipo_usuario("cliente");
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

    public Usuario loguearUsuario(String correo, String contraseña) throws Exception {

        if (correo == null || contraseña == null || correo.isBlank() || contraseña.isBlank()) {

            throw new IllegalArgumentException("Correo o contraseña inválidos.");
        }

        Usuario usuario = usuarioDAO.loguearUsuario(correo, contraseña);

        if (usuario == null) {

            throw new Exception("Credenciales incorrectas.");
        }
        return usuario;
    }

    public boolean actualizarContraseña(String correo, String nueva_contraseña) throws Exception {


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

    public void actualizarUsuario(Usuario usuario) throws Exception{

        if (usuario == null || usuario.getCorreo().isBlank() || usuario.getNombre_usuario().isBlank()) {

            throw new IllegalArgumentException("Datos inválidos.");
        }

        usuarioDAO.actualizarUsuario(usuario);
    }

    public void actualizarMonedas(int id_usuario, int nuevas_monedas) throws Exception{

        usuarioDAO.actualizarMonedas(id_usuario, nuevas_monedas);
    }

    // POSIBLE BORRADO, RAZON: SIN USO
    public Usuario buscarUsuarioPorId(int id_usuario) throws Exception {

        if (id_usuario <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        return usuarioDAO.buscarUsuarioPorId(id_usuario);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) throws Exception{

        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("Correo inválido.");
        }

        return usuarioDAO.buscarUsuarioPorCorreo(correo);
    }


    // CAMBIAR A QUE RETORNE UN OBSERVABLE LIST Y ACTUALIZAR UML
    public List<Usuario> buscarUsuarioPorTipo(String tipo_usuario) throws Exception{

        if(tipo_usuario == null || tipo_usuario.isBlank()){

            throw new Exception("Tipo de usuario no existente.");
        }

        return usuarioDAO.buscarUsuariosPorTipo(tipo_usuario);
    }

    public void eliminarUsuario(int id_usuario) throws Exception {

        if (id_usuario <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        usuarioDAO.eliminarUsuario(id_usuario);
    }

    public ObservableList<Usuario> verUsuarios() throws Exception{

        return usuarioDAO.verUsuarios();
    }
}
