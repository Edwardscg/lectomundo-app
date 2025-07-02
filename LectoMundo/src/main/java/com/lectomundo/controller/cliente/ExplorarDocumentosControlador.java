package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.DocumentoService;
import com.lectomundo.model.Documento;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ExplorarDocumentosControlador {

    @FXML
    VBox vboxItems;
    @FXML
    TextField txtBuscar;

    private DocumentoService documentoService = new DocumentoService();
    private ClienteControlador clienteControlador;
    private List<Documento> documentos;

    @FXML
    private void mostrarTodos() {

        try {

            documentos = documentoService.verDocumentos();

            cargarDocumentos(documentos);

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "Ha ocurrido un error y no se pudo buscar los documentos.");
        }
    }

    @FXML
    private void buscarPorTitulo() {

        String busqueda = txtBuscar.getText().trim();

        if (busqueda.isBlank()) {

            UIHelper.mostrarAlerta("Advertencia", "Se debe llenar el campo de búsqueda.");
            return;
        }

        try {

            documentos = documentoService.buscarDocumentosPorTitulo(busqueda);

            if (documentos.isEmpty()) {

                UIHelper.mostrarAlerta("Aviso", "No se encontraron resultados para su búsqueda.");
                return;
            }

            txtBuscar.clear();

            cargarDocumentos(documentos);

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "Ha ocurrido un error y no se pudo buscar los documentos.");
        }
    }

    @FXML
    private void buscarPorAutor() {

        String busqueda = txtBuscar.getText().trim();

        if (busqueda.isBlank()) {

            UIHelper.mostrarAlerta("Advertencia", "Se debe llenar el campo de búsqueda.");
            return;
        }

        try {

            documentos = documentoService.buscarDocumentosPorAutor(busqueda);

            if (documentos.isEmpty()) {

                UIHelper.mostrarAlerta("Aviso", "No se encontraron resultados para su búsqueda.");
                return;
            }

            txtBuscar.clear();

            cargarDocumentos(documentos);

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "Ha ocurrido un error y no se pudo buscar los documentos.");
        }
    }

    @FXML
    private void buscarPorTipo() {

        String busqueda = txtBuscar.getText().trim();

        if (busqueda.isBlank()) {

            UIHelper.mostrarAlerta("Advertencia", "Se debe llenar el campo de búsqueda.");
            return;
        }

        try {

            documentos = documentoService.buscarDocumentoPorTipo(busqueda);

            if (documentos.isEmpty()) {

                UIHelper.mostrarAlerta("Aviso", "No se encontraron resultados para su búsqueda.");
                return;
            }

            txtBuscar.clear();

            cargarDocumentos(documentos);

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "Ha ocurrido un error y no se pudo buscar los documentos.");
        }
    }

    @FXML
    private void buscarPorGenero() {

        String busqueda = txtBuscar.getText().trim();

        if (busqueda.isBlank()) {

            UIHelper.mostrarAlerta("Advertencia", "Se debe llenar el campo de búsqueda.");
            return;
        }

        try {

            documentos = documentoService.buscarDocumentosPorGenero(busqueda);

            if (documentos.isEmpty()) {

                UIHelper.mostrarAlerta("Aviso", "No se encontraron resultados para su búsqueda.");
                return;
            }

            txtBuscar.clear();

            cargarDocumentos(documentos);

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "Ha ocurrido un error y no se pudo buscar los documentos.");
        }
    }

    public void setClienteControlador(ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;
        documentos = documentoService.traerDocumentos();
        cargarDocumentos(documentos);
    }

    private void cargarDocumentos(List<Documento> documentos) {

        try {

            vboxItems.getChildren().clear();

            HBox fila = new HBox(35);
            int contador = 0;

            for (Documento documento : documentos) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/itemDocumento.fxml"));
                Node item = loader.load();

                ItemDocumentoControlador itemDocumentoControlador = loader.getController();
                itemDocumentoControlador.setClienteControlador(clienteControlador);
                itemDocumentoControlador.mostrarDocumento(documento);

                fila.getChildren().add(item);
                contador++;

                if (contador % 3 == 0) {

                    vboxItems.getChildren().add(fila);
                    fila = new HBox(35);
                }
            }

            if (!fila.getChildren().isEmpty()) {

                vboxItems.getChildren().add(fila);
            }

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo cargar los documentos.");
        }
    }
}
