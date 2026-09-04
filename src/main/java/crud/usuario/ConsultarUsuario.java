/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.usuario;

import conexion.ConexionDB; //paquete.Clase
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultarUsuario {
    public static void main (String[] args){
    ConexionDB con = new ConexionDB();
    Connection cn = null;
    Statement st = null;
    ResultSet rs = null;
    
try{
    //crear conexión
    cn=con.getConnection();
    st=cn.createStatement();
    rs=st.executeQuery("SELECT * FROM usuarios "); //consulta a la tabla usuarios y guarda los resultados en rs

//Se recorre cada fila:
    while (rs.next()) {
    //se obtiene los datos de cada columna de la fila actual
    int id_usuario = rs.getInt("id_usuario");
    String tipo_documento = rs.getString("tipo_documento");
    String numero_documento = rs.getString ("numero_documento");
    String nombres = rs.getString ("nombres");
    String apellidos = rs.getString ("apellidos");
    String contrasena = rs.getString ("contrasena");
    String rol = rs.getString ("rol");
    String correo = rs.getString("correo");
    String telefono = rs.getString ("telefono");
    String fecha_nacimiento = rs.getString("fecha_nacimiento");    
   
//mostrar datos en consola
   System.out.println (   
           id_usuario+ "|" +tipo_documento + "|" +numero_documento+ "|" +nombres+ "|" +apellidos+ "|"+contrasena +"|"+
           rol+ "|" +correo+ "|" +telefono+ "|" +fecha_nacimiento
   );
}
    
    }catch (SQLException ex){
        Logger.getLogger(ConsultarUsuario.class.getName()).log(Level.SEVERE, null, ex);
    
    }finally {
       try{
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (cn != null) cn.close();
       }catch (SQLException ex){
        Logger.getLogger(ConsultarUsuario.class.getName()).log(Level.SEVERE, null, ex);     
            }   
   }
}
}
