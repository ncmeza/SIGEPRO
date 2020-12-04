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
    private VistaInicioSesion vistaInicioSesion;
    private VistaModificarTarea vistaModificarTarea;
    private VistaMostrarDatos vistaMostrarDatos;
    private VistaAgregarTareaNueva vistaAgregarTareaNueva;
    private Conexion conexion;
    private Personal personalDesarrollo; //esto es temporal, se tiene que instanciar en el login
    private Proyecto nuevoProyecto;
    private Proyecto proyectoBuscado;
    
    public Controlador(Conexion conexion){
    this.vistaProyecto = new VistaProyecto();
    this.vistaDesarrollador = new VistaDesarrollador();
    this.vistaInicioSesion = new VistaInicioSesion();
    this.conexion= conexion;
    }
    
    public void ejecutar(){
      vistaInicioSesion.setControlador(this);
      vistaInicioSesion.ejecutar();
//        vistaProyecto.setControlador(this);
//        vistaProyecto.ejecutar();
//        vistaDesarrollador.setControlador(this);
//        vistaDesarrollador.ejecutar();
//        nuevoProyecto = new Proyecto();
    }
    
    public void actionPerformed(ActionEvent e){
        //Inicio sesion
        if(e.getActionCommand().equals(vistaInicioSesion.BTN_INGRESAR)){
            Personal personal = personalLogin(vistaInicioSesion.getUsuario(),vistaInicioSesion.getContraseña());
            if(personal.getRolPersonal() == 1){
                vistaProyecto = new VistaProyecto();
                nuevoProyecto = new Proyecto();
                vistaProyecto.setControlador(this);
                vistaProyecto.ejecutar();
            }else{
                vistaDesarrollador = new VistaDesarrollador();
                vistaDesarrollador.setControlador(this);
                vistaDesarrollador.ejecutar();
                personalDesarrollo = personal;
            }
        }
        
        //Búsqueda cliente
        if(e.getActionCommand().equals(vistaProyecto.BTN_BUSCAR_CLIENTE)){
            vistaBuscarCliente = new VistaBuscarCliente();
            vistaBuscarCliente.setControlador(this);
            vistaBuscarCliente.ejecutar(); 
        }
        
        if(e.getActionCommand().equals(vistaBuscarCliente.BTN_BUSCAR)){
            ClienteDAO clientedao = new ClienteDAO(conexion);
            Cliente cliente = clientedao.buscarClientePorCuit(vistaBuscarCliente.getCuit());
            if(cliente.getRazonSocial() != null){
                vistaBuscarCliente.setIdCliente(cliente.getIdcliente());
                System.out.println(""+cliente.getRazonSocial());
                vistaBuscarCliente.setRazonSocial(cliente.getRazonSocial());
                vistaBuscarCliente.setEmail(cliente.getEmail());
                vistaBuscarCliente.setTelefono(""+cliente.getTelefono());
                vistaBuscarCliente.setProvincia(cliente.getProvincia());
                vistaBuscarCliente.setLocalidad(cliente.getLocalidad());
            }else{
                JOptionPane.showMessageDialog(null, "Cliente invalido");
            }
            
        }
        
        if(e.getActionCommand().equals(vistaBuscarCliente.BTN_ACEPTAR_CLIENTE)){
            if(vistaBuscarCliente.getIdCliente() != 0){
                nuevoProyecto.setIdcliente(vistaBuscarCliente.getIdCliente());
                vistaProyecto.setCuit(vistaBuscarCliente.getCuit());
            }else{
                JOptionPane.showMessageDialog(null,"Se debe Ingresar un cliente" );
            }        
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_CREAR_PROYECTO)){
            
            nuevoProyecto.setIdproyecto(vistaProyecto.getIdProyecto());
            nuevoProyecto.setDescripcion(vistaProyecto.getDescripcion());
            nuevoProyecto.setResponsableProyecto(vistaProyecto.getResponsable());          
            
            ProyectoDAO proyectodao = new ProyectoDAO(nuevoProyecto, conexion);
            proyectodao.agregar();
            nuevoProyecto = new Proyecto();
            JOptionPane.showMessageDialog(null, "Se creo el proyecto con sus tareas exitosamente");
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_AGREGAR_TAREA)){
            vistaAgregarTarea = new VistaAgregarTarea();
            vistaAgregarTarea.setControlador(this);
            vistaAgregarTarea.ejecutar();
            vistaAgregarTarea.setIdTarea(ultimoIDdeTarea());
            vistaAgregarTarea.cargarTareas(listaTareas(nuevoProyecto));
            vistaAgregarTarea.cargarPersonal(listaPersonal());
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_BUSCAR_PROYECTO)){
            proyectoBuscado = buscarProyectoPorID(vistaProyecto.getIdProyectoBuscarPro());
            vistaProyecto.setDescripcionBuscarPro(proyectoBuscado.getDescripcion());
            vistaProyecto.setAvancePromBuscarPro(proyectoBuscado.getAvancePromedio());
            vistaProyecto.setCostoProyectoBuscarPro(proyectoBuscado.getCostoProyecto());
            vistaProyecto.setResponsableBuscarPro(proyectoBuscado.getResponsableProyecto());
            vistaProyecto.setCuitBuscarPro(buscarClientePorID(proyectoBuscado.getIdcliente()).getCuit());
            vistaProyecto.cargarListaDeTareas(listaTareas(proyectoBuscado));
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_MODIFICAR_PROYECTO)){
            proyectoBuscado.setDescripcion(vistaProyecto.getDescripcionBuscarPro());
            proyectoBuscado.setCostoProyecto(vistaProyecto.getCostoProyectoBuscarPro());
            proyectoBuscado.setResponsableProyecto(vistaProyecto.getResponsable());
            actualizarProyecto(proyectoBuscado);
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_MODIFICAR_TAREA)){
            Tarea tarea = buscarTareaPorID(vistaProyecto.getIDtareaParaActualizar());
            vistaModificarTarea = new VistaModificarTarea();
            vistaModificarTarea.setControlador(this);
            vistaModificarTarea.ejecutar();
            vistaModificarTarea.setIdTarea(tarea.getIdtarea());
            vistaModificarTarea.setNombreTarea(tarea.getNombre());
            vistaModificarTarea.setDescripcionTarea(tarea.getDescripcion());
            vistaModificarTarea.setFase(tarea.getIdfase());
            vistaModificarTarea.setFechaInicio(tarea.getFechaInicio().toString());
            vistaModificarTarea.setFechaFin(tarea.getFechaFin().toString());
            vistaModificarTarea.setGradoAvance(tarea.getGradoAvance());
            vistaModificarTarea.setCostoTarea(tarea.getCosto());
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_ELIMINAR_PROYECTO)){
            eliminarProyecto(proyectoBuscado);
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_AGREGAR_TAREA_NUEVA)){
            vistaAgregarTareaNueva = new VistaAgregarTareaNueva();
            vistaAgregarTareaNueva.setControlador(this);
            vistaAgregarTareaNueva.ejecutar();
            vistaAgregarTareaNueva.setIdTarea(ultimoIDdeTarea());
            vistaAgregarTareaNueva.cargarTareasNuevo(listaTareas(proyectoBuscado));
            vistaAgregarTareaNueva.cargarPersonalNuevo(listaPersonal());
        }
        
        if(e.getActionCommand().equals(vistaAgregarTareaNueva.BTN_AGREGAR_TAREA_NUEVA)){
            Tarea tarea = new Tarea();
            if(vistaAgregarTareaNueva.getLegajoPersonal()>0){
                if(!existeLaTareaEnNuevoProyecto(vistaAgregarTareaNueva.getIdTarea())){
                    if(vistaAgregarTareaNueva.getGradoAvance()<100 && vistaAgregarTareaNueva.getGradoAvance()>0){
                        tarea.setIdtarea(vistaAgregarTareaNueva.getIdTarea());
                        tarea.setNombre(vistaAgregarTareaNueva.getNombreTarea());
                        tarea.setDescripcion(vistaAgregarTareaNueva.getDescripcionTarea());
                        tarea.setIdfase(vistaAgregarTareaNueva.getFase());
                        tarea.setCosto(vistaAgregarTareaNueva.getCostoTarea());
                        tarea.setFechaInicio(vistaAgregarTareaNueva.getFechaInicio());
                        tarea.setFechaFin(vistaAgregarTareaNueva.getFechaFin());
                        tarea.setGradoAvance(vistaAgregarTareaNueva.getGradoAvance());           
                        tarea.setPersonalLegajo(vistaAgregarTareaNueva.getLegajoPersonal());
                        proyectoBuscado.getTareas().add(tarea);
                        //vistaProyecto.setCostoProyecto(Float.toString(nuevoProyecto.calcularCosto()));
                        vistaAgregarTareaNueva.cargarTareasNuevo(listaTareas(proyectoBuscado));
                        cargarNuevaTarea();
                        vistaProyecto.cargarListaDeTareas(listaTareas(proyectoBuscado));
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "No puede contener una grado de avance superior a 100 o menor a 0");
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(null, "La tarea ya existe");
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Se debe asignar personal a la tarea");
            }
        }
        
        //if()
        
        if(e.getActionCommand().equals(vistaModificarTarea.BTN_MODIFICAR_TAREA)){
            Tarea tarea = buscarTareaPorID(vistaModificarTarea.getIdTarea());
            tarea.setNombre(vistaModificarTarea.getNombreTarea());
            tarea.setDescripcion(vistaModificarTarea.getNombreTarea());
            tarea.setIdfase(vistaModificarTarea.getFase());
            tarea.setFechaInicio(vistaModificarTarea.getFechaInicio());
            tarea.setFechaFin(vistaModificarTarea.getFechaFin());
            tarea.setGradoAvance(vistaModificarTarea.getGradoAvance());
            tarea.setCosto(vistaModificarTarea.getCostoTarea());
            vistaProyecto.cargarListaDeTareas(listaTareas(proyectoBuscado));
            actualizarTareaVistaProyecto(tarea);
            proyectoBuscado = buscarProyectoPorID(vistaProyecto.getIdProyectoBuscarPro());
            vistaProyecto.setDescripcionBuscarPro(proyectoBuscado.getDescripcion());
            vistaProyecto.setAvancePromBuscarPro(proyectoBuscado.getAvancePromedio());
            vistaProyecto.setCostoProyectoBuscarPro(proyectoBuscado.getCostoProyecto());
            vistaProyecto.setResponsableBuscarPro(proyectoBuscado.getResponsableProyecto());
            vistaProyecto.setCuitBuscarPro(buscarClientePorID(proyectoBuscado.getIdcliente()).getCuit());
            vistaProyecto.cargarListaDeTareas(listaTareas(proyectoBuscado));
        }
        
        if(e.getActionCommand().equals(vistaAgregarTarea.BTN_AGREGAR_TAREA)){
            Tarea tarea = new Tarea();
            if(vistaAgregarTarea.getLegajoPersonal()>0){
                if(!existeLaTareaEnNuevoProyecto(vistaAgregarTarea.getIdTarea())){
                    if(vistaAgregarTarea.getGradoAvance()<100 && vistaAgregarTarea.getGradoAvance()>0){
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
                        vistaProyecto.setCostoProyecto(Float.toString(nuevoProyecto.calcularCosto()));
                        vistaAgregarTarea.cargarTareas(listaTareas(nuevoProyecto));
                    }else{
                        JOptionPane.showMessageDialog(null, "No puede contener una grado de avance superior a 100 o menor a 0");
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(null, "La tarea ya existe");
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Se debe asignar personal a la tarea");
            }
        }
        
        if(e.getActionCommand().equals(vistaAgregarTarea.BTN_ELIMINAR_TAREA)){
            eliminarTareaDeNuevoProyecto(vistaAgregarTarea.getIDtareaParaBorrar());
            vistaAgregarTarea.cargarTareas(listaTareas(nuevoProyecto));
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
            actualizarTareaVistaDesarrollador();
            vistaDesarrollador.cargarListaDeTareas(tareasDeUnDesarrollador(proyectoBuscado));
        }
        
        if(e.getActionCommand().equals(vistaProyecto.BTN_AGREGAR_TAREA_DE_BUSCAR)){
            vistaMostrarDatos = new VistaMostrarDatos();
            vistaMostrarDatos.ejecutar();
            vistaMostrarDatos.cargarListaProyectos(listaProyectos());           
        }
        
           
    } 
        
        //METODOS UTILES
    
    private int ultimoIDdeTarea(){
        //Hacer consulta en base de datos
        return 0;
    }
    
    private void eliminarTareaDeNuevoProyecto(int idTarea){
        for(int i = 0; i < nuevoProyecto.getTareas().size(); i++){
            if(nuevoProyecto.getTareas().get(i).getIdtarea() == idTarea){
                nuevoProyecto.getTareas().remove(i);
            }
        }
    }
    
    private boolean existeLaTareaEnNuevoProyecto(int id){
        boolean resultado = false;
        for(Tarea tarea: nuevoProyecto.getTareas()){
            if(tarea.getIdtarea() == id){
                resultado = true;
            }
        }
        return resultado;
    }
    
    private ArrayList<String[]> listaTareas(Proyecto proyecto){
        ArrayList lista = new ArrayList();
        for(Tarea tarea: proyecto.getTareas()){
            String[] fila = new String[7];
            fila[0]=""+ tarea.getIdtarea();
            fila[1] = ""+ tarea.getIdfase();
            fila[2] = ""+ tarea.getNombre();
            fila[3] = ""+ tarea.getFechaInicio();
            fila[4] = ""+ tarea.getFechaFin();
            fila[5] = ""+ tarea.getCosto();
            fila[6] = ""+tarea.getGradoAvance();
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
        return clienteDAO.buscarClientePorId(idCliente);
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
    
    public void actualizarTareaVistaDesarrollador(){
        ProyectoDAO proyectodao = new ProyectoDAO(conexion);
        proyectodao.actualizarAvancePromedio(proyectoBuscado);
    }
    
    public void actualizarProyecto(Proyecto proyecto){
        ProyectoDAO proyectoDAO = new ProyectoDAO(conexion);
        proyectoDAO.modificarProyecto(proyectoBuscado);
    }
    
    public void actualizarTareaVistaProyecto(Tarea tarea){
        TareaDAO tareaDAO = new TareaDAO(conexion);
        ProyectoDAO proyectoDAO = new ProyectoDAO(conexion);
        tareaDAO.modificarTarea(tarea);
        proyectoDAO.modificarProyecto(proyectoBuscado);
        
    }
    
    public Personal personalLogin(String usuario, String contra){
        PersonalDAO personalDAO = new PersonalDAO(conexion);
        return personalDAO.login(usuario, contra);
    }
    
    public void eliminarProyecto(Proyecto proyecto){
        ProyectoDAO proyectoDAO = new ProyectoDAO(conexion);
        proyectoDAO.eliminarProyecto(proyecto);
    }
    
    public ArrayList<String[]> listaProyectos(){
        
        ArrayList lista = new ArrayList();
        ProyectoDAO proyectoDAO = new ProyectoDAO(conexion);
        for(Proyecto proyecto: proyectoDAO.buscarProyectos()){
            String[] fila = new String[7];
            fila[0]=""+ proyecto.getIdproyecto();
            fila[1] = ""+ proyecto.getDescripcion();
            fila[2] = ""+ proyecto.getAvancePromedio();
            fila[3] = ""+ proyecto.getCostoProyecto();
            fila[4] = ""+ proyecto.getResponsableProyecto();
            fila[5] = ""+ proyecto.getIdcliente();
            fila[6] = ""+ buscarClientePorID(proyecto.getIdcliente()).getRazonSocial();
            
            lista.add(fila);
        }
        return lista;
    }
    
    public void cargarNuevaTarea(){
        TareaDAO tareaDAO = new TareaDAO(conexion);
        tareaDAO.agregarTareaNueva(proyectoBuscado);
    }
}
