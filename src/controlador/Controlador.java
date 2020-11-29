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
    private VistaProyecto vistaProyecto;
    private Conexion conexion;
    private Personal personal;
    
    public Controlador(Conexion conexion){
    
    this.conexion= conexion;
    }
    
    public void ejecutar(){
    
    }
    
    
    public void actionPerformed(ActionEvent e){
        
        
        
    }  
}
