package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.MembresiaService;
import com.lectomundo.model.Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class MembresiaControlador {

    @FXML
    private Label lblEstado;
    @FXML
    private Button btnAdquirir;
    @FXML
    private Button btnExtender;
    @FXML
    private Button btnFinalizar;

    private MembresiaService membresiaService = new MembresiaService();
    private Cliente cliente = ClienteControlador.cliente;
    private ClienteControlador clienteControlador;

    @FXML
    private void initialize() {

        actualizarVistaMembresia();
    }

    @FXML
    private void adquirirMembresia() {

        try {

            cliente = membresiaService.registrarMembresia(cliente);
            clienteControlador.actualizarMonedas(cliente.getMonedas());
            UIHelper.mostrarAlerta("Membresia Activada", "Ahora tienes acceso ilimitado por 30 días.");
            initialize();

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo adquirir la membresia.");
        }
    }

    @FXML
    private void extenderMembresia() {

        try {

            cliente = membresiaService.actualizarMembresia(cliente);
            clienteControlador.actualizarMonedas(cliente.getMonedas());
            UIHelper.mostrarAlerta("Membresía Extendedia", "Se ha extendido la membresía por 30 días más.");
            initialize();

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo extender la membresía.");
        }
    }

    @FXML
    private void finalizarMembresia() {

        try {

            membresiaService.finalizarMembresia(cliente);
            UIHelper.mostrarAlerta("Membresía finalizada", "Tu membresía ha sido cancelada.");
            initialize();

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo finalizar la membresía.");
        }
    }

    public void setClienteControlador(ClienteControlador clienteControlador) {

        this.clienteControlador = clienteControlador;
    }

    public void actualizarVistaMembresia() {

        try {

            if (membresiaService.tieneMembresiaActiva(cliente.getId_usuario())) {

                lblEstado.setText("Membresía Activa");
                btnAdquirir.setVisible(false);
                btnExtender.setVisible(true);
                btnFinalizar.setVisible(true);
            } else {

                lblEstado.setText("Sin Membresía");
                btnAdquirir.setVisible(true);
                btnExtender.setVisible(false);
                btnFinalizar.setVisible(false);
            }

        } catch (Exception e) {

            UIHelper.mostrarAlerta("Error", "No se pudo actualizar la vista de membresía.");
        }
    }
}
