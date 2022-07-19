package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Instructores;
import org.in5bm.michaelsalvatierra.bicksongarcia.reports.GenerarReporte;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;

/**
 *
 * @date May 6, 2022
 * @time 8:10:24 PM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class InstructoresController implements Initializable{ 
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
        
    
    @FXML
    private Button btnModificar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private Button btnAtras;

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
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private DatePicker dtpFechaDeNacimiento;

    @FXML
    private Label lblId;

    @FXML
    private TableView tblInstructores;
    
    @FXML
    private TableColumn colId;

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
    private TableColumn colDireccion;
    
    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colFechaNacimiento;
    
    @FXML
    private Label lblAdvertenciaNombre1;

    @FXML
    private Label lblAdvertenciaEmail;

    @FXML
    private Label lblAdvertenciaTelefono;

    @FXML
    private Label lblAdvertenciApelido1;
    
    @FXML
    private ImageView imgCrear;    
    
    @FXML
    private ImageView imgModificar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private Label lblTotalInstructores;
    
    @FXML
    private Label lblAdvertenciaFechaNacimiento;
    
    private Instructores instructoresSelect = new Instructores();
    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    private ObservableList<Instructores> listaInstructores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInstructores();
        conteoRegistros();
    }

    @FXML
    private void clickCrear(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                lblId.setText(String.valueOf(getId()));
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
                    if (agregarInstructores()) {
                        tblInstructores.setDisable(false);
                        cargarInstructores();
                        limpiarCampos();
                        deshabilitarCampos();
                        imgCrear.setFitHeight(70);
                        imgCrear.setFitWidth(100);
                        imgCrear.setImage(new Image(PAQUETE_IMAGES + "crear-removebg-preview.png"));
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                        btnEliminar.setDisable(false);
                        btnReporte.setDisable(false);
                        validacionesfalse();
                        conteoRegistros();
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Exito");
                        alerta.setHeaderText(null);
                        alerta.setContentText("El registro se ha llevado con exito");
                        alerta.initStyle(StageStyle.UTILITY);
                        alerta.showAndWait();
                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        switch (operacion){
            case ACTUALIZAR: //CANCELAR DE ACTUALIZACION
                validacionesfalse();
                limpiarCampos();
                deshabilitarCampos();
                btnCrear.setDisable(false);
                btnReporte.setDisable(false);
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                tblInstructores.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if(existeElementoSeleccionado()) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Control Academico KINAL");
                confirmacion.setHeaderText(null);
                Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(PAQUETE_IMAGES + "ICONO.png"));
                confirmacion.setContentText("Realmente desea eliminar la carreca tecnica : " + "\n" + instructoresSelect.getId()+ " " + instructoresSelect.getNombre1()+ " " + instructoresSelect.getApellido1());
                Optional<ButtonType> result = confirmacion.showAndWait();

                if (result.get() == ButtonType.OK)
                {
                    deshabilitarCampos();
                    if (eliminarInstructores())
                    {
                        listaInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
                        cargarInstructores();
                        limpiarCampos();
                        deshabilitarCampos();
                        conteoRegistros();
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Exito");
                        alerta.setHeaderText(null);
                        alerta.setContentText("La eliminacion se ha llevado con exito");
                        alerta.initStyle(StageStyle.UTILITY);
                        instructoresSelect = null;
                        alerta.showAndWait();
                        tblInstructores.getSelectionModel().clearSelection();
                    }
                }else{
                    tblInstructores.getSelectionModel().clearSelection();
                    limpiarCampos();
                }
            }
            else {
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
        switch (operacion) {
            case NINGUNO:
                if(existeElementoSeleccionado()){
                    habilitarCampos();
                    lblId.setDisable(true);
                    btnCrear.setDisable(true);
                    btnReporte.setDisable(true);
                    imgModificar.setFitHeight(80);
                    imgModificar.setFitWidth(120);
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "button save.png"));
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "BTN-CANCELAR.png"));
                    operacion = Operacion.ACTUALIZAR;
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
                        if (actualizarInstructores()) {
                            cargarInstructores();
                            deshabilitarCampos();
                            btnReporte.setDisable(false);
                            btnCrear.setDisable(false);
                            imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                            imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Exito");
                            alerta.setHeaderText(null);
                            alerta.setContentText("La modificacion se ha llevado con exito");
                            alerta.initStyle(StageStyle.UTILITY);
                            alerta.showAndWait();
                            tblInstructores.getSelectionModel().clearSelection();
                            operacion = Operacion.NINGUNO;
                            limpiarCampos();
                            deshabilitarCampos();
                            validacionesfalse();
                        }
                    }
                }  
                break;
            case GUARDAR: //CANCELA EN CREAR
                validacionesfalse();
                limpiarCampos();
                tblInstructores.setDisable(false);
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
    
    private void conteoRegistros(){
        int total= 0;
        for (int i = 0; i < listaInstructores.size(); i++) {
            total = total + 1;
        }
        lblTotalInstructores.setText(String.valueOf(total));
    }
    
    private boolean agregarInstructores() {
        Instructores instructor = new Instructores();
//        instructor.setId(Integer.parseInt(lblId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaDeNacimiento(java.sql.Date.valueOf(dtpFechaDeNacimiento.getValue()));
        PreparedStatement pstmt = null;
        try{
            String SQL ="CALL sp_instructores_create(?,?,?,?,?,?,?,?,?);";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setString(1,instructor.getNombre1());
            pstmt.setString(2,instructor.getNombre2());
            pstmt.setString(3,instructor.getNombre3());
            pstmt.setString(4,instructor.getApellido1());
            pstmt.setString(5,instructor.getApellido2());
            pstmt.setString(6,instructor.getDireccion());
            pstmt.setString(7,instructor.getEmail());
            pstmt.setString(8,instructor.getTelefono());
            pstmt.setDate(9,instructor.getFechaDeNacimiento());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar insertar el siquiente alumno "+instructor.toString());
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
    
    private boolean actualizarInstructores(){
        Instructores instructor = new Instructores();
        instructor.setId(Integer.parseInt(lblId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaDeNacimiento(java.sql.Date.valueOf(dtpFechaDeNacimiento.getValue()));
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="CALL sp_instructores_update(?,?,?,?,?,?,?,?,?,?);";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setInt(1,instructor.getId());
            pstmt.setString(2,instructor.getNombre1());
            pstmt.setString(3,instructor.getNombre2());
            pstmt.setString(4,instructor.getNombre3());
            pstmt.setString(5,instructor.getApellido1());
            pstmt.setString(6,instructor.getApellido2());
            pstmt.setString(7,instructor.getDireccion());
            pstmt.setString(8,instructor.getEmail());
            pstmt.setString(9,instructor.getTelefono());
            pstmt.setDate(10,instructor.getFechaDeNacimiento());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar modificar el siquiente alumno "+instructor.toString());
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
        
    
    private boolean eliminarInstructores(){
        if(existeElementoSeleccionado()){
            Instructores InstructoresSelect = (Instructores)tblInstructores.getSelectionModel().getSelectedItem(); 
            System.out.println("\nA eliminar: "+InstructoresSelect.toString());
            
            PreparedStatement pstmt= null;
            try{
                String SQL = "CALL sp_instructores_delete(?);";
                pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
                pstmt.setInt(1, InstructoresSelect.getId());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            }catch(SQLException e){
                System.err.println("\n Se produjo un error al tratar de eliminar el siguiente registro "+InstructoresSelect.toString());
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
        lblAdvertenciaNombre1.setText("");
        lblAdvertenciApelido1.setText("");
        lblAdvertenciaEmail.setText("");
        lblAdvertenciaTelefono.setText("");
        lblAdvertenciaFechaNacimiento.setText("");
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
    
    private void habilitarCampos(){     
        lblId.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        dtpFechaDeNacimiento.setDisable(false);
    }
    
    private void deshabilitarCampos(){
        lblId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        dtpFechaDeNacimiento.setDisable(true);
    }

    private void limpiarCampos(){
        lblId.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        dtpFechaDeNacimiento.getEditor().clear();
    }
    
    private void reporte() {
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"instructores-module.png");
        GenerarReporte.getInstance().mostrarReporte("Instructores.jasper", parametros, "Reporte de Instructores");
    }
    
    private ObservableList getInstructores(){
        List <Instructores> lista= new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "CALL sp_instructores_read();";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            while(rs.next() == true){
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaDeNacimiento(rs.getDate(10));
                lista.add(instructor);
                System.out.println(instructor.toString());
                listaInstructores = FXCollections.observableArrayList(lista);
            }

        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de Carreras Tecnicas");
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
        return listaInstructores;
    }
    
    private void cargarInstructores(){
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<Instructores, String>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Instructores, String>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Instructores, String>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Instructores, String>("telefono"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<Instructores, String>("fechaDeNacimiento"));
    }
        
    private boolean validaciones() {
        boolean validacion1=true;
        boolean validacion2=true;
        boolean validacion3=true;
        boolean validacion4=true;
        boolean validacion5=true;
        
            if (txtNombre1.getText().isEmpty()) {
                validacion1=false;
                lblAdvertenciaNombre1.setText("CAMPO NECESARIO");
            }
            if (txtApellido1.getText().isEmpty()) {
                validacion2=false;
                lblAdvertenciApelido1.setText("CAMPO NECESARIO");
            }
            if (txtEmail.getText().isEmpty()) {
                validacion3=false;
                lblAdvertenciaEmail.setText("CAMPO NECESARIO");
            }
            if (txtTelefono.getText().isEmpty()) {
                validacion4=false;
                lblAdvertenciaTelefono.setText("CAMPO NECESARIO");
            }
            
            if(dtpFechaDeNacimiento.getValue() ==null){
                validacion5=false;
                lblAdvertenciaFechaNacimiento.setText("CAMPO NECESARIO");
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

            if (validacion1 & validacion2 &validacion3 & validacion4 & validacion5) {
                return true;
            }
            return false;
    }
        
    public boolean existeElementoSeleccionado() {
        return (tblInstructores.getSelectionModel().getSelectedItem() != null);
    }
        
    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) { 
            instructoresSelect = ((Instructores) tblInstructores.getSelectionModel().getSelectedItem());
            System.out.println(instructoresSelect);
            lblId.setText(String.valueOf(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId()));
            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDireccion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            if((((Instructores)tblInstructores.getSelectionModel().getSelectedItem()).getFechaDeNacimiento())!= null){
                dtpFechaDeNacimiento.setValue(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFechaDeNacimiento().toLocalDate());   
            }else{
               dtpFechaDeNacimiento.getEditor().clear(); 
            }
        }
    }
    
    private int getId(){
        CallableStatement sentencia = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String SQL = "CALL sp_instructores_read();";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            while(rs.next() == true){
                id++;
            }

        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de Carreras Tecnicas");
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
        return id;
    }
}
