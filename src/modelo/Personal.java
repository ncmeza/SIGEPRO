/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author ncmeza
 */
public class Personal {
    
    private int legajo;
    private String apellido;
    private String nombre;
    private int dni;
   // private RolPersonal rolPersonal;
    private int RolPersonal;
    
    public Personal(){
        //Constructor vacio a proposito
    }

    public Personal(int legajo, String apellido, String nombre, int dni, int rolPersonal) {
        this.legajo = legajo;
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.RolPersonal= rolPersonal;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    /*public RolPersonal getRolPersonal() {
        return rolPersonal;
    }

    public void setRolPersonal(RolPersonal rolPersonal) {
        this.rolPersonal = rolPersonal;
    }*/

    public void setRolPersonal(int RolPersonal) {
        this.RolPersonal = RolPersonal;
    }

    public int getRolPersonal() {
        return RolPersonal;
    }
    

    @Override
    public String toString() {
        return "Personal{" + "legajo=" + legajo + ", apellido=" + apellido + ", nombre=" + nombre + ", dni=" + dni + ", rolPersonal=" + RolPersonal + '}';
    }
    
    
}
