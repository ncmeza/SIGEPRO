/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.SQLException;

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
    
        try{
            String sql = "INSERT INTO adm_rec.tarea SET nombre_tarea='"+ tarea.getNombre()+
                    "', descripcion_tarea='"+ tarea.getDescripcion() +
                    "', fecha_inicio="+ tarea.getFechaInicio() +
                    ", fecha_fin='"+ tarea.getFechaFin() +
                    ", grado_avance="+ tarea.getGradoAvance() +
                    ", costo_tarea="+ tarea.getCosto();
            //Falta ver qué pasa con el idprotecto_fase
        
            conexion.getSql().execute(sql);
            System.out.println("Tarea "+tarea.getNombre()+" agregada correctamente a la BD.");
        }catch (SQLException e){
            System.out.println("Erro al agregar tarea: "+e);
        }
    }
}
