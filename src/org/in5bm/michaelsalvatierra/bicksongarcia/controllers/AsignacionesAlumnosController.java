package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.time.LocalDate;
import java.sql.Timestamp;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.in5bm.michaelsalvatierra.bicksongarcia.models.AsignacionesAlumnos;
import org.in5bm.michaelsalvatierra.bicksongarcia.system.Principal;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Alumnos;
import org.in5bm.michaelsalvatierra.bicksongarcia.models.Cursos;
/**
 *
 * @date Apr 19, 2022
 * @time 11:23:18 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */

public class AsignacionesAlumnosController implements Initializable {
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox <Alumnos> cmbCarneAlumno;
    @FXML
    private ComboBox <Cursos> cmbIdCurso;
    @FXML
    private DatePicker dpkFechaDeAsignacion;
    @FXML
    private Label lblId;
    @FXML
    private Label lblAdvertenciaCarne;
    @FXML
    private Label lblAdvertenciaCursoId;
    @FXML
    private TableView <AsignacionesAlumnos> tblAsignacionDeAlumnos;
    @FXML
    private TableColumn <AsignacionesAlumnos, Integer> colId;
    @FXML
    private TableColumn <Alumnos, String> colnombreAlumno;
    @FXML
    private TableColumn <Cursos, String> colNombreCurso;
    @FXML
    private TableColumn <AsignacionesAlumnos,Integer>  colCarneAlumno;
    @FXML
    private TableColumn <AsignacionesAlumnos,LocalDate> colFechaAsignacion;
    @FXML
    private TableColumn <AsignacionesAlumnos,Integer> colIdCurso;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnGuardar;
    @FXML
    private ImageView imgCrear;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    
    private AsignacionesAlumnos AsignacionesSelect = new AsignacionesAlumnos();
    private ObservableList<AsignacionesAlumnos> listaAsignacionesAlumnos;
    
