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
    
    public ProyectoDAO(Conexion conexion){
        this.conexion = conexion;
    }

    public ProyectoDAO(Proyecto proyecto, Conexion conexion) {
        this.proyecto = proyecto;
        this.conexion = conexion;
    }
    
    public void modificarProyecto(Proyecto proyecto){
        TareaDAO tareadao = new TareaDAO(conexion);
        try{
            tareadao.actualizarGradoAvance(proyecto.getTareas());
            tareadao.actualizarCostoTarea(proyecto.getTareas());
            String sql = "UPDATE proyecto SET descripcion_proyecto='"+ proyecto.getDescripcion()+
                    "', promedio_avance="+ proyecto.getAvancePromedio()+
                    ", costo_proyecto="+ proyecto.getCostoProyecto()+
                    ", responsable_proyecto='"+ proyecto.getResponsableProyecto()+"';";
            conexion.getSql().executeUpdate(sql);
            System.out.println("Proyecto: "+proyecto.getDescripcion()+" actualizado correctamente.");
        }catch(SQLException e){
            System.out.println("Error al actualizar proyecto: "+e);
        }
    }
    
    public void actualizarAvancePromedio(Proyecto proyecto){
        TareaDAO tareadao = new TareaDAO(conexion);
        try{
            tareadao.actualizarGradoAvance(proyecto.getTareas());
            String sql = "UPDATE proyecto SET promedio_avance="+ proyecto.calcularGradoAvance()+
                    " WHERE idproyecto="+proyecto.getIdproyecto()+";";
            conexion.getSql().executeUpdate(sql);
            //actualizarGradoAvance lo pasé para arriba
            System.out.println("El promedio avance de proyecto "+proyecto.getDescripcion()+"  se actualizó correctamente.");
        }catch(SQLException e){
            System.out.println("El promedio avance de proyecto "+proyecto.getDescripcion()+" no se actualizó correctamente:"
                    + "\n"+e);
        }
    }
    
    public Proyecto buscarProyecto(int idproyecto){
        Proyecto proyectoTmp = new Proyecto();
        TareaDAO tareadao = new TareaDAO(conexion);
        try{
            String sql = "SELECT proyecto.* FROM proyecto WHERE proyecto.idproyecto="+ idproyecto +";"; //crear columna visibilidad
            ResultSet fila = conexion.getSql().executeQuery(sql);
            
            if(fila.next()){
                proyectoTmp.setIdproyecto(idproyecto);
                proyectoTmp.setDescripcion(fila.getString("descripcion_proyecto"));
                proyectoTmp.setAvancePromedio(fila.getFloat("promedio_avance"));
                proyectoTmp.setCostoProyecto(fila.getFloat("costo_proyecto"));
                proyectoTmp.setResponsableProyecto(fila.getString("responsable_proyecto"));
                proyectoTmp.setIdcliente(fila.getInt("cliente_idcliente"));
                proyectoTmp.setTareas(tareadao.buscarTareaPorProyecto(idproyecto));
            }
        }catch(SQLException e){
            
        }
        return proyectoTmp;
    }
    
    public void agregar(){
        try{
            String sql = "INSERT INTO proyecto SET idproyecto="+ proyecto.getIdproyecto()+
                    ", descripcion_proyecto='"+ proyecto.getDescripcion()+
                    "', promedio_avance="+ proyecto.calcularGradoAvance()+
                    ", costo_proyecto="+ proyecto.calcularCosto()+
                    ", responsable_proyecto='"+ proyecto.getResponsableProyecto()+
                    "', visibilidad="+ proyecto.getVisibilidad() + 
                    ", cliente_idcliente=" + proyecto.getIdcliente()+ ";";
            conexion.getSql().execute(sql);
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
                tarea.setIdproyecto_fase(idfase_proyecto);
                TareaDAO tareadao = new TareaDAO(tarea, conexion);
                tareadao.agregar();
            }
        }catch(SQLException e){
            System.out.println("No se pudo agregar proyecto: "+e);
        }
    }
}
