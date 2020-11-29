/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.SQLException;

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
    
//    public void agregar(){
//        try{
//            String sql = "INSERT INTO adm_rec.personal SET legajo="+personal.getLegajo()+", apellido='"+personal.getApellido()+"', nombre='"+personal.getNombre()+"',"
//                    + " dni="+personal.getDni()+", rol_personal_idrol_personal="+personal.getRolPersonal()+";";
//            conexion.getSql().execute(sql);
//            
//            System.out.println("Personal agregado a la base de datos.");
//        }catch(SQLException e){
//            System.out.println("Error al agregar datos:"+e);
//        }

    }

