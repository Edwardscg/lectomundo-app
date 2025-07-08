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

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisorPDFControlador {

    @FXML private ScrollPane scrollPane;
    @FXML private VBox pdfContainer;
    @FXML private ProgressIndicator loadingIndicator;

    private PDDocument pdfDocument;
    private PDFRenderer pdfRenderer;
    private ExecutorService executorService;
    private int currentPage = 0;
    private boolean isLoading = false;
    private final int PAGE_BATCH_SIZE = 3;

    public void initialize() {
        executorService = Executors.newFixedThreadPool(2);
        scrollPane.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0.9 && !isLoading &&
                    currentPage < pdfDocument.getNumberOfPages()) {
                loadNextPages();
            }
        });
    }

    public void setPdfUrl(String url) {
        loadingIndicator.setVisible(true);

        executorService.execute(() -> {
            try {
                URL pdfUrl = new URL(url);
                InputStream inputStream = pdfUrl.openStream();
                pdfDocument = PDDocument.load(inputStream);
                pdfRenderer = new PDFRenderer(pdfDocument);

                javafx.application.Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    loadNextPages();
                });
            } catch (Exception e) {

                javafx.application.Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    UIHelper.mostrarAlerta("Error", "No se pudo cargar el documento. Verifique su conexión.");

                });
            }
        });
    }

    private void loadNextPages() {
        if (isLoading || currentPage >= pdfDocument.getNumberOfPages()) return;

        isLoading = true;
        loadingIndicator.setVisible(true);

        executorService.execute(() -> {
            try {
                int endPage = Math.min(currentPage + PAGE_BATCH_SIZE,
                        pdfDocument.getNumberOfPages());

                for (int i = currentPage; i < endPage; i++) {
                    final int pageNum = i;
                    java.awt.image.BufferedImage awtImage = pdfRenderer.renderImage(pageNum, 1.0f);
                    Image fxImage = SwingFXUtils.toFXImage(awtImage, null);

                    javafx.application.Platform.runLater(() -> {
                        ImageView pageView = new ImageView(fxImage);
                        pageView.setPreserveRatio(true);
                        pageView.setFitWidth(scrollPane.getWidth() - 20);
                        pdfContainer.getChildren().add(pageView);
                    });
                }

                currentPage = endPage;

                javafx.application.Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    isLoading = false;
                });
            } catch (Exception e) {

                javafx.application.Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    isLoading = false;
                });
            }
        });
    }

    public void cleanup() {
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