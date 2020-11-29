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
    private VistaBuscarCliente vistaBuscarCliente;
    private VistaProyecto vistaProyecto;
    private Conexion conexion;
    private Personal personal;
    private Proyecto nuevoProyecto;
    
    public Controlador(Conexion conexion){
    this.vistaProyecto = new VistaProyecto();
    this.conexion= conexion;
    }
    
    public void ejecutar(){
        vistaProyecto.setControlador(this);
        vistaProyecto.ejecutar();
        nuevoProyecto = new Proyecto();
    }
    
    public void actionPerformed(ActionEvent e){
        
        //Búsqueda cliente
        if(e.getActionCommand().equals(vistaProyecto.BTN_BUSCAR_CLIENTE)){
            vistaBuscarCliente = new VistaBuscarCliente();
            vistaBuscarCliente.ejecutar();
            
            ClienteDAO clientedao = new ClienteDAO(conexion);
            Cliente cliente = clientedao.buscarClientePorCuit(vistaBuscarCliente.getCuit());
            vistaBuscarCliente.setRazonSocial(cliente.getRazonSocial());
        }
        
        
    }  
}
