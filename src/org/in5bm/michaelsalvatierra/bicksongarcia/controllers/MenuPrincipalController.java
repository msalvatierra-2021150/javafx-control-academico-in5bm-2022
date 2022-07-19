package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bm.michaelsalvatierra.bicksongarcia.reports.GenerarReporte;
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
    
    @FXML
    private MenuBar mnbAyuda;
    
    
    private final String PAQUETE_VIEWS = "../views/";
    private final String PAQUETE_IMAGES= "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    
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
    private void clickAcercaDe(ActionEvent event) throws IOException {
        acercaDeController.getInstance();
    }
    
    @FXML
    private void clickSalir(){
        System.exit(0);
    }
    
    @FXML
    private void clickReporteAlumnos(){
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"alumnos-module.png");
        GenerarReporte.getInstance().mostrarReporte("Alumnos.jasper", parametros, "Reporte de Alumnos");
    }
    
     @FXML
    private void clickReporteInstructores(){
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"instructores-module.png");
        GenerarReporte.getInstance().mostrarReporte("Instructores.jasper", parametros, "Reporte de Instructores");
    }
     @FXML
    private void clickReporteCarrerasTecnicas(){
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"carreras tecnicas-module.png");
        GenerarReporte.getInstance().mostrarReporte("CarrerasTecnicas.jasper", parametros, "Reporte de Horarios");
    }
    
     @FXML
    private void clickReporteHorarios(){
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"horarios-module.png");
        GenerarReporte.getInstance().mostrarReporte("Horarios.jasper", parametros, "Reporte de Horarios");
    }
    
     @FXML
    private void clickReporteCursos(){
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"cursos-module-removebg-preview.png");
        GenerarReporte.getInstance().mostrarReporte("Cursos.jasper", parametros, "Reporte de Cursos");   
    }
    
     @FXML
    private void clickReporteSalones(){
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"salones-module.png");
        GenerarReporte.getInstance().mostrarReporte("Salones.jasper", parametros, "Reporte de Salones");        
    }
    
     @FXML
    private void clickReporteAsignacionesAlumnos(){
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"asignacion alumnos-module.png");
        GenerarReporte.getInstance().mostrarReporte("AsignacionAlumnos.jasper", parametros, "Reporte de Asigancion de Alumnos");
    }
    
    @FXML
    private void clickReporteCursoPorID(){
        int idCurso= 0;
        TextInputDialog dialogo = new TextInputDialog("");
        dialogo.setHeaderText("Ingrese el Id del Curso a generar reporte");
        dialogo.showAndWait();
        idCurso = Integer.parseInt(dialogo.getEditor().getText());
      
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"cursos-module-removebg-preview.png");
        parametros.put("idCursos", idCurso );
        GenerarReporte.getInstance().mostrarReporte("CursosById.jasper", parametros, "Reporte de Cursos");      
    }
    
    @FXML
    private void clickReporteAsignacionesPorID(){
        int idAsignacion= 0;
        TextInputDialog dialogo = new TextInputDialog("");
        dialogo.setHeaderText("Ingrese el Id de la asignacion a generar reporte");
        dialogo.showAndWait();
        idAsignacion = Integer.parseInt(dialogo.getEditor().getText());
      
        
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"asignacion alumnos-module.png");
        parametros.put("idAsignacion", idAsignacion );
        GenerarReporte.getInstance().mostrarReporte("AsignacionAlumnosByID.jasper", parametros, "Reporte de Asigancion de lumnos"); 
    }
    
    @FXML
    private void clickCerrarSesion(){
        LoginController login = new LoginController();
        login.hacerNuloObjeto();
        escenarioPrincipal.mostrarLogin();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoginController login = new LoginController();
        if((login.getUsuarioActual()).equals("Estandar")){
            btnAlumnos.setDisable(true);
            btnInstructores.setDisable(true);
            btnCarrerasTecnicas.setDisable(true);
            btnHorarios.setDisable(true);
            btnSalones.setDisable(true);
        }
    }    
}