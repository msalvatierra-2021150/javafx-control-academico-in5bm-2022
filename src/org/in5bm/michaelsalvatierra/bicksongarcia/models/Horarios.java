package org.in5bm.michaelsalvatierra.bicksongarcia.models;

import java.sql.Time;

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
public class Horarios {
    private int id;
    private Time horarioInicio;
    private Time horarioFinal;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;

    public Horarios() {
    }

    public void setHorarioInicio(Time horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setHorariofinal(Time horarioFinal) {
        this.horarioFinal = horarioFinal;
    }
    
    public Integer getId() {
        return id;
    }
    public Time getHorarioInicio(){
        return horarioInicio;
    }
    public Time getHorarioFinal(){
        return horarioFinal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHorarioFinal(Time horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }
    
    
    public boolean isLunes() {
        return lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public boolean isViernes() {
        return viernes;
    }
    
    @Override
    public String toString() {
        return id + " | horarioInicio=" + horarioInicio + ", horariofinal=" + horarioFinal + ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves + ", viernes=" + viernes;
    }

    
}
