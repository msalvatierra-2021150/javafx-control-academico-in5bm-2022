package org.in5bm.michaelsalvatierra.bicksongarcia.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDateTime;

/**
 * @date May 10 , 2022
 * @time 7:45:47 PM
 * @author Bill Abel Bickson Garcia Rangel
 * Carne: 2018187
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class AsignacionesAlumnos {
    private IntegerProperty id;
    private StringProperty alumnoId;
    private IntegerProperty cursoId;
    private ObjectProperty<LocalDateTime> fechaAsignacion;

    public AsignacionesAlumnos() {
        this.id = new SimpleIntegerProperty();
        this.alumnoId = new SimpleStringProperty();
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty();
    }
    
    public AsignacionesAlumnos(int id, String alumnoId, int cursoId, LocalDateTime fechaAsignacion) {
        this.id = new SimpleIntegerProperty(id);
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }
    
    public IntegerProperty Id(){
        return id;
    }
    
    public int getId(){     
       return id.get(); 
    }
    
    public void setId(int id){    
        this.id.set(id);
    }
    
    public StringProperty alumnoId(){
        return alumnoId;
    }
    
    public String getAlumnoId(){
        return alumnoId.get();
    }
    
    public void setAlumnoId(String alumnoId){
        this.alumnoId.set(alumnoId);
    }
    
    public IntegerProperty cursoId(){
        return cursoId;
    }
    
    public int getCursoId(){
        return cursoId.get();
    }
    
    public void setCursoId(int cursoId){
        this.cursoId.set(cursoId);
    }
    
    public ObjectProperty<LocalDateTime> fechaAsignacion(){
        return fechaAsignacion;
    }
    
    public LocalDateTime getFechaAsignacion(){
        return fechaAsignacion.get();
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion){
        this.fechaAsignacion.set(fechaAsignacion);
    }
    
    @Override
    public String toString() {
        return "AsignacionesAlumnos{" + "id=" + id.get() + ", alumnoId=" + alumnoId.get() + ", cursoId=" + cursoId.get() + ", fechaAsignacion=" + fechaAsignacion.get() + '}';
    }


    
    
}
