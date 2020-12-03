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
    private Conexion conexion;

    public PersonalDAO( Conexion conexion) {
        this.conexion = conexion;
    }
    
    public Personal login(String usuario, String clave){
        //retornar un personal que coincida con clave y usuario, crear columnas clave, usuario en la tabla
        
        try{
            String sql = "SELECT personal.* FROM personal WHERE usuario='"+ usuario +"' AND clave='"+ clave +"';";
            ResultSet fila = conexion.getSql().executeQuery(sql);
            Personal personal = new Personal();
            if(fila.next()){
                personal.setLegajo(fila.getInt("legajo"));
                personal.setApellido(fila.getString("apellido"));
                personal.setNombre(fila.getString("nombre"));
                personal.setDni(fila.getInt("dni"));
                personal.setRolPersonal(fila.getInt("rol_personal_idrol_personal"));
            }
            System.out.println("El personal del login fue recuperado correctamente.");
            return personal;
        }catch(SQLException e){
            System.out.println("El personal del login NO fue recuperado correctamente: "+e);
        }
        return null;
    }
    
    public ArrayList<Personal> buscarTodoPersonalConLegajo(){
        ArrayList<Personal> personales = new ArrayList<>();
        try{
            String sql = "SELECT personal.legajo, personal.apellido, personal.nombre FROM personal;";
            ResultSet fila = conexion.getSql().executeQuery(sql);
            while(fila.next()){
                Personal personal = new Personal();
                personal.setLegajo(fila.getInt("legajo"));
                personal.setApellido(fila.getString("apellido"));
                personal.setNombre(fila.getString("nombre"));
                personales.add(personal);
            }
            System.out.println("Personal recuperado correctamente.");
        }catch(SQLException e){
            System.out.println("No se pudo encontrar personal buscado: " +e);
        }
        return personales;
    }


}

