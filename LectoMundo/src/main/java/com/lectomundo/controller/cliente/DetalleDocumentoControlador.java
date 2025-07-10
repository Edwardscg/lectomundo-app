package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.AlquilerService;
import com.lectomundo.logic.CompraDocumentoService;
import com.lectomundo.logic.FavoritoService;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Cliente;
import com.lectomundo.model.Documento;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

public class DetalleDocumentoControlador {

    @FXML
    private ImageView imgPortada;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblGenero;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Button btnAlquilar;
    @FXML
    private Button btnComprar;
    @FXML
    private Button btnLeer;
    @FXML
    private Button btnDevolver;
    @FXML
    private Button btnDescargar;
    @FXML
    private Button btnFavoritoVacio;
    @FXML
    private Button btnFavoritoLleno;
    @FXML
    private Button btnValorar;

    private ClienteControlador clienteControlador;
    private Documento documento;
    private Cliente cliente = ClienteControlador.cliente;
    private MembresiaService membresiaService = new MembresiaService();
    private CompraDocumentoService compraDocumentoService = new CompraDocumentoService();
    private AlquilerService alquilerService = new AlquilerService();
    private FavoritoService favoritoService = new FavoritoService();
    private boolean tiene_membresia = false;
    private boolean esta_comprado = false;
    private boolean esta_alquilado = false;
    private boolean es_favorito = false;

