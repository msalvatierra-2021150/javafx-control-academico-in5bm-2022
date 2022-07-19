package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
/**
 *
 * @author W10
 * @date Jul 3, 2022
 * @time 9:26:00 AM
 */
public class acercaDeController {
    private static acercaDeController instancia;
    private final String PAQUETE_VIEWS = "../views/";
    private final String PAQUETE_IMAGES= "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    
    public acercaDeController() throws IOException {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEWS + "AcercaDeView.fxml"));
            Parent root1 = (Parent) cargadorFXML.load();
            Stage stage = new Stage();
            stage.setTitle("AcercaDe");
            Image icon = new Image(PAQUETE_IMAGES+"ICONO.png");
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setScene(new Scene(root1)); 
            stage.show();   
            stage.setOnCloseRequest(event -> {
                instancia = null;
            });
    }
    
    public static acercaDeController getInstance() throws IOException{
        if(instancia == null){
            instancia = new acercaDeController();
        }
        return instancia ;
    }
    
}
