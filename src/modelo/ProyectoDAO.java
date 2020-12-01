/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ncmeza
 */
public class ProyectoDAO {
    private Proyecto proyecto;
    private Conexion conexion;

    public ProyectoDAO(Proyecto proyecto, Conexion conexion) {
        this.proyecto = proyecto;
        this.conexion = conexion;
    }
    
    public void agregar(){
        try{
            String sql = "INSERT INTO proyecto SET idproyecto="+ proyecto.getIdproyecto()+
                    ", descripcion_proyecto='"+ proyecto.getDescripcion()+
                    //"', promedio_avance="+ proyecto.getAvancePromedio()+
                    "', responsable_proyecto='"+ proyecto.getResponsableProyecto()+
                    "', cliente_idcliente=" + proyecto.getIdcliente()+ ";" ;
            conexion.getSql().execute(sql);
            //Llenamos tabla proyecto_fase con todos los idfase para ese idproyecto
            sql = "INSERT INTO proyecto_fase SET proyecto_idproyecto="+ proyecto.getIdproyecto()+ ", fase_idfase=" + 1 + ";";
            conexion.getSql().execute(sql);
            sql = "INSERT INTO adm_rec.proyecto_fase SET proyecto_idproyecto="+ proyecto.getIdproyecto() + ", fase_idfase=" + 2 + ";";
            conexion.getSql().execute(sql);
            sql = "INSERT INTO adm_rec.proyecto_fase SET proyecto_idproyecto="+ proyecto.getIdproyecto()+ ", fase_idfase=" + 3 + ";";
            conexion.getSql().execute(sql);
            sql = "INSERT INTO adm_rec.proyecto_fase SET proyecto_idproyecto="+ proyecto.getIdproyecto()+ ", fase_idfase=" + 4 + ";";
            conexion.getSql().execute(sql);
            sql = "INSERT INTO adm_rec.proyecto_fase SET proyecto_idproyecto="+ proyecto.getIdproyecto()+ ", fase_idfase=" + 5 + ";";
            conexion.getSql().execute(sql);
            //Recuperamos el idproyecto_fase de la tabla proyecto_fase
            for(Tarea tarea: proyecto.getTareas()){
                sql = "SELECT proyecto_fase.idproyecto_fase FROM proyecto_fase WHERE proyecto_idproyecto="+ proyecto.getIdproyecto()+" AND fase_idfase="+ tarea.getIdfase()+";";
                ResultSet fila = conexion.getSql().executeQuery(sql);
                int idfase_proyecto = 0;
                if(fila.next()){
                    idfase_proyecto = fila.getInt("idproyecto_fase");
                }
                tarea.setIdfase(idfase_proyecto);
                TareaDAO taredao = new TareaDAO(tarea, conexion);
            }
            
            
        }catch(SQLException e){
            System.out.println("No se pudo agregar proyecto: "+e);
        }
    }
}
