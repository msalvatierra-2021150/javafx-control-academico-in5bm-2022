package org.in5bm.michaelsalvatierra.bicksongarcia.controllers;

import java.sql.PreparedStatement;
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
    
    private ObservableList<CarrerasTecnicas> listaCarrera;
    private ObservableList<Horarios> listaHorarios;
    private ObservableList<Instructores> listaInstructor;
    private ObservableList<Salones> listaSalones;
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
                    if (agregarCursos()) {
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
                        if (eliminarCursos()) {
                            listaCursos.remove(tblCurso.getSelectionModel().getFocusedIndex());
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
                        }
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
                        if (actualizarCursos()) {
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
                        }
                    }
                }
                break;
            case GUARDAR: //CANCELAR EN CREAR
                limpiarCampos();
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
    
    private boolean agregarCursos() {
        Cursos curso = new Cursos();
        curso.setNombreCurso(txtNombreDelCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());
        curso.setCarreraTecnicaId(cmbIdCarreraTecnica.getSelectionModel().getSelectedItem().getCodigoTecnico());
        curso.setHorarioId(cmbIdHorario.getSelectionModel().getSelectedItem().getId());
        curso.setInstructorId(cmbIdInstructor.getSelectionModel().getSelectedItem().getId());
        curso.setSalonId(cmbIdSalon.getSelectionModel().getSelectedItem().getCodigoSalon());
        
        PreparedStatement pstmt = null;
        try{
            String SQL ="{CALL sp_cursos_create(?,?,?,?,?,?,?,?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setString(1,curso.getNombreCurso());
            pstmt.setInt(2,curso.getCiclo());
            pstmt.setInt(3,curso.getCupoMaximo());
            pstmt.setInt(4,curso.getCupoMinimo());
            pstmt.setString(5,curso.getCarreraTecnicaId());
            pstmt.setInt(6,curso.getHorarioId());
            pstmt.setInt(7,curso.getInstructorId());
            pstmt.setString(8,curso.getSalonId());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar insertar el siquiente alumno "+curso.toString());
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
    
    private boolean eliminarCursos(){
        if(existeElementoSeleccionado()){
            Cursos curso = (Cursos)tblCurso.getSelectionModel().getSelectedItem(); 
            System.out.println("\nA eliminar: "+curso.toString());
            
            PreparedStatement pstmt= null;
            try{
                String SQL = "{CALL sp_cursos_delete(?)}";
                pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
                pstmt.setInt(1, curso.getId());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
            }catch(SQLException e){
                System.err.println("\n Se produjo un error al tratar de eliminar el siguiente registro "+curso.toString());
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
         
    private boolean actualizarCursos(){
        Cursos curso = new Cursos();
        curso.setId(Integer.parseInt(lblId.getText()));
        curso.setNombreCurso(txtNombreDelCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());
        curso.setCarreraTecnicaId(cmbIdCarreraTecnica.getSelectionModel().getSelectedItem().getCodigoTecnico());
        curso.setHorarioId(cmbIdHorario.getSelectionModel().getSelectedItem().getId());
        System.out.println("Horario "+curso.getHorarioId());
        curso.setInstructorId(cmbIdInstructor.getSelectionModel().getSelectedItem().getId());
        curso.setSalonId(cmbIdSalon.getSelectionModel().getSelectedItem().getCodigoSalon());
        PreparedStatement pstmt = null;
        try{
            String SQL ="{CALL sp_cursos_update(?,?,?,?,?,?,?,?,?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
           
            pstmt.setInt(1,curso.getId());
            pstmt.setString(2,curso.getNombreCurso());
            pstmt.setInt(3,curso.getCiclo());
            pstmt.setInt(4,curso.getCupoMaximo());
            pstmt.setInt(5,curso.getCupoMinimo());
            pstmt.setString(6,curso.getCarreraTecnicaId());
            pstmt.setInt(7,curso.getHorarioId());
            pstmt.setInt(8,curso.getInstructorId());
            pstmt.setString(9,curso.getSalonId());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("\nSe produjo un error al intentar modificar el siquiente alumno "+curso.toString());
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
    
    private ObservableList getCarreraTecnica(){
        List <CarrerasTecnicas> lista= new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "{CALL sp_carreras_tecnicas_read()}";
            
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            System.out.println("");
            while(rs.next() == true){
                CarrerasTecnicas carrerasTecnicas = new CarrerasTecnicas();
                carrerasTecnicas.setCodigoTecnico(rs.getString(1));
                carrerasTecnicas.setCarrera(rs.getString(2));
                carrerasTecnicas.setGrado(rs.getString(3));
                carrerasTecnicas.setSeccion(rs.getString(4));
                carrerasTecnicas.setJornada(rs.getString(5));
                lista.add(carrerasTecnicas);
            }
            System.out.println("");
            listaCarrera= FXCollections.observableArrayList(lista);
            
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
        return listaCarrera;
    }
        
    private Instructores buscarInstructores(int id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Instructores instructor = new Instructores();
        try {
            String SQL = "{CALL sp_carreras_tecnicas_read_by(?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
            pstmt.setInt(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){

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
                System.out.println(instructor.toString());
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
        return instructor;        
    }
    
    private ObservableList getHorario(){
        List <Horarios> lista= new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "{CALL sp_horarios_read()}";
            
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            System.out.println("");
            while(rs.next() == true){
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2));
                horario.setHorarioFinal(rs.getTime(3));
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                lista.add(horario);
            }
            System.out.println("");
             listaHorarios= FXCollections.observableArrayList(lista);
            
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
        return listaHorarios;
    }
    
    private ObservableList getInstructores(){
        List <Instructores> lista= new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "{CALL sp_instructores_read()}";
            
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            System.out.println("");
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

            }
            System.out.println("");
             listaInstructor= FXCollections.observableArrayList(lista);
            
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
        return listaInstructor;
    }
    
    private ObservableList getSalones(){
        List <Salones> lista= new ArrayList<>();
        CallableStatement sentencia = null;
        ResultSet rs = null;
        try {
            String SQL = "{CALL sp_salones_read()}";
            
            sentencia = Conexion.getInstance().getConnection().prepareCall(SQL);
            rs = sentencia.executeQuery();
            System.out.println("");
            while(rs.next() == true){
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(Integer.parseInt(rs.getString(3)));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(Integer.parseInt(rs.getString(5)));
                lista.add(salon);
                System.out.println(salon.toString());   
            }
            System.out.println("");
             listaSalones= FXCollections.observableArrayList(lista);
            
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
        return listaSalones;
    }
          
        
    private CarrerasTecnicas buscarCarrerasTecnicas(String id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        try {
            String SQL = "{CALL sp_carreras_tecnicas_read_by(?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
            pstmt.setString(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                carreraTecnica.setCodigoTecnico(rs.getString(1));
                carreraTecnica.setCarrera(rs.getString(2));
                carreraTecnica.setGrado(rs.getString(3));
                carreraTecnica.setSeccion(rs.getString(4));
                carreraTecnica.setJornada(rs.getString(5));
                System.out.println(carreraTecnica.toString());         
            }    
            System.out.println("");           
        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de buscar carreras tecnicas");
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
        return carreraTecnica;        
    }        
    
    private Horarios buscarHorarios(int id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Horarios horario = new Horarios();
        try {
            String SQL = "{CALL sp_horarios_read_by(?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
            pstmt.setInt(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2));
                horario.setHorariofinal(rs.getTime(3));
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                System.out.println(horario.toString());  
            }    
            System.out.println("");           
        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de buscar Horarios");
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
        return horario;        
    }      
    
    private Instructores buscarInstructor(int id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Instructores instructor = new Instructores();
        try {
            String SQL = "{CALL sp_instructores_read_by(?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
            pstmt.setInt(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
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
            }    
            System.out.println("");           
        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de isntructores");
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
        return instructor;        
    }     
    
    private Salones buscarSalon(String id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Salones salon = new Salones();
        try {
            String SQL = "{CALL sp_salones_read_by(?)}";
            pstmt = Conexion.getInstance().getConnection().prepareCall(SQL);
            pstmt.setString(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(Integer.parseInt(rs.getString(3)));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(Integer.parseInt(rs.getString(5)));
                System.out.println(salon.toString());
            }    
            System.out.println("");           
        } catch (SQLException e) {
            System.err.println("\nSe Produjo u error al intentar consultarla lista de buscar salones");
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
        return salon;        
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
        
        ObservableList<CarrerasTecnicas> listaCarrera = FXCollections.observableArrayList(getCarreraTecnica());
        cmbIdCarreraTecnica.getItems().addAll(listaCarrera);
        
        ObservableList<Horarios> listaHorario = FXCollections.observableArrayList(getHorario());
        cmbIdHorario.getItems().addAll(listaHorario);
        
        
        
        ObservableList<Instructores> listaInstructor = FXCollections.observableArrayList(getInstructores());
        cmbIdInstructor.getItems().addAll(listaInstructor);
        
        ObservableList<Salones> listaSalon = FXCollections.observableArrayList(getSalones());
        cmbIdSalon.getItems().addAll(listaSalon);
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
            if (cmbIdCarreraTecnica.getSelectionModel().getSelectedItem() == null) {
                validacion2=false;
                lblAdvertenciaIdCarreraTecnica.setText("CAMPO NECESARIO");
            }
            if (cmbIdHorario.getSelectionModel().getSelectedItem() == null) {
                validacion3=false;
                lblAdvertenciaIdHorario.setText("CAMPO NECESARIO");
            }
            if (cmbIdInstructor.getSelectionModel().getSelectedItem() == null) {
                validacion4=false;
                lblAdvertenciaIdInstructor.setText("CAMPO NECESARIO");
            }
            if (cmbIdSalon.getSelectionModel().getSelectedItem() == null) {
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
            lblId.setText(String.valueOf(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getId()));
            txtNombreDelCurso.setText(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getNombreCurso());
            spnCiclo.getValueFactory().setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getCiclo());
            spnCupoMaximo.getValueFactory().setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getCupoMaximo());
            spnCupoMinimo.getValueFactory().setValue(((Cursos) tblCurso.getSelectionModel().getSelectedItem()).getCupoMinimo());
            cmbIdCarreraTecnica.getSelectionModel().select(buscarCarrerasTecnicas(((Cursos)tblCurso.getSelectionModel().getSelectedItem()).getCarreraTecnicaId()));
            cmbIdHorario.getSelectionModel().select(buscarHorarios(((Cursos)tblCurso.getSelectionModel().getSelectedItem()).getHorarioId()));
            cmbIdInstructor.getSelectionModel().select(buscarInstructor(((Cursos)tblCurso.getSelectionModel().getSelectedItem()).getInstructorId()));
            cmbIdSalon.getSelectionModel().select(buscarSalon(((Cursos)tblCurso.getSelectionModel().getSelectedItem()).getSalonId()));
        }
        
    
    }
}
    
