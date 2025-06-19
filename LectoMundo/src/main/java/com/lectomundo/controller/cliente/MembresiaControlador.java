package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Cliente;
import javafx.fxml.FXML;

import java.awt.*;

public class MembresiaControlador {

    @FXML private Label lblEstado;
    @FXML private Button btnAdquirir;
    @FXML private Button btnExtender;
    @FXML private Button btnFinalizar;

    private MembresiaService membresiaService = new MembresiaService();
    private Cliente cliente = ClienteControlador.cliente;

    @FXML
    private void initialize(){


    }

    @FXML
    private void adquirirMembresia(){

        try{

            membresiaService.registrarMembresia(cliente);
            UIHelper.mostrarAlerta("Membresia Activada", "Ahora tienes acceso ilimitado por 30 días.");
            initialize();

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo adquirir la membresia.");
        }
    }

    @FXML
    private void extenderMembresia(){

        try{

            membresiaService.actualizarMembresia(cliente);
            UIHelper.mostrarAlerta("Membresía Extendedia", "Se ha extendido la membresía por 30 días más.");
            initialize();

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo extender la membresía.");
        }
    }
}
