package com.lectomundo.controller.cliente;

import com.lectomundo.controller.UIHelper;
import com.lectomundo.logic.MovimientoMonedaService;
import com.lectomundo.model.Cliente;
import javafx.fxml.FXML;

public class CompraMonedasControlador {

    private MovimientoMonedaService movimientoMonedaService = new MovimientoMonedaService();
    private Cliente cliente = ClienteControlador.cliente;

    @FXML
    private void comprar60Monedas(){

        comprarMonedas(60);
    }

    @FXML
    private void comprar300Monedas(){

        comprarMonedas(300);
    }

    @FXML
    private void comprar980Monedas(){

        comprarMonedas(980);
    }

    @FXML
    private void comprar1980Monedas(){

        comprarMonedas(1980);
    }

    @FXML
    private void comprar3280Monedas(){

        comprarMonedas(3280);
    }

    @FXML
    private void comprar6480Monedas(){

        comprarMonedas(6480);
    }

    private void comprarMonedas(int monto){

        try{

            movimientoMonedaService.comprarMonedas(cliente, monto);
            UIHelper.mostrarAlerta("Compra exitosa", "Se han comprado " + monto + " monedas.");

        }catch (Exception e){

            e.printStackTrace();
            UIHelper.mostrarAlerta("Error", "No se pudo completar la compra.");
        }
    }
}
