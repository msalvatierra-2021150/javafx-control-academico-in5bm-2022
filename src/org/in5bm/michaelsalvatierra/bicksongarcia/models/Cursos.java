package org.in5bm.michaelsalvatierra.bicksongarcia.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author W10
 * @date Apr 11, 2022
 * @time 11:23:18 AM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class Cursos {
    private IntegerProperty id;
    private StringProperty nombreCurso;
    private IntegerProperty ciclo;
    private IntegerProperty cupoMaximo;
    private IntegerProperty cupoMinimo;
    private StringProperty carreraTecnicaId;
    private IntegerProperty horarioId;
    private IntegerProperty instructorId;
    private StringProperty salonId;

    public Cursos() {
        this.id = new SimpleIntegerProperty();
        this.nombreCurso = new SimpleStringProperty();
        this.ciclo = new SimpleIntegerProperty();
        this.cupoMaximo = new SimpleIntegerProperty();
        this.cupoMinimo = new SimpleIntegerProperty();
        this.carreraTecnicaId = new SimpleStringProperty();
        this.horarioId = new SimpleIntegerProperty();
        this.instructorId = new SimpleIntegerProperty();
        this.salonId = new SimpleStringProperty();
    }

    public Integer getId() {
        return id.get();
    }

    public String getNombreCurso() {
        return nombreCurso.get();
    }

    public Integer getCiclo() {
        return ciclo.get();
    }

    public Integer getCupoMaximo() {
        return cupoMaximo.get();
    }

    public Integer getCupoMinimo() {
        return cupoMinimo.get();
    }

    public String getCarreraTecnicaId() {
        return carreraTecnicaId.get();
    }

    public Integer getHorarioId() {
        return horarioId.get();
    }

    public Integer getInstructorId() {
        return instructorId.get();
    }

    public String getSalonId() {
        return salonId.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso.set(nombreCurso);
    }

    public void setCiclo(int ciclo) {
        this.ciclo.set(ciclo);
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo.set(cupoMaximo);
    }

    public void setCupoMinimo(int cupoMinimo) {
        this.cupoMinimo.set(cupoMinimo);
    }

    public void setCarreraTecnicaId(String carreraTecnicaId) {
        this.carreraTecnicaId.set(carreraTecnicaId);
    }

    public void setHorarioId(int horarioId) {
        this.horarioId.set(horarioId);
    }

    public void setInstructorId(int instructorId) {
        this.instructorId.set(instructorId);
    }

    public void setSalonId(String salonId) {
        this.salonId.set(salonId);
    }
    
    public IntegerProperty Id(){
        return id;
    }
    
    public StringProperty Nombre(){
        return nombreCurso;
    }
    
    public IntegerProperty ciclo(){
        return ciclo;
    }
    
    public IntegerProperty cupoMaximo(){
        return cupoMaximo;
    }
    
    public IntegerProperty cupoMinimo(){
        return cupoMinimo;
    }
    
    public StringProperty carreraTecnicaId(){
        return carreraTecnicaId;
    }
    
    public IntegerProperty horarioId(){
        return horarioId;
    }
    
    public IntegerProperty instructorId(){
        return instructorId;
    }
    public StringProperty salonId(){
        return  salonId;
    }
     
    
    @Override
    public String toString() {
        return id.get() +" | "+ nombreCurso.get() ;
    }
    
    
}
