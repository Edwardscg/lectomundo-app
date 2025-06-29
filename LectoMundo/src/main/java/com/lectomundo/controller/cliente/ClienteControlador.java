package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClienteControlador {

    @FXML private StackPane panelContenedor;
    @FXML private Label lblMonedas;
    @FXML private Button btnAlquilados;

    public static Cliente cliente;

    @FXML
    private void initialize(){

        explorarDocumentos();
        actualizarMonedas(cliente.getMonedas());

        try {

            MembresiaService membresiaService = new MembresiaService();
            if(membresiaService.tieneMembresiaActiva(cliente.getId_usuario())){

                btnAlquilados.setVisible(false);
            }

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo comprobar la membresía activa.");
        }
    }

    @FXML
    private void explorarDocumentos(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/explorarDocumentos.fxml"));
            Node contenido = loader.load();

            ExplorarDocumentosControlador explorarDocumentosControlador = loader.getController();
            explorarDocumentosControlador.setClienteControlador(this);

            panelContenedor.getChildren().setAll(contenido);

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo ir a la sección explorar documentos.");
        }
    }

    @FXML
    private void verDocumentosAlquilados(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/documentosAlquilados.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo ir a la sección documentos alquilados.");
        }

    }

    @FXML
    private void verDocumentosComprados(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/documentosComprados.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo ir a la sección documentos comprados.");
        }
    }

    @FXML
    private void verDocumentosFavoritos(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/documentosFavoritos.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo ir a la sección documentos favoritos.");
        }
    }

    @FXML
    private void irATiendaMonedas(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/compraMonedas.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

            CompraMonedasControlador compraMonedasControlador = loader.getController();
            compraMonedasControlador.setClienteControlador(this);

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo ir a la tienda.");
        }
    }

    @FXML
    private void verMembresia(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/membresia.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

            MembresiaControlador membresiaControlador = loader.getController();
            membresiaControlador.setClienteControlador(this);

        } catch (Exception e) {

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo ir a la sección membresía.");
        }
    }

    @FXML
    private void verNotificaciones(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cliente/notificaciones.fxml"));
            Node contenido = loader.load();
            panelContenedor.getChildren().setAll(contenido);

        } catch (Exception e) {

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo ir a la sección notificaciones.");
        }
    }

    @FXML
    private void cerrarSesion(){

        try{

            Stage ventana_actual = (Stage) panelContenedor.getScene().getWindow();

            UIHelper.abrirYCerrarVentanaActual(ventana_actual, "/view/general/login.fxml", "Login");

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "Ocurrió un error y no se pudo cerrar sesión.");
        }
    }

    public void actualizarMonedas(int nuevas_monedas){

        lblMonedas.setText(String.valueOf(nuevas_monedas));
    }

}
