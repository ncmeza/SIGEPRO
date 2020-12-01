/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ncmeza
 */
public class TareaDAO {
    private Tarea tarea;
    private Conexion conexion;

    public TareaDAO(Tarea tarea, Conexion conexion) {
        this.tarea = tarea;
        this.conexion = conexion;
    }
    
    public void agregar(){
    SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        try{
            String sql = "INSERT INTO adm_rec.tarea SET idfase="+ tarea.getIdfase()+ 
                    ", nombre_tarea='"+ tarea.getNombre()+
                    "', descripcion_tarea='"+ tarea.getDescripcion() +
                    "', fecha_inicio='"+ DateFor.format(tarea.getFechaInicio()) +
                    "', fecha_fin='"+ DateFor.format(tarea.getFechaFin()) +
                    "', grado_avance="+ tarea.getGradoAvance() +
                    ", costo_tarea="+ tarea.getCosto()+
                    ", proyecto_fase_idproyecto_fase="+ tarea.getIdproyecto_fase()+";";
            conexion.getSql().execute(sql);
            
            sql = "INSERT INTO adm_rec.personal_tarea SET ";
            conexion.getSql().execute(sql);
            
            System.out.println("Tarea "+tarea.getNombre()+" agregada correctamente a la BD.");
        }catch (SQLException e){
            System.out.println("Error al agregar tarea: "+e);
        }
    }
}
