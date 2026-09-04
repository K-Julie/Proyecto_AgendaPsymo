/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.profesional;

import conexion.ConexionDB;  //paquete.Clase
import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrearProfesional {
    public static void main (String[] args){
        ConexionDB con = new ConexionDB();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        
   //Datos del usuario a ingresar  
    String tipo_documento = "CC";
    String numero_documento = "53918655";
    String nombres = "Santiago";
    String apellidos = "Linares Muñoz";
    String especialidad = "Psiquiatria";
    String consultorio = "204";
    //SQL para insertar
    String sql = "INSERT INTO profesionales (tipo_documento,numero_documento,nombres, apellidos, especialidad,consultorio) VALUES ("
            + "'" + tipo_documento + "',"
            + "'" + numero_documento + "',"
            + "'" + nombres + "',"
            + "'" + apellidos + "',"
            + "'" + especialidad + "',"
            + "'" + consultorio + "')";
       
    try{
     Class.forName("com.mysql.cj.jdbc.Driver"); //Cargar Driver
    }catch(ClassNotFoundException ex){
        Logger.getLogger(CrearProfesional.class.getName()).log(Level.SEVERE, null, ex);    
    }
    try{
    cn=con.getConnection();
    st=cn.createStatement();
    st.executeUpdate(sql);
    rs=st.executeQuery("SELECT * FROM profesionales ");
    rs.next();
    
    do{
    System.out.println(
            rs.getInt("id_profesional") + " | " +
            rs.getString("tipo_documento") + " | " +
            rs.getString("numero_documento") + " | " +
            rs.getString("nombres") + " | " +
            rs.getString("apellidos") + " | " +
            rs.getString("especialidad") + " | " +
            rs.getString("consultorio") 
    );
    
    }while (rs.next());
    
    
    }catch (SQLException ex){
        Logger.getLogger(CrearProfesional.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }   
}

