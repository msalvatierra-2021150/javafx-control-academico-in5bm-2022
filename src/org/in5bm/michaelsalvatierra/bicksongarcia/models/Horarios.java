package org.in5bm.michaelsalvatierra.bicksongarcia.models;

import java.sql.Time;

/**
 *
 * @date Apr 11, 2022
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
    private byte lunes;
    private byte martes;
    private byte miercoles;
    private byte jueves;
    private byte viernes;

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

    public byte getLunes() {
        return lunes;
    }

    public byte getMartes() {
        return martes;
    }

    public byte getMiercoles() {
        return miercoles;
    }

    public byte getJueves() {
        return jueves;
    }

    public byte getViernes() {
        return viernes;
    }

    public void setId(int id) {
        this.id=id;
    }

    public void setLunes(byte lunes) {
        this.lunes = lunes;
    }

    public void setMartes(byte martes) {
        this.martes = martes;
    }

    public void setMiercoles(byte miercoles) {
        this.miercoles = miercoles;
    }

    public void setJueves(byte jueves) {
        this.jueves = jueves;
    }

    public void setViernes(byte viernes) {
        this.viernes = viernes;
    }
    
    
    @Override
    public String toString() {
        return "Horarios{" + "id=" + id + ", horarioInicio=" + horarioInicio + ", horariofinal=" + horarioFinal + ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves + ", viernes=" + viernes + '}';
    }
    
}
