package com.lectomundo.controller.cliente;
import com.lectomundo.controller.UIHelper;

import javafx.fxml.FXML;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.embed.swing.SwingFXUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisorPDFControlador {

    @FXML private ScrollPane scrollPane;
    @FXML private VBox pdfContenedor;
    @FXML private ProgressIndicator indicador_carga;

    private PDDocument pdfDocument;
    private PDFRenderer pdfRenderer;
    private ExecutorService executorService;
    private int pagina_actual = 0;
    private boolean esta_cargando = false;
    private final int num_cargar_paginas = 3;

    public void initialize() {
        executorService = Executors.newFixedThreadPool(2);
        scrollPane.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0.9 && !esta_cargando &&
                    pagina_actual < pdfDocument.getNumberOfPages()) {
                cargarPaginas();
            }
        });
    }

    public void setPdfUrl(String url) {
        indicador_carga.setVisible(true);

        executorService.execute(() -> {
            try {
                URL pdfUrl = new URL(url);
                InputStream inputStream = pdfUrl.openStream();
                pdfDocument = PDDocument.load(inputStream);
                pdfRenderer = new PDFRenderer(pdfDocument);

                javafx.application.Platform.runLater(() -> {
                    indicador_carga.setVisible(false);
                    cargarPaginas();
                });
            } catch (Exception e) {

                javafx.application.Platform.runLater(() -> {
                    indicador_carga.setVisible(false);
                    UIHelper.mostrarAlerta("Error", "No se pudo cargar el documento. Verifique su conexión.");

                });
            }
        });
    }

    private void cargarPaginas() {
        if (esta_cargando || pagina_actual >= pdfDocument.getNumberOfPages()) return;

        esta_cargando = true;
        indicador_carga.setVisible(true);

        executorService.execute(() -> {
            try {

                int ultima_pagina = Math.min(pagina_actual + num_cargar_paginas,
                        pdfDocument.getNumberOfPages());

                for (int i = pagina_actual; i < ultima_pagina; i++) {

                    int numero_pagina = i;

                    BufferedImage imagen = pdfRenderer.renderImage(numero_pagina, 1.0f);
                    Image imagen_fx = SwingFXUtils.toFXImage(imagen, null);

                    javafx.application.Platform.runLater(() -> {
                        ImageView pageView = new ImageView(imagen_fx);
                        pageView.setPreserveRatio(true);
                        pageView.setFitWidth(scrollPane.getWidth() - 20);
                        pdfContenedor.getChildren().add(pageView);
                    });
                }

                pagina_actual = ultima_pagina;

                javafx.application.Platform.runLater(() -> {
                    indicador_carga.setVisible(false);
                    esta_cargando = false;
                });

            } catch (Exception e) {

                javafx.application.Platform.runLater(() -> {
                    indicador_carga.setVisible(false);
                    esta_cargando = false;
                });
            }
        });
    }

    public void liberarRecursos() {
        if (executorService != null) {
            executorService.shutdown();
        }
        if (pdfDocument != null) {
            try {
                pdfDocument.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}