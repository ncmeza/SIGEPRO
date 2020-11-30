/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author ncmeza
 */
public class Proyecto {
    private int idproyecto;
    private String descripcion;
    private float avancePromedio;
    private float costoProyecto;
    private String responsableProyecto;
    private boolean visibilidad;
    private int idcliente;
    private ArrayList<Integer> fases;
    private ArrayList<Tarea> tareas;
    
    public Proyecto(){
        //Constructor vacio intencional
        fases.add(1);
        fases.add(2);
        fases.add(3);
        fases.add(4);
        fases.add(5);
        tareas = new ArrayList<Tarea>();
    }

    public Proyecto(String descripción, float avancePromedio, float costoProyecto, String ResponsableProyecto) {
        this.descripcion = descripción;
        this.avancePromedio = avancePromedio;
        this.costoProyecto = costoProyecto;
        this.responsableProyecto = ResponsableProyecto;
        this.visibilidad = true;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }
    
    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripción) {
        this.descripcion = descripción;
    }

    public float getAvancePromedio() {
        return avancePromedio;
    }

    public void setAvancePromedio(float avancePromedio) {
        this.avancePromedio = avancePromedio;
    }

    public float getCostoProyecto() {
        return costoProyecto;
    }

    public void setCostoProyecto(float costoProyecto) {
        this.costoProyecto = costoProyecto;
    }

    public String getResponsableProyecto() {
        return responsableProyecto;
    }

    public void setResponsableProyecto(String responsableProyecto) {
        this.responsableProyecto = responsableProyecto;
    }

    public boolean isVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    @Override
    public String toString() {
        return "Proyecto{" + "descripcion" + descripcion + ", avancePromedio=" + avancePromedio + ", costoProyecto=" + costoProyecto + ", responsableProyecto=" + responsableProyecto + ", visibilidad=" + visibilidad + '}';
    }   
    
}
