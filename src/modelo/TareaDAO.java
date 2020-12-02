/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author ncmeza
 */
public class TareaDAO {
    private Tarea tarea;
    private Conexion conexion;
    
    public TareaDAO(Conexion conexion){
        this.conexion = conexion;
    }

    public TareaDAO(Tarea tarea, Conexion conexion) {
        this.tarea = tarea;
        this.conexion = conexion;
    }
    
    public void actualizarGradoAvance(ArrayList<Tarea> tareas){
        try{
            for(Tarea tareaTemp: tareas){
                String sql = "UPDATE tarea SET grado_avance="+ tareaTemp.getGradoAvance()+
                        "WHERE idtarea="+ tareaTemp.getIdtarea()+";";
                conexion.getSql().executeUpdate(sql);
            }
            System.out.println("Las tareas se actualizaron correctamente.");
        }catch(SQLException e){
            System.out.println("Las tareas no se actualizaron correctamente: "+e);
        }
    }
    
    public ArrayList<Tarea> buscarTareaPorProyecto(int idproyecto){
        ArrayList<Tarea> tareas = new ArrayList<>();
        try{
            String sql = "SELECT idproyecto_fase FROM proyecto_fase WHERE proyecto_idproyecto="+ idproyecto+";";
            ResultSet fila = conexion.getSql().executeQuery(sql);
            ArrayList<Integer> proyectoFases = new ArrayList<>();
            while(fila.next()){
                proyectoFases.add(fila.getInt("idproyecto_fase"));
            }
            
            for(Integer idproyectofase: proyectoFases){
                sql = "SELECT tarea.* FROM tarea WHERE proyecto_fase_idproyecto_fase="+ idproyectofase;
                ResultSet row = conexion.getSql().executeQuery(sql);
                while(row.next()){
                    Tarea tareaTmp = new Tarea();
                    tareaTmp.setIdtarea(row.getInt("idtarea"));
                    tareaTmp.setIdfase(row.getInt("idfase"));
                    tareaTmp.setNombre(row.getString("nombre_tarea"));
                    tareaTmp.setDescripcion(row.getString("descripcion_tarea"));
                    tareaTmp.setFechaInicio(row.getDate("fecha_inicio"));
                    tareaTmp.setFechaFin(row.getDate("fecha_fin"));
                    tareaTmp.setGradoAvance(row.getInt("grado_avance"));
                    tareaTmp.setCosto(row.getFloat("costo_tarea"));
                    tareas.add(tareaTmp);
                }
            }
        }catch(SQLException e){
            System.out.println("Error al obtener tareas del proyecto: "+e);
        }
        return tareas;
    }
    
    public void agregar(){
    SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        try{
            String sql = "INSERT INTO adm_rec.tarea SET idtarea="+ tarea.getIdtarea()+
                    ", idfase="+ tarea.getIdfase()+ 
                    ", nombre_tarea='"+ tarea.getNombre()+
                    "', descripcion_tarea='"+ tarea.getDescripcion() +
                    "', fecha_inicio='"+ DateFor.format(tarea.getFechaInicio()) +
                    "', fecha_fin='"+ DateFor.format(tarea.getFechaFin()) +
                    "', grado_avance="+ tarea.getGradoAvance() +
                    ", costo_tarea="+ tarea.getCosto()+
                    ", proyecto_fase_idproyecto_fase="+ tarea.getIdproyecto_fase()+";";
            conexion.getSql().execute(sql);
            
            sql = "INSERT INTO adm_rec.personal_tarea SET tarea_idtarea="+ tarea.getIdtarea()+
                    ", personal_legajo="+ tarea.getPersonalLegajo()+";";
            conexion.getSql().execute(sql);
            
            System.out.println("Tarea "+tarea.getNombre()+" agregada correctamente a la BD.");
        }catch (SQLException e){
            System.out.println("Error al agregar tarea: "+e);
        }
    }
}
