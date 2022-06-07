package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;

/**
 * @date Apr 18, 2022
 * @time 10:20:08 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */

public class MenuPrincipalController implements Initializable{
    @FXML
    private Button btnAyuda;

    @FXML
    private Button btnAlumnos;
    
    @FXML 
    private Button btnInstructores;
    
    @FXML
    private Button btnCarrerasTecnicas;
        
    @FXML
    private Button btnHorarios;

    @FXML
    private Button btnCursos;

    @FXML
    private Button btnSalones;
    
    @FXML
    private Button btnAsignaciones;
    
    private Principal escenarioPrincipal = new Principal();

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clickAlumnos(ActionEvent event) {
        System.out.println("\nAlumnos");
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    private void clickAsignacionesDeAlumno(ActionEvent event) {
        System.out.println("\nAsignaciones");
        escenarioPrincipal.mostrarEscenaAsignaciones();
    }


    @FXML
    private void clickCarrerasTecnicas(ActionEvent event) {
        System.out.println("\nCarreras Tecnicas");
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }

    @FXML
    private void clickCursos(ActionEvent event) {
        System.out.println("\nCursos");
        escenarioPrincipal.mostrarEscenaCursos();
    }

    @FXML
    private void clickHorarios(ActionEvent event) {
        System.out.println("\nHorarios");
        escenarioPrincipal.mostrarEscenaHorarios();
    }

    @FXML
    private void clickInstructores(ActionEvent event) {
        System.out.println("\nInstructores");
        escenarioPrincipal.mostrarEscenaInstructores();
    }

    @FXML
    private void clickSalones(ActionEvent event) {
        System.out.println("\nSalones");
        escenarioPrincipal.mostrarEscenaSalones();
    }
    
    @FXML
    private void clickAyuda(ActionEvent event) {
        System.out.println("\nAyuda");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}
