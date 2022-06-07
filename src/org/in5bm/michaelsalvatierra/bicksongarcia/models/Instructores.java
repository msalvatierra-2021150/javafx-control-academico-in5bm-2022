package org.in5bm.michaelsalvatierra.bicksongarcia.models;

/**
 *
 * @date Apr 11, 2022
 * @time 5:54:32 PM
 * @author Bill Abel Bickson Garcia Rangel
 * Carne: 2018187
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class Instructores {

    private int id;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private String email;
    private String telefono;
    private String fechaDeNacimiento;

    public Instructores() {
    }

    public Instructores(int id, String nombre1, String apellido1, String email, String telefono) {
        this.id = id;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
        this.email = email;
        this.telefono = telefono;
    }

    public Instructores(int id, String nombre1, String nombre2, String nombre3, String apellido1, String apellido2, String direccion, String email, String telefono, String fechaDeNacimiento) {
        this.id = id;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    
    public int getId() {
        return id;
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

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    @Override
    public String toString() {
        return "Instructores{" + "id=" + id + ", nombre1=" + nombre1 + ", nombre2=" + nombre2 + ", nombre3=" + nombre3 + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + ", fechaDeNacimiento=" + fechaDeNacimiento + '}';
    }
    
}
