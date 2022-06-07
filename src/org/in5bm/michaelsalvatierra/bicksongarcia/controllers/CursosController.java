package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Cursos;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Instructores;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Salones;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.CarrerasTecnicas;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Horarios;

/**
 * @date Apr 5, 2022
 * @time 11:58:57 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */

public class CursosController implements Initializable {

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
    private Button btnCancelar;
    @FXML
    private TextField txtNombreDelCurso;
    @FXML
    private Spinner<Integer> spnCupoMaximo;
    private SpinnerValueFactory<Integer> valueFactoryCupoMaximo;
    @FXML
    private Spinner <Integer>spnCupoMinimo;
    private SpinnerValueFactory<Integer> valueFactoryCupoMinimo;
    @FXML
    private ComboBox <CarrerasTecnicas> cmbIdCarreraTecnica;
    @FXML
    private ComboBox <Horarios> cmbIdHorario;
    @FXML
    private ComboBox <Instructores> cmbIdInstructor;
    @FXML
    private Spinner <Integer> spnCiclo;
    private SpinnerValueFactory<Integer> valueFactoryCiclo;
    @FXML
    private ComboBox <Salones> cmbIdSalon;
    @FXML
    private TableView <Cursos> tblCurso;
    @FXML
    private TableColumn <Cursos, Integer> colId;
    @FXML
    private TableColumn <Cursos, String> colNombreCurso;
    @FXML
    private TableColumn <Cursos,Integer> colCiclo;
    @FXML
    private TableColumn <Cursos, Integer>colCupoMaximo;
    @FXML
    private TableColumn <Cursos, Integer> colCupoMinimo;
    @FXML
    private TableColumn <Cursos,String> colIdCarreraTecnica;
    @FXML
    private TableColumn <Cursos,Integer> colIdHorario;
    @FXML
    private TableColumn <Cursos, Integer> colIdInstructor;
    @FXML
    private TableColumn <Cursos, String>colIdSalon;
    @FXML
    private ImageView imgCrear;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Label lblId;
    @FXML
    private Label lblAdvertenciaNombreCurso;
    @FXML
    private Label lblAdvertenciaIdCarreraTecnica;
    @FXML
    private Label lblAdvertenciaIdHorario;
    @FXML
    private Label lblAdvertenciaIdInstructor;
    @FXML
    private Label lblAdvertenciaIdSalon;
    