    @FXML
    private void alquilarDocumento() {

        try {

            cliente = alquilerService.registrarAlquiler(cliente, documento);
            clienteControlador.actualizarMonedas(cliente.getMonedas());

            actualizarBotones();

            UIHelper.mostrarAlerta("Éxito", "Documento alquilado exitosamente.");

        } catch (RuntimeException e) {

            UIHelper.mostrarAlerta("Advertencia", e.getMessage());
        } catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo alquilar el documento.");
        }
    }

    @FXML
    private void devolverDocumento() {

        try {

            alquilerService.devolverDocumento(cliente, documento);
            actualizarBotones();

            UIHelper.mostrarAlerta("Éxito", "Documento devuelto exitosamente.");

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo devolver el documento.");
        }
    }

    @FXML
    private void comprarDocumento() {

        try {

            cliente = compraDocumentoService.registrarCompra(cliente, documento);
            clienteControlador.actualizarMonedas(cliente.getMonedas());

            UIHelper.mostrarAlerta("Éxito", "Documento comprado exitosamente.");

            actualizarBotones();

        } catch (RuntimeException e) {

            UIHelper.mostrarAlerta("Error", e.getMessage());
        } catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo comprar el documento.");
        }
    }

    @FXML
    private void leerDocumento() {

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/visorPDF.fxml"));
            Parent root = loader.load();

            VisorPDFControlador controlador = loader.getController();
            controlador.setPdfUrl(documento.getPdf_url());

            Stage stage = new Stage();
            stage.setTitle("Lectura del Documento");
            stage.setScene(new Scene(root));

            stage.setOnCloseRequest(evento -> controlador.liberarRecursos());

            stage.show();

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo abrir el documento.");
        }
    }

    @FXML
    private void descargarDocumento() {

        try {

            String url = documento.getPdf_url();
            System.out.println(url);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Descargar Documento");
            fileChooser.setInitialFileName(documento.getTitulo().replaceAll("\\s+", "_") + ".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            File destino = fileChooser.showSaveDialog(btnDescargar.getScene().getWindow());

            if (destino != null) {

                try (InputStream inputStream = new URL(url).openStream();
                     OutputStream outputStream = new FileOutputStream(destino)) {

                    byte[] buffer = new byte[4096];
                    int bytes_read;

                    while ((bytes_read = inputStream.read(buffer)) != -1) {

                        outputStream.write(buffer, 0, bytes_read);

                    }
                    UIHelper.mostrarAlerta("Éxito", "El documento se ha descargado.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "No se pudo procesar la descarga del documento.");
        }
    }

    @FXML
    private void marcarFavorito() {

        try {

            favoritoService.agregarFavorito(cliente, documento);
            btnFavoritoVacio.setVisible(false);
            btnFavoritoVacio.setManaged(false);
            btnFavoritoLleno.setVisible(true);
            btnFavoritoLleno.setManaged(true);

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo marcar como favorito.");
        }
    }

    @FXML
    private void desmarcarFavorito() {

        try {

            favoritoService.eliminarFavorito(cliente, documento);

            btnFavoritoVacio.setVisible(true);
            btnFavoritoVacio.setManaged(true);
            btnFavoritoLleno.setVisible(false);
            btnFavoritoLleno.setManaged(false);

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo quitar de favoritos.");
        }
    }

    @FXML
    private void irAValorar() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/valoracionDocumento.fxml"));
            Parent root = loader.load();

            ValoracionDocumentoControlador valoracionDocumentoControlador = loader.getController();
            valoracionDocumentoControlador.cargarDatos(cliente, documento);

            Stage ventana_valorar = new Stage();
            ventana_valorar.setScene(new Scene(root));

            Stage ventana_detalle = (Stage) ((Node) imgPortada).getScene().getWindow();
            ventana_detalle.setOpacity(0);

            ventana_valorar.setOnHidden(e -> ventana_detalle.setOpacity(1));

            ventana_valorar.setResizable(false);
            ventana_valorar.show();

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo abrir la ventana de valoración.");
        }
    }

    public void setClienteControlador(ClienteControlador clienteControlador) {

        this.clienteControlador = clienteControlador;
    }

    public void cargarDatos(Documento documento) {

        try {

            this.documento = documento;

            imgPortada.setImage(new Image(documento.getPortada_url()));
            lblTitulo.setText(documento.getTitulo());
            lblAutor.setText("Autor: " + documento.getAutor());
            lblGenero.setText("Género: " + documento.getGenero());
            lblDescripcion.setText("Descripción: " + documento.getDescripcion());

            es_favorito = favoritoService.esFavorito(cliente.getId_usuario(), documento.getId_documento());

            btnFavoritoLleno.setVisible(es_favorito);
            btnFavoritoLleno.setManaged(es_favorito);

            btnFavoritoVacio.setVisible(!es_favorito);
            btnFavoritoVacio.setManaged(!es_favorito);

            btnValorar.setText("Valorar \u2605 " + documento.getPuntuacion_promedio());

            actualizarBotones();

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo cargar los datos del documento.");
        }
    }

    private void actualizarBotones() {

        tiene_membresia = membresiaService.tieneMembresiaActiva(cliente.getId_usuario());
        esta_comprado = compraDocumentoService.estaComprado(cliente.getId_usuario(), documento.getId_documento());
        esta_alquilado = alquilerService.estaAlquilado(cliente.getId_usuario(), documento.getId_documento());

        btnAlquilar.setVisible(false);
        btnAlquilar.setManaged(false);

        btnComprar.setVisible(false);
        btnComprar.setManaged(false);

        btnLeer.setVisible(false);
        btnLeer.setManaged(false);

        btnDevolver.setVisible(false);
        btnDevolver.setManaged(false);

        btnDescargar.setVisible(false);
        btnDescargar.setManaged(false);

        if (tiene_membresia) {

            btnLeer.setVisible(true);
            btnLeer.setManaged(true);

            if (esta_comprado) {

                btnDescargar.setVisible(true);
                btnDescargar.setManaged(true);
            } else {

                btnComprar.setVisible(true);
                btnComprar.setManaged(true);

                btnComprar.setText("Comprar \uD83D\uDCB0 " + documento.getPrecio() * 3);
            }

        } else if (esta_comprado) {

            btnLeer.setVisible(true);
            btnLeer.setManaged(true);

            btnDescargar.setVisible(true);
            btnDescargar.setManaged(true);

        } else if (esta_alquilado) {

            btnComprar.setVisible(true);
            btnComprar.setManaged(true);

            btnComprar.setText("Comprar \uD83D\uDCB0 " + documento.getPrecio() * 3);

            btnLeer.setVisible(true);
            btnLeer.setManaged(true);

            btnDevolver.setVisible(true);
            btnDevolver.setManaged(true);

        } else {

            btnAlquilar.setVisible(true);
            btnAlquilar.setManaged(true);

            btnAlquilar.setText("Alquilar \uD83D\uDCB0 "+ documento.getPrecio());

            btnComprar.setVisible(true);
            btnComprar.setManaged(true);

            btnComprar.setText("Comprar \uD83D\uDCB0 " + documento.getPrecio() * 3);
        }
    }
}
