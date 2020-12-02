/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author ncmeza
 */
public class ClienteDAO {
    private Cliente cliente;
    private Conexion conexion;

    public ClienteDAO(Cliente cliente, Conexion conexion) {
        this.cliente = cliente;
        this.conexion = conexion;
    }
    
    public ClienteDAO(Conexion conexion){
        this.conexion = conexion;
    }
    
    public Cliente buscarClientePorCuit(int cuit){
        cliente = new Cliente();
        try{
            String sql = "SELECT cliente.*, localidad.nombre, provincia.nombre FROM cliente "
                    + "JOIN localidad ON cliente.localidad_codigo_postal1 = localidad.codigo_postal "
                    + "JOIN provincia ON localidad.provincia_idprovincia = provincia.idprovincia "
                    + "WHERE cliente.cuit_cuil="+cuit+";";
            
            ResultSet fila = conexion.getSql().executeQuery(sql);
            if(fila.next()){
                cliente.setCuit(cuit);
                cliente.setIdcliente(fila.getInt("idcliente"));
                cliente.setRazonSocial(fila.getString("razon_social"));
                cliente.setEmail(fila.getString("email"));
                cliente.setTelefono(fila.getInt("telefono"));
                cliente.setProvincia(fila.getString("provincia.nombre"));
                cliente.setLocalidad(fila.getString("localidad.nombre"));
            }
        }catch(SQLException e){
            System.out.println("No se encontraron resultados: "+e);
        }
        return cliente;
    }
    
    public Cliente buscarClientePorId(int idcliente){
        cliente = new Cliente();
        try{
            String sql = "SELECT cliente.*, localidad.nombre, provincia.nombre FROM cliente "
                    + "JOIN localidad ON cliente.localidad_codigo_postal1 = localidad.codigo_postal "
                    + "JOIN provincia ON localidad.provincia_idprovincia = provincia.idprovincia "
                    + "WHERE cliente.idcliente="+idcliente+";";
            
            ResultSet fila = conexion.getSql().executeQuery(sql);
            if(fila.next()){
                
                cliente.setIdcliente(idcliente);
                cliente.setCuit(fila.getInt("cuit_cuil"));
                cliente.setRazonSocial(fila.getString("razon_social"));
                cliente.setEmail(fila.getString("email"));
                cliente.setTelefono(fila.getInt("telefono"));
                cliente.setProvincia(fila.getString("provincia.nombre"));
                cliente.setLocalidad(fila.getString("localidad.nombre"));
            }
        }catch(SQLException e){
            System.out.println("No se encontraron resultados: "+e);
        }
        return cliente;
    }
}
