/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.*;
import modelo.Conexion;

/**
 *
 * @author Leonel
 */
public class Controlador implements ActionListener {
    
    private VistaProyecto vistaProyecto;
    private VistaBuscarCliente vistaBuscarCliente;
    private VistaAgregarTarea vistaAgregarTarea;
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
            vistaBuscarCliente.setControlador(this);
            vistaBuscarCliente.ejecutar();
            
            
        }
        
        if(e.getActionCommand().equals(vistaBuscarCliente.BTN_BUSCAR)){
            ClienteDAO clientedao = new ClienteDAO(conexion);
            Cliente cliente = clientedao.buscarClientePorCuit(vistaBuscarCliente.getCuit());
            vistaBuscarCliente.setIdCliente(cliente.getIdcliente());
            System.out.println(""+cliente.getRazonSocial());
            vistaBuscarCliente.setRazonSocial(cliente.getRazonSocial());
            vistaBuscarCliente.setEmail(cliente.getEmail());
            vistaBuscarCliente.setTelefono(""+cliente.getTelefono());
            vistaBuscarCliente.setProvincia(cliente.getProvincia());
            vistaBuscarCliente.setLocalidad(cliente.getLocalidad());
        }
        
        if(e.getActionCommand().equals(vistaBuscarCliente.BTN_ACEPTAR_CLIENTE)){
            nuevoProyecto.setIdcliente(vistaBuscarCliente.getIdCliente());
            vistaProyecto.setCuit(vistaBuscarCliente.getCuit());
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_CREAR_PROYECTO)){
            nuevoProyecto.setIdproyecto(vistaProyecto.getIdProyecto());
            nuevoProyecto.setDescripcion(vistaProyecto.getDescripcion());
            nuevoProyecto.setResponsableProyecto(vistaProyecto.getResponsable());          
            
            ProyectoDAO proyectodao = new ProyectoDAO(nuevoProyecto, conexion);
            proyectodao.agregar();
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_AGREGAR_TAREA)){
            vistaAgregarTarea = new VistaAgregarTarea();
            vistaAgregarTarea.setControlador(this);
            vistaAgregarTarea.ejecutar();
        }
        
        if(e.getActionCommand().equals(vistaAgregarTarea.BTN_AGREGAR_TAREA)){
            Tarea tarea = new Tarea();
            
            tarea.setNombre(vistaAgregarTarea.getNombreTarea());
            tarea.setDescripcion(vistaAgregarTarea.getDescripcionTarea());
            tarea.setIdfase(vistaAgregarTarea.getFase());
            tarea.setCosto(vistaAgregarTarea.getCostoTarea());
            tarea.setFechaInicio(vistaAgregarTarea.getFechaInicio());
            tarea.setFechaFin(vistaAgregarTarea.getFechaFin());
            tarea.setGradoAvance(vistaAgregarTarea.getGradoAvance());
            
//            nuevoProyecto.getTareas().add(tarea);
//            TareaDAO tareadao=new TareaDAO(tarea,conexion);
//            tareadao.agregar();
            
        }
    }
    
    public ArrayList<String[]> listaTareas(){
        ArrayList lista = new ArrayList();
        for(Tarea tarea: nuevoProyecto.getTareas()){
            String[] fila = new String[5];
            fila[0]=""+ tarea.getIdtarea();
            fila[1] = ""+ tarea.getIdfase();
            fila[2] = ""+ tarea.getNombre();
            fila[3] = ""+ tarea.getFechaInicio();
            fila[4] = ""+ tarea.getFechaFin();
            fila[5] = ""+ tarea.getCosto();
            lista.add(fila);
        }
        return lista;
    }
}
