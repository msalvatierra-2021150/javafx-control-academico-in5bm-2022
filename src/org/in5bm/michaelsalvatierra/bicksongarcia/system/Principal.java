package org.in5bm.michaelsalvatierra.bicksongarcia.system;

import java.io.IOException;
import java.sql.Connection;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.AlumnosController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.AsignacionesAlumnosController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.CarrerasTecnicasController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.CursosController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.HorariosController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.InstructoresController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.LoginController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.MenuPrincipalController;
import org.in5bm.michaelsalvatierra.bicksongarcia.controllers.SalonesController;
/**
 * @date Apr 5, 2022
 * @time 9:24:06 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class Principal extends Application{
    private Stage escenarioPrincipal;
    
    private final String PAQUETE_IMAGES= "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    private final String PAQUETE_VIEWS = "../views/";
    
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Academico KINAL");
        Image icon = new Image(PAQUETE_IMAGES+"ICONO.png");
        this.escenarioPrincipal.getIcons().add(icon);
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        mostrarLogin();
    }

    public Initializable cambiarEscena(String vistaFXML, int alto, int ancho) throws IOException {
        System.out.println("Cambiando de escena: "+PAQUETE_VIEWS + vistaFXML);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEWS + vistaFXML));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }
    
    public void mostrarLogin( ){
        try {
            LoginController loginController = (LoginController)cambiarEscena("Login.fxml",602, 1024);
            loginController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista del menu Principal "+ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaPrincipal(){
        try {
            MenuPrincipalController menuController = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",602, 1024);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista del menu Principal "+ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
    
    public String mostrarEscenaPrincipal(String tipo){
        System.out.println("TIPO LLEGA "+tipo);
        try {
            MenuPrincipalController menuController = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",602, 1024);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista del menu Principal "+ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
        return tipo;
    }
    
    public void mostrarEscenaAlumnos(){
        try {
         AlumnosController alumnosController = (AlumnosController)cambiarEscena("AlumnosView.fxml",602, 1024);
         alumnosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista de Alumnos " + ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
        
       public void mostrarEscenaSalones(){
        try {
         SalonesController salonesController = (SalonesController)cambiarEscena("SalonesView.fxml",602, 1024);
         salonesController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista de Salones " + ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
       
        public void mostrarEscenaCarrerasTecnicas(){
        try {
         CarrerasTecnicasController carrerasController = (CarrerasTecnicasController)cambiarEscena("CarrerasTecnicasView.fxml",602, 1024);
         carrerasController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista de Carreras Tecnicas" + ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
        
        public void mostrarEscenaAsignaciones(){
        try {
         AsignacionesAlumnosController asignacionesController = (AsignacionesAlumnosController)cambiarEscena("AsignacionesAlumnosView.fxml",602, 1024);
         asignacionesController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista de Asignaciones de Alumnos " + ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
        
    public void mostrarEscenaInstructores(){
        try {
         InstructoresController instructoresController = (InstructoresController)cambiarEscena("InstructoresView.fxml",602, 1024);
         instructoresController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista de Instructores" + ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaHorarios(){
        try {
         HorariosController horariosController = (HorariosController)cambiarEscena("HorariosView.fxml",602, 1024);
         horariosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista de Instructores" + ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaCursos(){
        try {
         CursosController cursosController = (CursosController)cambiarEscena("CursosView.fxml",602, 1024);
         cursosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Ocurrio un error al mostar la vista de Instructores" + ex.getMessage());
            System.err.println("Track del error ");
            ex.printStackTrace();
        }
    }
}

