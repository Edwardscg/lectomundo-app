package com.lectomundo.controller.general;
import com.lectomundo.controller.UIHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CodigoVerificacionControlador {

    @FXML private TextField txtCodigo;
    @FXML private Label lblCorreo;

    private String codigo_correcto;
    private boolean verificacionHecha = false;

    public void inicializarDatos(String correo, String codigo){

        this.codigo_correcto = codigo;
        lblCorreo.setText("Codigo enviado a: "+ correo);
    }

    @FXML
    private void VerificarCodigo(){

        String ingresado = txtCodigo.getText().trim();

        if(ingresado.isEmpty()){

            UIHelper.mostrarAlerta("Error", "Tiene que ingresar el código.");
            return;
        }

        if(ingresado.equals(codigo_correcto)){

            try{

                verificacionHecha = true;
                UIHelper.mostrarAlerta("Acción exitosa", "Codigo verificado correctamente.");
                Stage ventana_actual = (Stage) txtCodigo.getScene().getWindow();
                UIHelper.cerrarVentana(ventana_actual);

            }catch (Exception e){
                UIHelper.mostrarAlerta("Error", "No se pudo ejecutar la acción.");
            }
        } else{

            UIHelper.mostrarAlerta("Código incorrecto", "Ingrese el código que recibió en su correo.");
        }
    }

    public boolean fueVerificado(){

        return verificacionHecha;
    }
}
