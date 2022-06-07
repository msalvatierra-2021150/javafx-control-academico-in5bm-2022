package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.CallableStatement;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Horarios;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;
import java.sql.Time;
import java.time.LocalTime;

/**
 * @date May 9, 2022
 * @time 10:23:53 PM
 * @author Bill Abel Bickson Garcia Rangel
 * Carne: 2018187
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class HorariosController implements Initializable {
    
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
    private Label lblAdvertenciaPrimerNombre1;
    @FXML
    private Label lblAdvertenciaPrimerNombre11;
    @FXML
    private TableView<?> tblHorarios;
    @FXML
    private CheckBox chkLunes;
    @FXML
    private CheckBox chkbMartes;
    @FXML
    private CheckBox chkbMiercoles;
    @FXML
    private CheckBox chkJueves;
    @FXML
    private CheckBox chkViernes;
    @FXML
    private Button btnAtras;
    @FXML
    private ImageView imgCrear;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private JFXTimePicker tpHorarioInicio;
    @FXML
    private JFXTimePicker tpHorarioFinalizacion;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colHorarioInicio;
    @FXML
    private TableColumn colHorarioFinalizacion;
    @FXML
    private TableColumn colLunes;
    @FXML
    private TableColumn colMartes;
    @FXML
    private TableColumn colMirecoles;
    @FXML
    private TableColumn colJueves;
    @FXML
    private TableColumn colViernes;
    @FXML
    private Label lblId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCursos();
//        tpHorarioFinalizacion.set24HourView(false);
//        tpHorarioInicio.set24HourView(false);
    }    
    
        private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
        
    private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    private ObservableList<Horarios> listaCursos;

        
   @FXML
    private void clickCrear(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                tblHorarios.setDisable(true);
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
                //if (validaciones()) {
                    //if (agregarSalones()) {
                        tblHorarios.setDisable(false);
                        ////cargarCursos();
                        limpiarCampos();
                        deshabilitarCampos();
                        imgCrear.setFitHeight(70);
                        imgCrear.setFitWidth(100);
                        imgCrear.setImage(new Image(PAQUETE_IMAGES + "crear-removebg-preview.png"));
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                        btnEliminar.setDisable(false);
                        btnReporte.setDisable(false);
                        //validacionesfalse();
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Exito");
                        alerta.setHeaderText(null);
                        alerta.setContentText("El registro se ha llevado con exito");
                        alerta.initStyle(StageStyle.UTILITY);
                        alerta.showAndWait();
                        operacion = Operacion.NINGUNO;
                    //}
                //}
                break;
        }
    }
    
       @FXML
    private void clickEliminar(ActionEvent event) {
        switch (operacion) {
            case ACTUALIZAR: //CANCELAR DE ACTUALIZACION
                //validacionesfalse();
                limpiarCampos();
                deshabilitarCampos();
                btnCrear.setDisable(false);
                btnReporte.setDisable(false);
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                tblHorarios.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                //if (existeElementoSeleccionado()) {
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Control Academico KINAL");
                    confirmacion.setHeaderText(null);
                    Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "ICONO.png"));
                    //confirmacion.setContentText("Realmente desea eliminar al salon : " + "\n" + salonSelect.getCodigoSalon());
                    Optional<ButtonType> result = confirmacion.showAndWait();

                    //if (result.get() == ButtonType.OK) {
                        deshabilitarCampos();
                        //if (eliminarAlumnos()) {
                            //listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                            ////cargarCursos();
                            limpiarCampos();
                            deshabilitarCampos();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Exito");
                            alerta.setHeaderText(null);
                            alerta.setContentText("La eliminacion se ha llevado con exito");
                            alerta.initStyle(StageStyle.UTILITY);
                            //salonSelect = null;
                            alerta.showAndWait();
                        //}
                    //} else {
                        tblHorarios.getSelectionModel().clearSelection();
                        limpiarCampos();
                    //}
                //} else {
//                    Alert alerta = new Alert(Alert.AlertType.WARNING);
//                    alerta.setTitle("Error");
//                    alerta.setHeaderText(null);
//                    alerta.setContentText("Antes de continuar, elija un registro");
//                    alerta.initStyle(StageStyle.UTILITY);
//                    alerta.showAndWait();
                    break;
               // }
        }
    }
    
    @FXML
    private void clickModificar(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                //if (existeElementoSeleccionado()) {
                    habilitarCampos();
                    //txtCodigoDelSalon.setDisable(true);
                    btnCrear.setDisable(true);
                    btnReporte.setDisable(true);
                    imgModificar.setFitHeight(80);
                    imgModificar.setFitWidth(120);
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "button save.png"));
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "BTN-CANCELAR.png"));
                    operacion = Operacion.ACTUALIZAR;
                //} else {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Error");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Antes de continuar, elija un registro");
                    alerta.initStyle(StageStyle.UTILITY);
                    alerta.showAndWait();
               // }
                break;
            case ACTUALIZAR:
               // if (existeElementoSeleccionado()) {
                    //if (validaciones()) {
                       // if (actualizarSalones()) {
                            ////cargarCursos();
                            btnReporte.setDisable(false);
                            btnCrear.setDisable(false);
                            imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                            imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
//                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
//                            alerta.setTitle("Exito");
//                            alerta.setHeaderText(null);
//                            alerta.setContentText("La modificacion se ha llevado con exito");
//                            alerta.initStyle(StageStyle.UTILITY);
//                            alerta.showAndWait();
                            operacion = Operacion.NINGUNO;
                            tblHorarios.getSelectionModel().clearSelection();
                            limpiarCampos();
                            deshabilitarCampos();
                        //}
                    //}
                //}
                break;
            case GUARDAR: //CANCELAR EN CREAR
                //validacionesfalse();
                tblHorarios.setDisable(false);
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

    private void habilitarCampos() {
        tpHorarioInicio.setDisable(false);
        tpHorarioFinalizacion.setDisable(false);
        chkLunes.setDisable(false);
        chkbMartes.setDisable(false);
        chkbMiercoles.setDisable(false);
        chkJueves.setDisable(false);
        chkViernes.setDisable(false);
    }
    
    private void deshabilitarCampos() {
        tpHorarioInicio.setDisable(true);
        tpHorarioFinalizacion.setDisable(true);
        chkLunes.setDisable(true);
        chkbMartes.setDisable(true);
        chkbMiercoles.setDisable(true);
        chkJueves.setDisable(true);
        chkViernes.setDisable(true);
    }
    
    private void limpiarCampos() {
        tpHorarioInicio.getEditor().clear();
        tpHorarioFinalizacion.getEditor().clear();
        chkLunes.setSelected(false);
        chkbMartes.setSelected(false);
        chkbMiercoles.setSelected(false);
        chkJueves.setSelected(false);
        chkViernes.setSelected(false);
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
    
    private ObservableList getHorarios() {
        List<Horarios> lista = new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "CALL sp_horarios_read();";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            LocalTime localTime = LocalTime.now();
            while (rs.next() == true) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2));
                horario.setHorariofinal(rs.getTime(3));
                horario.setLunes((Byte)rs.getByte(4));
                horario.setMartes(rs.getByte(5));
                horario.setMiercoles(rs.getByte(6));
                horario.setJueves(rs.getByte(7));
                horario.setViernes(rs.getByte(8));
                lista.add(horario);
                System.out.println(horario.toString());
            }
            listaCursos = FXCollections.observableArrayList(lista);
        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de Salones");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocurrio un error al listar " + e.getMessage());
            System.err.println("Track del error ");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaCursos;
    }
    
    private void cargarCursos() {
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("id"));
        colHorarioInicio.setCellValueFactory(new PropertyValueFactory<Horarios, Time>("horarioInicio"));
        colHorarioFinalizacion.setCellValueFactory(new PropertyValueFactory<Horarios, Time>("horarioFinal"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("martes"));
        colMirecoles.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("viernes"));
    }
    
    public boolean existeElementoSeleccionado() {
        return (tblHorarios.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            lblId.setText(String.valueOf(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getId()));
            tpHorarioInicio.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioInicio().toLocalTime());
            tpHorarioFinalizacion.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioFinal().toLocalTime());
            chkLunes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isLunes());
        }
    }  
    
    
}
