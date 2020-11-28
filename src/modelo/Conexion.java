/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Leonel
 */
public class Conexion {
    
    private String basededatos;
    private String usuario="root";
    private String clave="nicomeza";
    private String servidor="127.0.0.1";
    private Connection con;
    private Statement sql;

   public Conexion(){
        
    }
    public int conectar(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/adm_rec", "root", clave);
        sql = con.createStatement();
    }
    catch(ClassNotFoundException e){
         JOptionPane.showMessageDialog(null,"Error de conexion de la base de datos");
        return -1;
    }
    catch(SQLException e){
        return -2;
    }
    return 1;
    }

    public String getBasededatos() {
        return basededatos;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public String getServidor() {
        return servidor;
    }

    public Connection getCon() {
        return con;
    }

    public Statement getSql() {
        return sql;
    }

    public void setBasededatos(String basededatos) {
        this.basededatos = basededatos;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void setSql(Statement sql) {
        this.sql = sql;
    }
}

