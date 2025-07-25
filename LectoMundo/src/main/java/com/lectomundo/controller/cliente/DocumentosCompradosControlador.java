package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.CompraDocumentoService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class DocumentosCompradosControlador {

    @FXML
    VBox vboxContenedor;

    private CompraDocumentoService compraDocumentoService = new CompraDocumentoService();
    private Cliente cliente = ClienteControlador.cliente;

    @FXML
    private void initialize() {

        cargarDocumentosComprados();
    }

    private void cargarDocumentosComprados() {

        try {

            List<Documento> documentosComprados = compraDocumentoService.verDocumentosCompradosPorCliente(cliente);

            HBox fila = new HBox(35);
            int contador = 0;

            for (Documento documento : documentosComprados) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/itemDocumento.fxml"));
                Node item = loader.load();

                ItemDocumentoControlador itemDocumentoControlador = loader.getController();
                itemDocumentoControlador.mostrarDocumento(documento);

                fila.getChildren().add(item);
                contador++;

                if (contador % 3 == 0) {

                    vboxContenedor.getChildren().add(fila);
                    fila = new HBox(35);
                }
            }

            if (!fila.getChildren().isEmpty()) {

                vboxContenedor.getChildren().add(fila);
            }

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo cargar los documentos comprados.");
        }
    }
}
