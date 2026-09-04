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

public class ActualizarUsuario {
    
    public static void main (String[] args){
    ConexionDB con = new ConexionDB();
    Connection cn;
    Statement st;
    ResultSet rs;
   
    // Datos a actualizar
    int id_usuario = 1; //identificador
    String newNombres = "Katherine"; //nuevos valores
    String newCorreo = "katha33@gmail.com";
    String newTelefono = "3128653729";
    
    String sql = "UPDATE usuarios SET "
           + "nombres='"+ newNombres+"',"
           + "correo='" + newCorreo+ "',"
           + "telefono='" + newTelefono+ "" 
           + "'WHERE id_usuario =" +id_usuario; //para afectar solo ese usuario
        
    try{
     Class.forName("com.mysql.cj.jdbc.Driver");
    }catch(ClassNotFoundException ex){
        Logger.getLogger(ActualizarUsuario.class.getName()).log(Level.SEVERE, null, ex);    
    }
    try{
    cn=con.getConnection();
    st=cn.createStatement();
    st.executeUpdate(sql);
    rs=st.executeQuery("SELECT * FROM usuarios WHERE id_usuario=" + id_usuario);
    rs.next();
    
    do{
    System.out.println(
            rs.getInt("id_usuario") + "|" +
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
    
    }while (rs.next());
    
    
    }catch (SQLException ex){
        Logger.getLogger(ActualizarUsuario.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }   
}
    