    private ObservableList<Alumnos> listaAlumnos;
    private ObservableList<Cursos> listaCursos;
    Principal escenarioPrincipal = new Principal();

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarAsignacionesAlumnos();
    }    
    
     private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";

        
   @FXML
    private void clickCrear(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                tblAsignacionDeAlumnos.setDisable(true);
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
                    if (agregarAsignacionesAlumnos()) {
                        tblAsignacionDeAlumnos.setDisable(false);
                        cargarAsignacionesAlumnos();
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
        switch (operacion) {
            case ACTUALIZAR: //CANCELAR DE ACTUALIZACION
                validacionesfalse();
                limpiarCampos();
                deshabilitarCampos();
                btnCrear.setDisable(false);
                btnReporte.setDisable(false);
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "button-modificar.png"));
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "button-eliminar.png"));
                tblAsignacionDeAlumnos.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacion.setTitle("Control Academico KINAL");
                    confirmacion.setHeaderText(null);
                    Stage stage = (Stage) confirmacion.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "ICONO.png"));
                    confirmacion.setContentText("Realmente desea eliminar la asignacion : " + "\n" + AsignacionesSelect.getId());
                    Optional<ButtonType> result = confirmacion.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        deshabilitarCampos();
                        if (eliminarAsignacion()) {
                            listaAsignacionesAlumnos.remove(tblAsignacionDeAlumnos.getSelectionModel().getFocusedIndex());
                            cargarAsignacionesAlumnos();
                            limpiarCampos();
                            deshabilitarCampos();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Exito");
                            alerta.setHeaderText(null);
                            alerta.setContentText("La eliminacion se ha llevado con exito");
                            alerta.initStyle(StageStyle.UTILITY);
                            AsignacionesSelect = null;
                            alerta.showAndWait();
                        }
                    } else {
                        tblAsignacionDeAlumnos.getSelectionModel().clearSelection();
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
                        if (actualizarAsignaciones()) {
                            cargarAsignacionesAlumnos();
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
                            tblAsignacionDeAlumnos.getSelectionModel().clearSelection();
                            limpiarCampos();
                            deshabilitarCampos();
                        }
                    }
                }
                break;
            case GUARDAR: //CANCELAR EN CREAR
                validacionesfalse();
                tblAsignacionDeAlumnos.setDisable(false);
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
        cmbCarneAlumno.setDisable(false);
        dpkFechaDeAsignacion.setDisable(false);
        cmbIdCurso.setDisable(false);
    }
    
    private void deshabilitarCampos() {
        lblId.setDisable(true);
        cmbCarneAlumno.setDisable(true);
        dpkFechaDeAsignacion.setDisable(true);
        cmbIdCurso.setDisable(true);
    }
    
    private void limpiarCampos() {
        lblId.setText("");
        cmbCarneAlumno.valueProperty().set(null);
        cmbIdCurso.valueProperty().set(null);
        dpkFechaDeAsignacion.getEditor().clear();
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
    
        
    private boolean actualizarAsignaciones() {
        AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
        asignacion.setId(Integer.parseInt(lblId.getText()));
        asignacion.setAlumnoId((cmbCarneAlumno.getSelectionModel().getSelectedItem()).getCarne());
        asignacion.setCursoId((cmbIdCurso.getSelectionModel().getSelectedItem()).getId());
        asignacion.setFechaAsignacion(dpkFechaDeAsignacion.getValue().atStartOfDay());
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="{CALL sp_asignaciones_update(?,?,?,?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setInt(1,asignacion.getId());
            pstmt.setString(2,asignacion.getAlumnoId());
            pstmt.setInt(3,asignacion.getCursoId());
            pstmt.setTimestamp(4, Timestamp.valueOf(asignacion.getFechaAsignacion()));
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaAsignacionesAlumnos.add(asignacion);
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar insertar la siquiente asignacion "+asignacion.toString());
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
    
    private boolean agregarAsignacionesAlumnos() {
        AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
        asignacion.setAlumnoId((cmbCarneAlumno.getSelectionModel().getSelectedItem()).getCarne());
        asignacion.setCursoId((cmbIdCurso.getSelectionModel().getSelectedItem()).getId());
        asignacion.setFechaAsignacion(dpkFechaDeAsignacion.getValue().atStartOfDay());
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="{CALL sp_asignaciones_create(?,?,?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setString(1,asignacion.getAlumnoId());
            pstmt.setInt(2,asignacion.getCursoId());
            pstmt.setTimestamp(3, Timestamp.valueOf(asignacion.getFechaAsignacion()));
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaAsignacionesAlumnos.add(asignacion);
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar insertar la siquiente asignacion "+asignacion.toString());
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
    
    public boolean eliminarAsignacion(){
        AsignacionesAlumnos asignacion = ((AsignacionesAlumnos) tblAsignacionDeAlumnos.getSelectionModel().getSelectedItem());
        PreparedStatement pstmt = null;
        try{
                String SQL = "{CALL sp_asignaciones_delete(?)}";
                pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
                pstmt.setInt(1, asignacion.getId());
                System.out.println(pstmt);
                pstmt.execute();
                listaAsignacionesAlumnos.remove(tblAsignacionDeAlumnos.getSelectionModel().getFocusedIndex());
                return true;
            }catch(SQLException e){
                System.err.println("\n Se produjo un error al tratar de eliminar el siguiente registro "+asignacion.toString());
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
        return false;
    }
        
    private ObservableList getAsignacionesAlumnos() {
       ArrayList<AsignacionesAlumnos> arrayListAsignaciones = new ArrayList<>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           String SQL = "{CALL sp_asignaciones_read()}";
           pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
           rs = pstmt.executeQuery();
           while(rs.next()){
               AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
               asignacion.setId(rs.getInt(1));
               asignacion.setAlumnoId(rs.getString(2));
               asignacion.setCursoId(rs.getInt(3));
               asignacion.setFechaAsignacion(rs.getTimestamp(4).toLocalDateTime());
               System.out.println(asignacion.toString());
               arrayListAsignaciones.add(asignacion);
           }
           listaAsignacionesAlumnos = FXCollections.observableArrayList(arrayListAsignaciones);
        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de Asignaciones Alumnos");
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
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaAsignacionesAlumnos;
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
    
    private Alumnos buscarAlumnos(String id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Alumnos alumno = new Alumnos();
        try {
            String SQL = "{CALL sp_alumnos_read_by(?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
            pstmt.setString(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));
                System.out.println(alumno.toString());
            }    
            System.out.println("");           
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
                if(pstmt != null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return alumno;
    }
    
    private ObservableList getCursos() {
        List<Cursos> lista = new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "{CALL sp_cursos_read()}";
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            while (rs.next() == true) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt(1));
                curso.setNombreCurso(rs.getString(2));
                curso.setCiclo(rs.getInt(3));
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
    
    private Cursos buscarCursos(int id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = new Cursos();
        try {
            String SQL = "{CALL sp_cursos_read_by(?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
            pstmt.setInt(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                curso.setId(rs.getInt(1));
                curso.setNombreCurso(rs.getString(2));
                curso.setCiclo(rs.getInt(3));
                curso.setCupoMaximo(rs.getInt(4));
                curso.setCupoMinimo(rs.getInt(5));
                curso.setCarreraTecnicaId(rs.getString(6));
                curso.setHorarioId(rs.getInt(7));
                curso.setInstructorId(rs.getInt(8));
                curso.setSalonId(rs.getString(9));
                System.out.println(curso.toString());
            }    
            System.out.println("");           
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
                if(pstmt != null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return curso;        
    }
    
    private void cargarAsignacionesAlumnos() {
        tblAsignacionDeAlumnos.setItems(getAsignacionesAlumnos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCarneAlumno.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        colIdCurso.setCellValueFactory(new PropertyValueFactory<>("cursoId"));
        colFechaAsignacion.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        
        ObservableList<Alumnos> listaAlumnos = FXCollections.observableArrayList(getAlumnos());
        cmbCarneAlumno.getItems().addAll(listaAlumnos);
        
        ObservableList<Cursos> listaCursos = FXCollections.observableArrayList(getCursos());
        cmbIdCurso.getItems().addAll(listaCursos);
    }
    
        private boolean validaciones() {
        boolean validacion1=true;
        boolean validacion2=true;
        boolean validacion3=true;
        boolean validacion4=true;
        boolean validacion5=true;
        boolean validacion6=true;
        
            if ((cmbCarneAlumno.getSelectionModel().getSelectedItem() == null)) {
                validacion1=false;
                lblAdvertenciaCarne.setText("CAMPO NECESARIO");
            }
            if ((cmbIdCurso.getSelectionModel().getSelectedItem() == null)) {
                validacion2=false;
                lblAdvertenciaCursoId.setText("CAMPO NECESARIO");
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

            if (validacion1 & validacion2) {
                return true;
            }
            return false;
    }
    
    private void validacionesfalse(){
        lblAdvertenciaCarne.setText("");
        lblAdvertenciaCursoId.setText("");
    }    
        
    public boolean existeElementoSeleccionado() {
        return (tblAsignacionDeAlumnos.getSelectionModel().getSelectedItem() != null);
    }
        
    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            AsignacionesSelect = (AsignacionesAlumnos) tblAsignacionDeAlumnos.getSelectionModel().getSelectedItem();
            lblId.setText(String.valueOf(((AsignacionesAlumnos) tblAsignacionDeAlumnos.getSelectionModel().getSelectedItem()).getId()));
            cmbCarneAlumno.getSelectionModel().select(buscarAlumnos(((AsignacionesAlumnos)tblAsignacionDeAlumnos.getSelectionModel().getSelectedItem()).getAlumnoId()));
            cmbIdCurso.getSelectionModel().select(buscarCursos(((AsignacionesAlumnos)tblAsignacionDeAlumnos.getSelectionModel().getSelectedItem()).getCursoId()));
            dpkFechaDeAsignacion.setValue(((AsignacionesAlumnos) tblAsignacionDeAlumnos.getSelectionModel().getSelectedItem()).getFechaAsignacion().toLocalDate());
        }
    }     
}
