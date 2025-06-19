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

        try{

            if(membresiaService.tieneMembresiaActiva(cliente.getId_usuario())){

                lblEstado.setText("Membresía Activa");
                btnAdquirir.setVisible(false);
                btnExtender.setVisible(true);
                btnFinalizar.setVisible(true);
            }else {

                lblEstado.setText("Sin Membresía");
                btnAdquirir.setVisible(true);
                btnExtender.setVisible(false);
                btnFinalizar.setVisible(false);
            }

        }catch (Exception e){

            e.printStackTrace();
        }
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

    @FXML
    private void finalizarMembresia(){

        try{

            membresiaService.finalizarMembresia(cliente);
            UIHelper.mostrarAlerta("Membresía finalizada", "Tu membresía ha sido cancelada.");
            initialize();

        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo finalizar la membresía.");
        }
    }
}
