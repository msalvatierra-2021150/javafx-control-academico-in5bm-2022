package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Usuario;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;
/**
 *
 * @date Jul 19, 2022
 * @time 7:39:25 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */

public class LoginController implements Initializable{

    @FXML
    private PasswordField pfContrasena;

    @FXML
    private TextField txtUsuario;
    
    @FXML
    private Button btnIngresar;
    
    private Principal escenarioPrincipal;

    public String userAPasar;
    
    public String tipoDeUser;
    
    String confirmacion;
    
    public LoginController(){
        this.userAPasar=usuario.getUser();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }  
    
     Usuario usuario = new Usuario();
     
    String user ;
    String pass;

     
    @FXML
    void clickLogin(ActionEvent event) {
        boolean validacion1=true;
        boolean validacion2=true;
        
         if (txtUsuario.getText().isEmpty()) {
                validacion1=false;
            }
        if (pfContrasena.getText().isEmpty()) {
                validacion2=false;
        }

        if (!(validacion1 & validacion2)) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Credenciales Ingresados erroneos");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();
        }   
        
        String confirmacion = conexionADatos();
        System.out.println("CONFIRMACION "+confirmacion);
        
        if (!(confirmacion == null)){
            if (confirmacion.equals("Estandar") || confirmacion.equals("Administrador")) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Control Academico IN5BM");
                alerta.setHeaderText(null);
                alerta.setContentText("BIENVENIDO");
                alerta.initStyle(StageStyle.UTILITY);
                escenarioPrincipal.mostrarEscenaPrincipal(); 
                alerta.showAndWait();   
            }else{
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("El usuario o la contraseña es incorrecta.");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
            }
        }else{
         Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("El usuario o la contraseña es incorrecta , Intente de nuevo.");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();
        }
    }

    public String conexionADatos() {
        String user =txtUsuario.getText();
        String pass=pfContrasena.getText();
        CallableStatement sentencia = null;
        
        String verificacion = "";
        
        ResultSet rs = null;
        try {
            String SQL = "{CALL sp_user_exists(?,?)}";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            sentencia.setString(1, user);
            sentencia.setString(2, pass);
            rs = sentencia.executeQuery();
            
            System.out.println(sentencia.toString());
            
            while(rs.next() == true){
                verificacion = rs.getString(2);
                usuario.setTipo(rs.getString(1));
                System.out.println(usuario.getTipo());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar loggear con el user");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        return usuario.getTipo();
    }
    
    public void hacerNuloObjeto(){
        usuario.setTipo(null);
    }

        
    public String getUsuarioActual(){  
        return usuario.getTipo();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }
}

