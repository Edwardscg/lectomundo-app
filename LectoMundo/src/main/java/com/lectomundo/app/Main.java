package com.lectomundo.app;

import com.lectomundo.controller.UIHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/general/login.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Lecto Mundo");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){

            UIHelper.mostrarAlerta("Error", "No se pudo cargar la aplicación.");
        }
    }

    public static void main(String[] args){

        launch(args);
    }
}
