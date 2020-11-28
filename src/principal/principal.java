/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import controlador.Controlador;
import modelo.Conexion;

/**
 *
 * @author ncmeza
 */
public class principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         Controlador c;
        Conexion con = new Conexion();
       if( con.conectar() > 0 )
       {
        c = new Controlador(con);
        c.ejecutar();
           
       }
       else System.out.println("NO se conecto...");
        
    }
    
}
