/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
/**
 *
 * @author ncmeza
 */
public class Tarea {
    
    private int idtarea;
    private int idfase;
    private int idproyecto_fase;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private int gradoAvance;
    private float costo;
    private int idpersonal;
    
    public Tarea(){
        
    }

    public Tarea(String nombre, String descripcion, Date fechaInicio, Date fechaFin, int gradoAvance, float costo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.gradoAvance = gradoAvance;
        this.costo = costo;
    }

    public int getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(int idtarea) {
        this.idtarea = idtarea;
    }

    public int getIdfase() {
        return idfase;
    }

    public void setIdfase(int idfase) {
        this.idfase = idfase;
    }

    public int getIdproyecto_fase() {
        return idproyecto_fase;
    }

    public void setIdproyecto_fase(int idproyecto_fase) {
        this.idproyecto_fase = idproyecto_fase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getGradoAvance() {
        return gradoAvance;
    }

    public void setGradoAvance(int gradoAvance) {
        this.gradoAvance = gradoAvance;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getIdpersonal() {
        return idpersonal;
    }

    public void setIdpersonal(int idpersonal) {
        this.idpersonal = idpersonal;
    }

    @Override
    public String toString() {
        return "Tarea{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", gradoAvance=" + gradoAvance + ", costo=" + costo + '}';
    }
    
    
}
