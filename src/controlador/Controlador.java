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
import javax.swing.JOptionPane;
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
    private VistaDesarrollador vistaDesarrollador;
    private Conexion conexion;
    private Personal personalDesarrollo = new Personal(); //esto es temporal, se tiene que instanciar en el login
    private Proyecto nuevoProyecto;
    private Proyecto proyectoBuscado;
    
    public Controlador(Conexion conexion){
    this.vistaProyecto = new VistaProyecto();
    this.vistaDesarrollador = new VistaDesarrollador();
    this.conexion= conexion;
    }
    
    public void ejecutar(){
        vistaProyecto.setControlador(this);
        vistaProyecto.ejecutar();
        vistaDesarrollador.setControlador(this);
        vistaDesarrollador.ejecutar();
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
            vistaAgregarTarea.cargarTareas(listaTareas());
            vistaAgregarTarea.cargarPersonal(listaPersonal());
        }
        
        if(e.getActionCommand().equals(vistaAgregarTarea.BTN_AGREGAR_TAREA)){
            Tarea tarea = new Tarea();
            if(vistaAgregarTarea.getLegajoPersonal()>0){
                tarea.setIdtarea(vistaAgregarTarea.getIdTarea());
                tarea.setNombre(vistaAgregarTarea.getNombreTarea());
                tarea.setDescripcion(vistaAgregarTarea.getDescripcionTarea());
                tarea.setIdfase(vistaAgregarTarea.getFase());
                tarea.setCosto(vistaAgregarTarea.getCostoTarea());
                tarea.setFechaInicio(vistaAgregarTarea.getFechaInicio());
                tarea.setFechaFin(vistaAgregarTarea.getFechaFin());
                tarea.setGradoAvance(vistaAgregarTarea.getGradoAvance());           
                tarea.setPersonalLegajo(vistaAgregarTarea.getLegajoPersonal());
                nuevoProyecto.getTareas().add(tarea);
            }else{
                JOptionPane.showMessageDialog(vistaAgregarTarea, "Se debe asignar personal a la tarea");
            }
            
//            nuevoProyecto.getTareas().add(tarea);
//            TareaDAO tareadao=new TareaDAO(tarea,conexion);
//            tareadao.agregar();
        }
        //VISTA DEL DESARROLLADOR
        if(e.getActionCommand().equals(vistaDesarrollador.BTN_BUSCAR_PROYECTO)){
            proyectoBuscado = buscarProyectoPorID(vistaDesarrollador.getIdProyecto());
            vistaDesarrollador.setDescripcion(proyectoBuscado.getDescripcion());
            vistaDesarrollador.setCostoProyecto(""+proyectoBuscado.getCostoProyecto());
            vistaDesarrollador.setResponsable(proyectoBuscado.getResponsableProyecto());
            vistaDesarrollador.setCuitCliente(buscarClientePorID(proyectoBuscado.getIdcliente()).getCuit());
            vistaDesarrollador.setPromedioAvance(proyectoBuscado.getAvancePromedio());
            vistaDesarrollador.cargarListaDeTareas(tareasDeUnDesarrollador(proyectoBuscado));
            
        }
        
        if(e.getActionCommand().equals(vistaDesarrollador.BTN_SELECCIONAR_TAREA)){
            Tarea tareaSeleccionada = buscarTareaPorID(vistaDesarrollador.getIdTareaSeleccionada());
            
            vistaDesarrollador.setIDTarea(tareaSeleccionada.getIdtarea());
            vistaDesarrollador.setNombreTarea(tareaSeleccionada.getNombre());
            vistaDesarrollador.setDescripcionTarea(tareaSeleccionada.getDescripcion());
            vistaDesarrollador.setGradoAvance(tareaSeleccionada.getGradoAvance());
            vistaDesarrollador.setCostoTarea(tareaSeleccionada.getCosto());
        }
        
        if(e.getActionCommand().equals(vistaDesarrollador.BTN_ACTUALIZAR_TAREA)){
            Tarea tareaActualizada = buscarTareaPorID(vistaDesarrollador.getIDTarea());
            tareaActualizada.setGradoAvance(vistaDesarrollador.getGradoAvance());
            actualizarTarea();
        }
           
    } 
        
        //METODOS UTILES
    
    private ArrayList<String[]> listaTareas(){
        ArrayList lista = new ArrayList();
        for(Tarea tarea: nuevoProyecto.getTareas()){
            String[] fila = new String[6];
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
    
    private ArrayList<String[]> listaPersonal(){
        ArrayList lista = new ArrayList();
        PersonalDAO personalDAO = new PersonalDAO(conexion);
        for(Personal personal: personalDAO.buscarTodoPersonalConLegajo()){
            String[] fila = new String[3];
            fila[0]=""+ personal.getLegajo();
            fila[1] = ""+ personal.getNombre();
            fila[2] = ""+ personal.getApellido();
            lista.add(fila);
        }
        return lista;
    }
    
    private Proyecto buscarProyectoPorID(int idProyecto){
        ProyectoDAO proyecto = new ProyectoDAO(conexion);       
        return proyecto.buscarProyecto(idProyecto);
    }
    
    private Cliente buscarClientePorID(int idCliente){
        ClienteDAO clienteDAO = new ClienteDAO(conexion);
        return clienteDAO.buscarClientePorCuit(idCliente);
    }
    
    private ArrayList<String[]> tareasDeUnDesarrollador(Proyecto proyecto){
        ArrayList<Tarea> tareasDelDesarrollador = new ArrayList<>();
        ArrayList<String[]> filasTareas = new ArrayList<>();
        for(Tarea tarea: proyecto.getTareas()){
            if(tarea.getPersonalLegajo() == personalDesarrollo.getLegajo()){
                tareasDelDesarrollador.add(tarea);
            }
        }
        
        for(Tarea tarea: tareasDelDesarrollador){
            String[] fila = new String[5];
            fila[0] = ""+tarea.getIdtarea();
            fila[1] = tarea.getNombre();
            fila[2] = tarea.getDescripcion();
            fila[3] = ""+tarea.getGradoAvance();
            fila[4] = ""+tarea.getCosto();
            
            filasTareas.add(fila);
        }
        return filasTareas;
    }
    
    public Tarea buscarTareaPorID(int IDtarea){
        Tarea resultado = new Tarea();
        for(Tarea tarea: proyectoBuscado.getTareas()){
            if(tarea.getIdtarea()==IDtarea){
                resultado = tarea;
            }
        }
        return resultado;
    }
    
    public void actualizarTarea(){
        
    }
}
