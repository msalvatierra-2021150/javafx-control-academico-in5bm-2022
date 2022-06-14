package org.in5bm.michaelsalvatierra.bicksongarcia.models;

/**
 *
 * @date Apr 11, 2022
 * @time 6:51:23 PM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
public class Salones {
    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;

    public Salones() {
    }

    public Salones(String codigoSalon, int capacidadMaxima) {
        this.codigoSalon = codigoSalon;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Salones(String codigSalon, String descripcion, int capacidadMaxima, String edificio, int nivel) {
        this.codigoSalon = codigSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    public String getCodigoSalon() {
        return codigoSalon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public String getEdificio() {
        return edificio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return codigoSalon + " | " + descripcion + " | "+ edificio+ " nivel "+ nivel;
    }
    
}
