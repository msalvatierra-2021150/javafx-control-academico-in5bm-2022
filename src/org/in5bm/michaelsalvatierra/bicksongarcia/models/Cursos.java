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

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getNombreCurso() {
        return nombreCurso;
    }

    public IntegerProperty getCiclo() {
        return ciclo;
    }

    public IntegerProperty getCupoMaximo() {
        return cupoMaximo;
    }

    public IntegerProperty getCupoMinimo() {
        return cupoMinimo;
    }

    public StringProperty getCarreraTecnicaId() {
        return carreraTecnicaId;
    }

    public IntegerProperty getHorarioId() {
        return horarioId;
    }

    public IntegerProperty getInstructorId() {
        return instructorId;
    }

    public StringProperty getSalonId() {
        return salonId;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public void setNombreCurso(StringProperty nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setCiclo(IntegerProperty ciclo) {
        this.ciclo = ciclo;
    }

    public void setCupoMaximo(IntegerProperty cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public void setCupoMinimo(IntegerProperty cupoMinimo) {
        this.cupoMinimo = cupoMinimo;
    }

    public void setCarreraTecnicaId(StringProperty carreraTecnicaId) {
        this.carreraTecnicaId = carreraTecnicaId;
    }

    public void setHorarioId(IntegerProperty horarioId) {
        this.horarioId = horarioId;
    }

    public void setInstructorId(IntegerProperty instructorId) {
        this.instructorId = instructorId;
    }

    public void setSalonId(StringProperty salonId) {
        this.salonId = salonId;
    }
    
    
   
    @Override
    public String toString() {
        return "Cursos{" + "id=" + id + ", nombreCurso=" + nombreCurso + ", ciclo=" + ciclo + ", cupoMaximo=" + cupoMaximo + ", cupoMinimo=" + cupoMinimo + ", carreraTecnicaId=" + carreraTecnicaId + ", horarioId=" + horarioId + ", instructorId=" + instructorId + ", salonId=" + salonId + '}';
    }
    
    
}
