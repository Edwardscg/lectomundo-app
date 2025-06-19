package com.lectomundo.logic;

import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;
import com.lectomundo.model.Favorito;
import com.lectomundo.repository.dao.FavoritoDAO;

import java.util.List;

public class FavoritoService {

    private FavoritoDAO favoritoDAO = new FavoritoDAO();

    public void agregarFavorito(Cliente cliente, Documento documento) throws Exception {

        if (cliente == null || documento == null) {
            throw new IllegalArgumentException("Cliente o documento no válido.");
        }

        if (esFavorito(cliente.getId_usuario(), documento.getId_documento())) {

            return;
        }

        Favorito favorito = new Favorito();
        favorito.setCliente(cliente);
        favorito.setDocumento(documento);

        favoritoDAO.agregarFavorito(cliente.getId_usuario(), documento.getId_documento());
    }

    public void eliminarFavorito(Cliente cliente, Documento documento) throws Exception {

        if (cliente == null || documento == null) {
            throw new IllegalArgumentException("Cliente o documento no válido.");
        }

        favoritoDAO.eliminarFavorito(cliente.getId_usuario(), documento.getId_documento());
    }

    public boolean esFavorito(int id_usuario, int id_documento) throws Exception{

        return favoritoDAO.esFavorito(id_usuario, id_documento);
    }

    public List<Documento> obtenerFavoritosPorUsuario(Cliente cliente) throws Exception {

        return favoritoDAO.obtenerFavoritosPorUsuario(cliente.getId_usuario());
    }
}
