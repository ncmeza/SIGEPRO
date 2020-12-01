/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Leonel
 */
public class PersonalDAO {
    private Personal personal;
    private Conexion conexion;

    public PersonalDAO( Conexion conexion, Personal personal) {
        this.personal = personal;
        this.conexion = conexion;
    }
    
    public ArrayList<Personal> buscarTodoPersonalConLegajo(){
        try{
            ArrayList<Personal> personales = new ArrayList<>();
            String sql = "SELECT personal.legajo, personal.appellido, personal.nombre FROM personal;";
            ResultSet fila = conexion.getSql().executeQuery(sql);
            while(fila.next()){
                personal = new Personal();
                personal.setLegajo(fila.getInt("legajo"));
                personal.setApellido(fila.getString("apellido"));
                personal.setNombre(fila.getString("nombre"));
            }
            System.out.println("Personal recuperado correctamente.");
            return personales;
        }catch(SQLException e){
            System.out.println("No se pudo encontrar personal buscado.");
        }
        return null;
    }


}