    private Cursos cursosSelect;
    private ObservableList<Cursos> listaCursos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1960, 2022, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);
        
        valueFactoryCupoMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spnCupoMaximo.setValueFactory(valueFactoryCupoMaximo);
        
        valueFactoryCupoMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spnCupoMinimo.setValueFactory(valueFactoryCupoMinimo);
        
        cargarCursos();
    }    
    
    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";

        
   @FXML
    private void clickCrear(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                tblCurso.setDisable(true);
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
                    //if (agregarSalones()) {
                        tblCurso.setDisable(false);
                        cargarCursos();
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
                    //}
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
                tblCurso.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Control Academico KINAL");
                    confirmacion.setHeaderText(null);
                    Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "ICONO.png"));
                    confirmacion.setContentText("Realmente desea eliminar al salon : " + "\n" + cursosSelect.getNombreCurso());
                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        deshabilitarCampos();
                        //if (eliminarAlumnos()) {
                            //listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                            cargarCursos();
                            limpiarCampos();
                            deshabilitarCampos();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Exito");
                            alerta.setHeaderText(null);
                            alerta.setContentText("La eliminacion se ha llevado con exito");
                            alerta.initStyle(StageStyle.UTILITY);
                            cursosSelect = null;
                            alerta.showAndWait();
                        //}
                    } else {
                        tblCurso.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Error");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Antes de continuar, elija un registro");
                    alerta.initStyle(StageStyle.UTILITY);
                    alerta.showAndWait();
                    break;
                }
        }
    }
    
    @FXML
    private void clickModificar(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();
                    btnCrear.setDisable(true);
                    btnReporte.setDisable(true);
                    imgModificar.setFitHeight(80);
                    imgModificar.setFitWidth(120);
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "button save.png"));
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "BTN-CANCELAR.png"));
                    operacion = Operacion.ACTUALIZAR;
                } else {
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
                       // if (actualizarSalones()) {
                            cargarCursos();
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
                            operacion = Operacion.NINGUNO;
                            tblCurso.getSelectionModel().clearSelection();
                            limpiarCampos();
                            deshabilitarCampos();
                        //}
                    }
                }
                break;
            case GUARDAR: //CANCELAR EN CREAR
                validacionesfalse();
                tblCurso.setDisable(false);
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
        lblId.setDisable(false);
        txtNombreDelCurso.setDisable(false);
        spnCiclo.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        cmbIdCarreraTecnica.setDisable(false);
        cmbIdHorario.setDisable(false);
        cmbIdInstructor.setDisable(false);
        cmbIdSalon.setDisable(false);
    }
    
    private void deshabilitarCampos() {
        lblId.setDisable(true);
        txtNombreDelCurso.setDisable(true);
        spnCiclo.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        cmbIdCarreraTecnica.setDisable(true);
        cmbIdHorario.setDisable(true);
        cmbIdInstructor.setDisable(true);
        cmbIdSalon.setDisable(true);
    }
    
    private void limpiarCampos() {
        lblId.setText("");
        txtNombreDelCurso.setText("");
        spnCiclo.getValueFactory().setValue(2022);
        spnCupoMaximo.getValueFactory().setValue(1);
        spnCupoMinimo.getValueFactory().setValue(1);
        cmbIdCarreraTecnica.valueProperty().set(null);
        cmbIdHorario.valueProperty().set(null);
        cmbIdInstructor.valueProperty().set(null);
        cmbIdSalon.valueProperty().set(null);
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
    
    private ObservableList getCursos() {
        List<Cursos> lista = new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "CALL sp_cursos_read();";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            while (rs.next() == true) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt(1));
                curso.setNombreCurso(rs.getString(2));
                curso.setCiclo(rs.getString(3).substring(0,4));
                curso.setCupoMaximo(rs.getInt(4));
                curso.setCupoMinimo(rs.getInt(5));
                curso.setCarreraTecnicaId(rs.getString(6));
                curso.setHorarioId(rs.getInt(7));
                curso.setInstructorId(rs.getInt(8));
                curso.setSalonId(rs.getString(9));
                lista.add(curso);
                System.out.println(curso.toString());
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
        tblCurso.setItems(getCursos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colCupoMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colCupoMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colIdCarreraTecnica.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colIdHorario.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colIdInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colIdSalon.setCellValueFactory(new PropertyValueFactory<>("salonId"));
    }
    
        private boolean validaciones() {
        boolean validacion1=true;
        boolean validacion2=true;
        boolean validacion3=true;
        boolean validacion4=true;
        boolean validacion5=true;
        boolean validacion6=true;
        
            if (txtNombreDelCurso.getText().isEmpty()) {
                validacion1=false;
                lblAdvertenciaNombreCurso.setText("CAMPO NECESARIO");
            }
            if ((cmbIdCarreraTecnica.getValue().toString()).equals("")) {
                validacion2=false;
                lblAdvertenciaIdCarreraTecnica.setText("CAMPO NECESARIO");
            }
            if ((cmbIdHorario.getValue().toString()).equals("")) {
                validacion3=false;
                lblAdvertenciaIdHorario.setText("CAMPO NECESARIO");
            }
            if ((cmbIdInstructor.getValue().toString()).equals("")) {
                validacion4=false;
                lblAdvertenciaIdInstructor.setText("CAMPO NECESARIO");
            }
            if ((cmbIdSalon.getValue().toString()).equals("")) {
                validacion5=false;
                lblAdvertenciaIdSalon.setText("CAMPO NECESARIO");
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
    
    private void validacionesfalse(){
        lblAdvertenciaNombreCurso.setText("");
        lblAdvertenciaIdCarreraTecnica.setText("");
        lblAdvertenciaIdInstructor.setText("");
        lblAdvertenciaIdHorario.setText("");
        lblAdvertenciaIdSalon.setText("");
    }    
        
    public boolean existeElementoSeleccionado() {
        return (tblCurso.getSelectionModel().getSelectedItem() != null);
    }
        
    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            cursosSelect = ((Cursos) tblCurso.getSelectionModel().getSelectedItem());
            txtNombreDelCurso.setText(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getNombreCurso());
            spnCiclo.getValueFactory().setValue(Integer.parseInt(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getCiclo()));
            spnCupoMaximo.getValueFactory().setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getCupoMaximo());
            spnCupoMinimo.getValueFactory().setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getCupoMinimo());
            cmbIdCarreraTecnica.setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getCarreraTecnicaId());
            cmbIdHorario.setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getHorarioId());
            cmbIdInstructor.setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getInstructorId());
            cmbIdSalon.setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getSalonId());
        }
    }
}
    
