package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Salones;
import org.in5bm.michaelsalvatierra.bicksongarcia.reports.GenerarReporte;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;

/**
 *
 * @date May 12, 2022
 * @time 10:30:13 AM
 * @author Bill Abel Bickson Garcia Rangel Carne: 2018187 
 * Grado: 5to perito en
 * Informatica Seccion y grupo: IN5BM Grupo 2 (Lunes) 
 * Catedratico: Lic. Jorge  Luis Perez Canto
 */
public class SalonesController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    @FXML
    private Spinner<Integer> spnCapacidad;
    private SpinnerValueFactory<Integer> valueFactoryCapacidad;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Spinner<Integer> spnNivel;
    private SpinnerValueFactory<Integer> valueFactoryNivel;

    @FXML
    private Label lblAdvertenciaCapacidad;
    
    @FXML 
    private Label lblAdvertenciaCodigoDelSalon;

    @FXML
    private TextField txtCodigoDelSalon;

    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapacidadMax;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;

    @FXML
    private Button btnAtras;

    @FXML
    private ImageView imgCrear;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private TextField txtEdificio;
    
    @FXML
    private Label lblTotalSalones;

    private Salones salonSelect;

    Salones salon = new Salones();

    Principal escenarioPrincipal = new Principal();
    private int clickBtnModificar;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    private ObservableList<Salones> listaSalones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        valueFactoryCapacidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spnCapacidad.setValueFactory(valueFactoryCapacidad);

        valueFactoryNivel = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spnNivel.setValueFactory(valueFactoryNivel);
        cargarSalones();
        conteoRegistros();
    }

    @FXML
    private void clickCrear(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                tblSalones.setDisable(true);
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
                    if (agregarSalones()) {
                        tblSalones.setDisable(false);
                        cargarSalones();
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
        switch (operacion) {
            case ACTUALIZAR: //CANCELAR DE ACTUALIZACION
                validacionesfalse();
                limpiarCampos();
                deshabilitarCampos();
                btnCrear.setDisable(false);
                btnReporte.setDisable(false);
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                tblSalones.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Control Academico KINAL");
                    confirmacion.setHeaderText(null);
                    Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "ICONO.png"));
                    confirmacion.setContentText("Realmente desea eliminar al salon : " + "\n" + salonSelect.getCodigoSalon());
                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        deshabilitarCampos();
                        if (eliminarAlumnos()) {
                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                            cargarSalones();
                            limpiarCampos();
                            deshabilitarCampos();
                            conteoRegistros();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Exito");
                            alerta.setHeaderText(null);
                            alerta.setContentText("La eliminacion se ha llevado con exito");
                            alerta.initStyle(StageStyle.UTILITY);
                            salonSelect = null;
                            alerta.showAndWait();
                        }
                    } else {
                        tblSalones.getSelectionModel().clearSelection();
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
                    txtCodigoDelSalon.setDisable(true);
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
                        if (actualizarSalones()) {
                            cargarSalones();
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
                            tblSalones.getSelectionModel().clearSelection();
                            limpiarCampos();
                            deshabilitarCampos();
                        }
                    }
                }
                break;
            case GUARDAR: //CANCELAR EN CREAR
                validacionesfalse();
                tblSalones.setDisable(false);
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
        for (int i = 0; i < listaSalones.size(); i++) {
            total = total + 1;
        }
        lblTotalSalones.setText(String.valueOf(total));
    }
    
    private boolean agregarSalones() {
        Salones salon = new Salones();
        salon.setCodigoSalon(txtCodigoDelSalon.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setCapacidadMaxima(spnCapacidad.getValue());
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel(spnNivel.getValue());

        PreparedStatement pstmt = null;
        try {
            String SQL = "CALL sp_salones_create(?,?,?,?,?);";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);

            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siquiente salon " + salon.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean actualizarSalones() {
        salon.setCodigoSalon(txtCodigoDelSalon.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setCapacidadMaxima(spnCapacidad.getValue());
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel(spnNivel.getValue());

        PreparedStatement pstmt = null;
        try {
            String SQL = "CALL sp_salones_update(?,?,?,?,?);";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);

            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siquiente salon " + salon.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean eliminarAlumnos() {
        if (existeElementoSeleccionado()) {
            salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println("\nA eliminar: " + salon.toString());

            PreparedStatement pstmt = null;
            try {
                String SQL = "CALL sp_salones_delete(?);";
                pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
                pstmt.setString(1, salon.getCodigoSalon());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\n Se produjo un error al tratar de eliminar el siguiente registro " + salonSelect.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
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
        lblAdvertenciaCapacidad.setText("");
        lblAdvertenciaCodigoDelSalon.setText("");
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
        txtCodigoDelSalon.setEditable(true);
        txtDescripcion.setEditable(true);
        txtEdificio.setEditable(true);

        txtCodigoDelSalon.setDisable(false);
        txtDescripcion.setDisable(false);
        spnCapacidad.setDisable(false);
        txtEdificio.setDisable(false);
        spnNivel.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtCodigoDelSalon.setDisable(true);
        txtDescripcion.setDisable(true);
        txtEdificio.setDisable(true);
        spnCapacidad.setDisable(true);
        spnNivel.setDisable(true);
    }

    private void limpiarCampos() {
        txtCodigoDelSalon.setText("");
        txtDescripcion.setText("");
        txtEdificio.setText("");
        spnCapacidad.getValueFactory().setValue(1);
        spnNivel.getValueFactory().setValue(1);
    }

    private void reporte() {
        Map <String, Object > parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACION",PAQUETE_IMAGES+"salones-module.png");
        GenerarReporte.getInstance().mostrarReporte("Salones.jasper", parametros, "Reporte de Salones");
    }

    private ObservableList getSalones() {
        List<Salones> lista = new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "CALL sp_salones_read();";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            while (rs.next() == true) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(Integer.parseInt(rs.getString(3)));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(Integer.parseInt(rs.getString(5)));
                lista.add(salon);
                System.out.println(salon.toString());
            }
            listaSalones = FXCollections.observableArrayList(lista);
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
        return listaSalones;
    }

    public boolean existeElementoSeleccionado() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);
    }

    private boolean validaciones() {
        boolean validacion1 = true;
        boolean validacion2 = true;
        boolean validacion3 = true;
        boolean validacion4 = true;
        boolean validacion5 = true;
        boolean validacion6 = true;
        
        if (spnCapacidad.getValue() == null) {
            validacion1 = false;
            lblAdvertenciaCapacidad.setText("CAMPO NECESARIO");
        }
        if (txtCodigoDelSalon.getText().isEmpty()) {
            validacion2 = false;
            lblAdvertenciaCodigoDelSalon.setText("CAMPO NECESARIO");
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
        if (validacion1 & validacion2 ) {
            return true;
        }
        return false;
    }

    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCodigoDelSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            spnCapacidad.getValueFactory().setValue(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel());
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            spnNivel.getValueFactory().setValue(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel());
            salonSelect = (Salones) tblSalones.getSelectionModel().getSelectedItem();
        }
    }

    private void cargarSalones() {
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidadMax.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
    }
}
