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
public class ProyectoDAO {
    private Proyecto proyecto;
    private Conexion conexion;

    public ProyectoDAO(Proyecto proyecto, Conexion conexion) {
        this.proyecto = proyecto;
        this.conexion = conexion;
    }
    
    public void agregar(){
        try{
            String sql = "INSERT INO adrm_rec.proyecto SET descripcion_proyecto='"+ proyecto.getDescripcion()+
                    "', promedio_avance="+ proyecto.getAvancePromedio()+
                    "', responsable_proyecto='"+ proyecto.getResponsableProyecto()+
                    "'";
            
            conexion.getSql().execute(sql);
        }catch(SQLException e){
            System.out.println("No se pudo agregar proyecto: "+e);
        }
    }
}
