package org.in5bm.michaelsalvatierra.bicksongarcia.models;

/**
 * @date May 11,2022
 * @time 9:35:47 AM
 * @author Bill Abel Bickson Garcia Rangel
 * Carne: 2018187
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class CarrerasTecnicas {
    private String codigoTecnico;
    private String carrera;
    private String grado;
    private String seccion;
    private String jornada;

    public CarrerasTecnicas(String codigoTecnico, String carrera, String grado, String seccion, String jornada) {
        this.codigoTecnico = codigoTecnico;
        this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
    }

    public CarrerasTecnicas() {
    }

    
    public String getCodigoTecnico() {
        return codigoTecnico;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getGrado() {
        return grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getJornada() {
        return jornada;
    }

    public void setCodigoTecnico(String codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return "CarrerasTecnicas{" + "codigoTecnico=" + codigoTecnico + ", carrera=" + carrera + ", grado=" + grado + ", seccion=" + seccion + ", jornada=" + jornada + '}';
    }
    
    
}
