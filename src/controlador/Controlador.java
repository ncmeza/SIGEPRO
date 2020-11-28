/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.*;
import modelo.Conexion;

/**
 *
 * @author Leonel
 */
public class Controlador implements ActionListener {
    private VistaPersonal vistaPersonal;
    private Conexion conexion;
    private Personal personal;
    
    public Controlador(Conexion conexion){
    
    vistaPersonal = new VistaPersonal();
    this.conexion= conexion;
    }
    
    public void ejecutar(){
    vistaPersonal.setControlador(this);
    vistaPersonal.ejecutar();
    }
    
    
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals(vistaPersonal.BTN_AGREGAR_PERSONAL)){
            personal=new Personal(vistaPersonal.getLegajo(),vistaPersonal.getApellido(),vistaPersonal.getNombre(),
            vistaPersonal.getDNI(),vistaPersonal.getRolPersonal());
            System.out.println(personal);
            PersonalDAO personalDAO =new PersonalDAO(conexion,personal);
            personalDAO.agregar();
        }
    }  
}
