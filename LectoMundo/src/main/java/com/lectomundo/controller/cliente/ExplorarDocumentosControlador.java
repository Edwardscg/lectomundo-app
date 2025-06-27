package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.DocumentoService;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Documento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ExplorarDocumentosControlador {

    @FXML
    VBox vboxContenedor;

    private DocumentoService documentoService = new DocumentoService();

    @FXML
    private void initialize() {

        cargarDocumentos();
    }

    private void cargarDocumentos() {
        try {

            List<Documento> documentos = documentoService.traerDocumentos();

            HBox fila = new HBox(20);
            int contador = 0;

            for (Documento documento : documentos) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/itemDocumento.fxml"));
                Node item = loader.load();

                ItemDocumentoControlador itemDocumentoControlador = loader.getController();
                itemDocumentoControlador.mostrarDocumento(documento);

                fila.getChildren().add(item);
                contador++;

                if (contador % 3 == 0) {

                    vboxContenedor.getChildren().add(fila);
                    fila = new HBox(20);
                }
            }

            if (!fila.getChildren().isEmpty()) {

                vboxContenedor.getChildren().add(fila);
            }

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se puedo cargar los documentos.");
        }
    }
}
