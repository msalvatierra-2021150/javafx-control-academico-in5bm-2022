package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.CarrerasTecnicas;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;

/**
 *
 * @date Apr 19, 2022
 * @time 9:35:47 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class CarrerasTecnicasController implements Initializable {
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
        
    @FXML
    private TextField txtCarrera;

    @FXML
    private TextField txtGrado;

    @FXML
    private TextField txtSeccion;

    @FXML
    private TextField txtJornada;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnCrear;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnReporte;
    
    @FXML
    private Label lblAdvertenciaCarrera;
    
    @FXML
    private Label lblAdvertenciaGrado;
    
    @FXML
    private Label lblAdvertenciaSeccion;
    
    @FXML
    private Label lblAdvertenciaJornada;
    
    @FXML
    private TextField txtCodigoTecnico;
    
    @FXML
    private Label lblAdvertenciaCodigoTecnico;
    
    @FXML
    private TableView tblCarrerasTecnicas;
    
    @FXML
    private TableColumn colCodigoTecnico;
     
    @FXML
    private TableColumn colCarrera;
    
    @FXML
    private TableColumn colGrado;
    
    @FXML
    private TableColumn colSeccion;
    
    @FXML
    private TableColumn colJornada;
    
    @FXML
    private Button btnAtras;
    
    @FXML
    private ImageView imgCrear;    
    
    @FXML
    private ImageView imgModificar;
    
    @FXML
    private ImageView imgEliminar;
    
    private Principal escenarioPrincipal;
    private int clickBtnModificar;
    private CarrerasTecnicas carreraTecnicaSelect;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCarrerasTecnicas();
    }

    @FXML
    private void clickCrear(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
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
                    if (agregarCarrerasTecnicas()) {
                        tblCarrerasTecnicas.setDisable(false);
                        cargarCarrerasTecnicas();
                        limpiarCampos();
                        deshabilitarCampos();
                        imgCrear.setFitHeight(70);
                        imgCrear.setFitWidth(100);
                        imgCrear.setImage(new Image(PAQUETE_IMAGES + "crear-removebg-preview.png"));
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                        btnEliminar.setDisable(false);
                        btnReporte.setDisable(false);
                        validacionesfalse();
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
                tblCarrerasTecnicas.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if(existeElementoSeleccionado()) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Control Academico KINAL");
                confirmacion.setHeaderText(null);
                Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(PAQUETE_IMAGES + "ICONO.png"));
                confirmacion.setContentText("Realmente desea eliminar la carreca tecnica : " + "\n" + carreraTecnicaSelect.getCodigoTecnico()+ " " + carreraTecnicaSelect.getCarrera() + " " + carreraTecnicaSelect.getJornada());
                Optional<ButtonType> result = confirmacion.showAndWait();

                if (result.get() == ButtonType.OK)
                {
                    deshabilitarCampos();
                    if (eliminarCarrerasTecnicas())
                    {
                        listaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
                        cargarCarrerasTecnicas();
                        limpiarCampos();
                        deshabilitarCampos();
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Exito");
                        alerta.setHeaderText(null);
                        alerta.setContentText("La eliminacion se ha llevado con exito");
                        alerta.initStyle(StageStyle.UTILITY);
                        carreraTecnicaSelect = null;
                        alerta.showAndWait();
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
                    }
                }else{
                    tblCarrerasTecnicas.getSelectionModel().clearSelection();
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
                    txtCodigoTecnico.setDisable(true);
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
                        if (actualizarCarrerasTecnicas()) {
                            cargarCarrerasTecnicas();
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
                            tblCarrerasTecnicas.getSelectionModel().clearSelection();
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
                tblCarrerasTecnicas.setDisable(false);
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
    
    private boolean agregarCarrerasTecnicas() {
        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        carreraTecnica.setCarrera(txtCarrera.getText());
        carreraTecnica.setGrado(txtGrado.getText());
        carreraTecnica.setJornada(txtJornada.getText());
        carreraTecnica.setSeccion(txtSeccion.getText());
        carreraTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="CALL sp_carreras_tecnicas_create(?,?,?,?,?);";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setString(1,carreraTecnica.getCodigoTecnico());
            pstmt.setString(2,carreraTecnica.getCarrera());
            pstmt.setString(3,carreraTecnica.getGrado());
            pstmt.setString(4,carreraTecnica.getSeccion());
            pstmt.setString(5,carreraTecnica.getJornada());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar insertar el siquiente alumno "+carreraTecnica.toString());
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
    
        private boolean actualizarCarrerasTecnicas(){
        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        carreraTecnica.setCarrera(txtCarrera.getText());
        carreraTecnica.setGrado(txtGrado.getText());
        carreraTecnica.setJornada(txtJornada.getText());
        carreraTecnica.setSeccion(txtSeccion.getText());
        carreraTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="CALL sp_carreras_tecnicas_update(?,?,?,?,?);";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setString(1,carreraTecnica.getCodigoTecnico());
            pstmt.setString(2,carreraTecnica.getCarrera());
            pstmt.setString(3,carreraTecnica.getGrado());
            pstmt.setString(4,carreraTecnica.getSeccion());
            pstmt.setString(5,carreraTecnica.getJornada());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar modificar el siquiente alumno "+carreraTecnica.toString());
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
        
    
    private boolean eliminarCarrerasTecnicas(){
        if(existeElementoSeleccionado()){
            CarrerasTecnicas carreraTecnicaSelect = (CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem(); 
            System.out.println("\nA eliminar: "+carreraTecnicaSelect.toString());
            
            PreparedStatement pstmt= null;
            try{
                String SQL = "CALL sp_carreras_tecnicas_delete(?);";
                pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
                pstmt.setString(1, carreraTecnicaSelect.getCodigoTecnico());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            }catch(SQLException e){
                System.err.println("\n Se produjo un error al tratar de eliminar el siguiente registro "+carreraTecnicaSelect.toString());
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
        lblAdvertenciaCodigoTecnico.setText("");
        lblAdvertenciaCarrera.setText("");
        lblAdvertenciaGrado.setText("");
        lblAdvertenciaJornada.setText("");
        lblAdvertenciaSeccion.setText("");
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
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtJornada.setDisable(false);
        txtSeccion.setDisable(false);
        txtCodigoTecnico.setDisable(false);
    }
    
    private void deshabilitarCampos(){
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtJornada.setDisable(true);
        txtSeccion.setDisable(true);
        txtCodigoTecnico.setDisable(true);
    }

    private void limpiarCampos(){
        txtCodigoTecnico.setText("");
        txtCarrera.setText("");
        txtGrado.setText("");
        txtJornada.setText("");
        txtSeccion.setText("");
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
    
    private ObservableList getCarrerasTecnicas(){
        List <CarrerasTecnicas> lista= new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "CALL sp_carreras_tecnicas_read();";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            while(rs.next() == true){
                CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
                carreraTecnica.setCodigoTecnico(rs.getString(1));
                carreraTecnica.setCarrera(rs.getString(2));
                carreraTecnica.setGrado(rs.getString(3));
                carreraTecnica.setSeccion(rs.getString(4));
                carreraTecnica.setJornada(rs.getString(5));
                lista.add(carreraTecnica);
                System.out.println(carreraTecnica.toString());
                listaCarrerasTecnicas = FXCollections.observableArrayList(lista);
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
        return listaCarrerasTecnicas;
    }
    
    private void cargarCarrerasTecnicas(){
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
    }
        
    private boolean validaciones() {
        boolean validacion1=true;
        boolean validacion2=true;
        boolean validacion3=true;
        boolean validacion4=true;
        boolean validacion5=true;
        boolean validacion6=true;
        
            if (txtCodigoTecnico.getText().isEmpty()) {
                validacion1=false;
                lblAdvertenciaCodigoTecnico.setText("CAMPO NECESARIO");
            }
            if (txtJornada.getText().isEmpty()) {
                validacion2=false;
                lblAdvertenciaJornada.setText("CAMPO NECESARIO");
            }
            if (txtSeccion.getText().isEmpty()) {
                validacion3=false;
                lblAdvertenciaSeccion.setText("CAMPO NECESARIO");
            }
            if (txtGrado.getText().isEmpty()) {
                validacion4=false;
                lblAdvertenciaGrado.setText("CAMPO NECESARIO");
            }
            if (txtCarrera.getText().isEmpty()) {
                validacion5=false;
                lblAdvertenciaCarrera.setText("CAMPO NECESARIO");
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
        return (tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null);
    }
        
    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            carreraTecnicaSelect = ((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem());
            txtCodigoTecnico.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtSeccion.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion());
            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
        }
    }
}
