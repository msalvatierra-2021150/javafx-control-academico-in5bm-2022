package org.in5bm.michaelsalvatierra.bicksongarcia.models;


/**
 * @date Apr 11, 2022
 * @time 5:34:28 PM
 *  @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class Alumnos {
    private String carne;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    
    //AlumnosController alumnosController = new AlumnosController();
    
    public Alumnos(String nombre1, String apellido1) {
        //this.carne = alumnosController.setCarne();
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
    }

    public Alumnos(String nombre1, String nombre2, String nombre3, String apellido1, String apellido2) {
        //this.carne = alumnosController.setCarne();
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public Alumnos() {
    }
    
    
    public String getCarne() {
        return carne;
    }

    public String getNombre1() {
        return nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public String getNombre3() {
        return nombre3;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setCarne(String carne){
        this.carne = carne;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    @Override
    public String toString() {
        return carne + " | " + nombre1+" "+ apellido1  ;
    }
    
    
}
