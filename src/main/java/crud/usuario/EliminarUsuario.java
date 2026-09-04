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

public class EliminarUsuario {
    
    public static void main (String[] args){
    ConexionDB con=new ConexionDB();
    Connection cn;
    Statement st;
    ResultSet rs;
    
    //Usuario a eliminar 
     int id_usuario = 4;
     
     // instruccion sql para eliminar
     
     String sql= "DELETE fROM usuarios WHERE id_usuario =" + id_usuario;
     
     try{
        Class.forName("com.mysql.cj.jdbc.Driver");
    }catch(ClassNotFoundException ex){
        Logger.getLogger(EliminarUsuario.class.getName()).log(Level.SEVERE, null, ex);    
    }
     
    try{
    cn=con.getConnection();
    st=cn.createStatement();
    st.executeUpdate(sql); //ejecuta la eliminación
    
    rs=st.executeQuery("SELECT * FROM usuarios "); //consulta de usuarios restantes
    while (rs.next()) {
        System.out.println(
            rs.getInt("id_usuario") + " | " +
            rs.getString("tipo_documento") + " | " +
            rs.getString("numero_documento") + " | " +
            rs.getString("nombres") + " | " +
            rs.getString("apellidos") + " | " +
            rs.getString("contrasena") + " | " +
            rs.getString("rol") + " | " +
            rs.getString("correo") + " | " +
            rs.getString("telefono") + " | " +
            rs.getDate("fecha_nacimiento")
              );
            }
   
    }catch (SQLException ex){
        Logger.getLogger(EliminarUsuario.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }   
}
