package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class VisorPDFControlador implements Initializable {

    @FXML
    private VBox contenedorPaginas;

    private String pdfURL;

    public void setPdfURL(String pdfURL){

        this.pdfURL = pdfURL;
    }

    @Override
    public void initialize(URL ubicacion, ResourceBundle recursos){

    }

    public void cargarPDF(){

        try{

            File archivoTemporal = File.createTempFile("documento_", ".pdf");
            try (InputStream in = new URL(pdfURL).openStream();
                 FileOutputStream out = new FileOutputStream(archivoTemporal)) {

                byte[] buffer = new byte[4096];
                int bytesLeidos;
                while ((bytesLeidos = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesLeidos);
                }
            }

            try (PDDocument documento = PDDocument.load(archivoTemporal)) {
                PDFRenderer renderer = new PDFRenderer(documento);

                for (int i = 0; i < documento.getNumberOfPages(); i++) {
                    BufferedImage imagenPagina = renderer.renderImageWithDPI(i, 150); // buena calidad
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(imagenPagina, "png", os);
                    ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

                    Image imagen = new Image(is);
                    ImageView imageView = new ImageView(imagen);
                    imageView.setFitWidth(600); // ajusta el ancho si lo deseas
                    imageView.setPreserveRatio(true);

                    contenedorPaginas.getChildren().add(imageView);
                }
            }

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo cargar el PDF.");
        }

    }
}
