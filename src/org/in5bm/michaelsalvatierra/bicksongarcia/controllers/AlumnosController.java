package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Alumnos;
import java.text.SimpleDateFormat;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
/**
 *
 * @date Apr 19, 2022
 * @time 7:35:47 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class AlumnosController implements Initializable{

    @FXML
    private Button btnModificar;

    private enum Operacion{
    NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private TextField  txtCarne;
    
    @FXML
    private TableView tblAlumnos;
    
    @FXML
    private TableColumn colCarne;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

    @FXML
    private Button btnAtras;

    @FXML
    private Label lblAdvertenciaNombre1;

    @FXML
    private Label lblAdvertenciaApellido1;

    @FXML
    private Label lblAdvertenciaCarne;

    @FXML
    private Label lblAdvertenciaNombre2;

    @FXML
    private Label lblAdvertenciaNombre3;

    @FXML
    private Label lblAdvertenciaApellido2;

    @FXML
    private ImageView imgCrear;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    private Alumnos alumnoSelect;
    private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    private Principal escenarioPrincipal;
    private ObservableList<Alumnos> listaAlumnos;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

    Alumnos alumno = new Alumnos();

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clickCrear(ActionEvent event) {
        switch (operacion) {
            case NINGUNO: //Primer click en crear
                tblAlumnos.setDisable(true);
                btnEliminar.setDisable(true);
                btnReporte.setDisable(true);
                limpiarCampos();
                habilitarCampos();
                imgCrear.setFitHeight(80);
                imgCrear.setFitWidth(120);
                imgCrear.setImage(new Image(PAQUETE_IMAGES + "button save.png"));
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "BTN-CANCELAR.png"));
                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (validaciones()) {
                    if (agregarAlumnos()) {
                        tblAlumnos.setDisable(false);
                        cargarAlumnos();
                        limpiarCampos();
                        deshabilitarCampos();
                        imgCrear.setFitHeight(70);
                        imgCrear.setFitWidth(100);
                        imgCrear.setImage(new Image(PAQUETE_IMAGES + "crear-removebg-preview.png"));
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                        btnEliminar.setDisable(false);
                        btnReporte.setDisable(false);

                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Exito");
                        alerta.setHeaderText(null);
                        alerta.setContentText("El registro se ha llevado con exito");
                        alerta.initStyle(StageStyle.UTILITY);
                        alerta.showAndWait();
                        validacionesfalse();
                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        switch (operacion) {
            case ACTUALIZAR: //CANCELAR DE ACTUALIZACION
                validacionesfalse();
                limpiarCampos();
                deshabilitarCampos();
                btnCrear.setDisable(false);
                btnReporte.setDisable(false);
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                tblAlumnos.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: //Eliminar
                if (existeElementoSeleccionado()) {
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Control Academico KINAL");
                    confirmacion.setHeaderText(null);
                    Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image (PAQUETE_IMAGES+"ICONO.png"));
                    confirmacion.setContentText("Realmente desea eliminar al alumno : " +"\n" + alumnoSelect.getCarne() + " " + alumnoSelect.getNombre1()+" "+alumnoSelect.getApellido1());
                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        deshabilitarCampos();
                        if (eliminarAlumnos()) {
                            listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
                            cargarAlumnos();
                            limpiarCampos();
                            deshabilitarCampos();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Exito");
                            alerta.setHeaderText(null);
                            alerta.setContentText("La eliminacion se ha llevado con exito");
                            alerta.initStyle(StageStyle.UTILITY);
                            alumnoSelect = null;
                            alerta.showAndWait();
                        }
                    } else {
                        tblAlumnos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Error");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Antes de continuar, elija un registro");
                    alerta.initStyle(StageStyle.UTILITY);
                    alerta.showAndWait();
                }
                break;
        }
    }

    @FXML
    private void clickModificar(ActionEvent event) {
        switch(operacion){
            case NINGUNO:
                if(existeElementoSeleccionado()){
                    System.out.println("operacion = "+operacion);
                    habilitarCampos();
                    txtCarne.setDisable(true);
                    btnCrear.setDisable(true);
                    btnReporte.setDisable(true);
                    imgModificar.setFitHeight(80);
                    imgModificar.setFitWidth(120);
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "button save.png"));
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "BTN-CANCELAR.png"));
                    operacion=Operacion.ACTUALIZAR;
                }else{
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Error");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Antes de continuar, elija un registro");
                    alerta.initStyle(StageStyle.UTILITY);
                    alerta.showAndWait(); 
                }
                break;
            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (validaciones()) {
                        if (actualizarAlumno()) {
                            cargarAlumnos();
                            btnReporte.setDisable(false);
                            btnCrear.setDisable(false);
                            imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                            imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                            tblAlumnos.getSelectionModel().clearSelection();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Exito");
                            alerta.setHeaderText(null);
                            alerta.setContentText("La modificacion se ha llevado con exito");
                            alerta.initStyle(StageStyle.UTILITY);
                            alumnoSelect = null;
                            alerta.showAndWait();
                            operacion = Operacion.NINGUNO;
                            limpiarCampos();
                            validacionesfalse();
                            deshabilitarCampos();
                        }
                    }
                }
                break;
            case GUARDAR: //CANCELAR DE CREAR
                validacionesfalse();
                limpiarCampos();
                tblAlumnos.setDisable(false);
                imgCrear.setFitHeight(70);
                imgCrear.setFitWidth(100);
                deshabilitarCampos();
                imgCrear.setImage(new Image(PAQUETE_IMAGES + "crear-removebg-preview.png"));
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                btnEliminar.setDisable(false);
                btnReporte.setDisable(false);
                operacion = Operacion.NINGUNO;
                break;
        }
    }
    
    private boolean actualizarAlumno(){
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());
        System.out.println(alumno.toString());
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="{CALL sp_alumnos_update(?,?,?,?,?,?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setString(1,alumno.getCarne());
            pstmt.setString(2,alumno.getNombre1());
            pstmt.setString(3,alumno.getNombre2());
            pstmt.setString(4,alumno.getNombre3());
            pstmt.setString(5,alumno.getApellido1());
            pstmt.setString(6,alumno.getApellido2());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar modificar el siquiente alumno "+alumno.toString());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
       }finally{
            try{
                if(pstmt != null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        System.out.println("Atras");
        escenarioPrincipal.mostrarEscenaPrincipal();   
    }

    @FXML
    private void clickReporte(ActionEvent event) {
        System.out.println("Reporte");
        reporte();
    }
    
    private boolean agregarAlumnos() {
//        Alumnos alumno = new Alumnos();
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="{CALL sp_alumnos_create(?,?,?,?,?,?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setString(1,alumno.getCarne());
            pstmt.setString(2,alumno.getNombre1());
            pstmt.setString(3,alumno.getNombre2());
            pstmt.setString(4,alumno.getNombre3());
            pstmt.setString(5,alumno.getApellido1());
            pstmt.setString(6,alumno.getApellido2());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar insertar el siquiente alumno "+alumno.toString());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
       }finally{
            try{
                if(pstmt != null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private void reporte() {
        Alert reporte = new Alert(Alert.AlertType.INFORMATION);
        reporte.setTitle("Control Academico KINAL");
        Stage stageReporte = (Stage) reporte.getDialogPane().getScene().getWindow();
        stageReporte.getIcons().add(new Image(PAQUETE_IMAGES + "ICONO.png"));
        reporte.setHeaderText(null);
        reporte.setContentText("Lo lamento, Esta función es solo para subscriptores premium :( .");
        reporte.showAndWait();
    }
    
    private void habilitarCampos(){
        txtCarne.setDisable(false);
        txtCarne.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }
    
    private void deshabilitarCampos(){
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        
        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
    }

    private void limpiarCampos(){
        txtCarne.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
    }
    
     private ObservableList getAlumnos(){
        List <Alumnos> lista= new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "{CALL sp_alumnos_read()}";
            
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            System.out.println("");
            while(rs.next() == true){
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));
                lista.add(alumno);
                System.out.println(alumno.toString());
            }
            System.out.println("");
            listaAlumnos = FXCollections.observableArrayList(lista);
            
        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de Alumnos");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocurrio un error al listar "+e.getMessage());
            System.err.println("Track del error ");
            e.printStackTrace();
        }finally{
            try{
                if(rs != null){
                    rs.close();
                }
                if(sentencia != null){
                    sentencia.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return listaAlumnos;
    }
    
    private void cargarAlumnos(){
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2")); 
    }
    
    private boolean eliminarAlumnos(){
        if(existeElementoSeleccionado()){
            Alumnos alumno = (Alumnos)tblAlumnos.getSelectionModel().getSelectedItem(); 
            System.out.println("\nA eliminar: "+alumno.toString());
            
            PreparedStatement pstmt= null;
            try{
                String SQL = "{CALL sp_alumnos_delete(?)}";
                pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
                pstmt.setString(1, alumno.getCarne());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            }catch(SQLException e){
                System.err.println("\n Se produjo un error al tratar de eliminar el siguiente registro "+alumno.toString());
                e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    private void validacionesfalse(){
        lblAdvertenciaCarne.setText("");
        lblAdvertenciaNombre1.setText("");
        lblAdvertenciaApellido1.setText("");
    }
    
    private boolean validaciones() {
        boolean validacion1=true;
        boolean validacion2=true;
        boolean validacion3=true;
        boolean validacion4=true;
        boolean validacion5=true;
        boolean validacion6=true;
        
            if (txtCarne.getText().isEmpty()) {
                validacion1=false;
                lblAdvertenciaCarne.setText("CAMPO NECESARIO");
            }
            if (txtNombre1.getText().isEmpty()) {
                validacion2=false;
                lblAdvertenciaNombre1.setText("CAMPO NECESARIO");
            }
            if (txtApellido1.getText().isEmpty()) {
                validacion5=false;
                lblAdvertenciaApellido1.setText("CAMPO NECESARIO");
            }
            
            
//            String nombre1 = txtNombre1.getText().trim();
//            validacion1 = nombre1.matches("^[A-Za-z]\\w{4,29}$");
//            if (validacion1 != true) {
//                lblAdvertenciaNombre1.setVisible(true);
//            }
//
//            String nombre2 = txtNombre2.getText().trim();
//            validacion2 = nombre2.matches("^[A-Za-z]\\w{4,29}$");
//            if (validacion2 != true) {
//            }
//
//            String nombre3 = txtNombre3.getText().trim();
//            validacion3 = nombre3.matches("^[A-Za-z]\\w{4,29}$");
//            if (validacion3 != true) {
//            }
//
//            String apellido1 = txtApellido1.getText().trim();
//            validacion4 = apellido1.matches("^[A-Za-z]\\w{4,29}$");
//            if (validacion4 != true) {
//                lblAdvertenciaApellido1.setVisible(true);
//            }

//            String apellido2 = txtApellido2.getText().trim();
//            validacion5 = apellido2.matches("^[A-Za-z]\\w{4,29}$");
//            if (validacion5 != true) {
//            }

            if (validacion1 & validacion2 & validacion5) {
                return true;
            }
            return false;
    }

    public boolean existeElementoSeleccionado() {
        return (tblAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
            alumnoSelect = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
        }
    }   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarAlumnos();
    }

}