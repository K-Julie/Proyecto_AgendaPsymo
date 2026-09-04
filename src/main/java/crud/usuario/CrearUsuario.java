/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.usuario;

import conexion.ConexionDB;  //paquete.Clase
import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrearUsuario {
    public static void main (String[] args){
        ConexionDB con = new ConexionDB();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        
   //Datos del usuario a ingresar  
    String tipo_documento = "CC";
    String numero_documento = "1021935039";
    String nombres = "Dilan Manuel";
    String apellidos = "Diaz Cruz";
    String contrasena = "dima11";
    String rol = "consultante";
    String correo = "dmdc10@hotmail.com";
    String telefono = "3171002567";
    String fecha_nacimiento = "1983-03-27";
    
    //SQL para insertar
    String sql = "INSERT INTO usuarios (tipo_documento,numero_documento,nombres, apellidos, contrasena,rol,correo,telefono,fecha_nacimiento) VALUES ("
            + "'" + tipo_documento + "',"
            + "'" + numero_documento + "',"
            + "'" + nombres + "',"
            + "'" + apellidos + "',"
            + "'" + contrasena + "',"
            + "'" + rol + "',"
            + "'" + correo + "',"
            + "'" + telefono + "',"
            + "'" + fecha_nacimiento + "')";
    try{
     Class.forName("com.mysql.cj.jdbc.Driver"); //Cargar Driver
    }catch(ClassNotFoundException ex){
        Logger.getLogger(CrearUsuario.class.getName()).log(Level.SEVERE, null, ex);    
    }
    try{
    cn=con.getConnection();
    st=cn.createStatement();
    st.executeUpdate(sql);
    rs=st.executeQuery("SELECT * FROM usuarios ");
    rs.next();
    
    do{
    System.out.println(
            rs.getInt("id_usuario") + " : " +
            rs.getString("tipo_documento") + "  " +
            rs.getString("numero_documento") + "  " +
            rs.getString("nombres") + "  " +
            rs.getString("apellidos") + "  " +
            rs.getString("contrasena") + "  " +
            rs.getString("rol") + " - " +
            rs.getString("correo") + "  " +
            rs.getString("telefono") + " " +
            rs.getDate("fecha_nacimiento")
    );
    
    }while (rs.next());
    
    
    }catch (SQLException ex){
        Logger.getLogger(CrearUsuario.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }   
}
