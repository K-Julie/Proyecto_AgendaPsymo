/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.profesional;

import conexion.ConexionDB; //paquete.Clase
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActualizarProfesional {
    
    public static void main (String[] args){
    ConexionDB con = new ConexionDB();
    Connection cn;
    Statement st;
    ResultSet rs;
   
    // Datos a actualizar
    int id_profesional = 6; //identificador
    String newNombres = "Miguel"; //nuevos valores
    String newConsultorio = "104";


    String sql = "UPDATE profesionales SET "
            + "nombres='"+ newNombres+"',"
            + "consultorio='" + newConsultorio+ "" 
            + "'WHERE id_profesional =" +id_profesional; //para afectar solo ese usuario
    try{
     Class.forName("com.mysql.cj.jdbc.Driver");
    }catch(ClassNotFoundException ex){
        Logger.getLogger(ActualizarProfesional.class.getName()).log(Level.SEVERE, null, ex);    
    }
    try{
    cn=con.getConnection();
    st=cn.createStatement();
    st.executeUpdate(sql);
    rs=st.executeQuery("SELECT * FROM profesionales WHERE id_profesional=" + id_profesional);
    rs.next();
    
    do{
    System.out.println(
            rs.getInt("id_profesional") + "|" +
            rs.getString("tipo_documento") + " | " +
            rs.getString("numero_documento") + " | " +
            rs.getString("nombres") + " | " +
            rs.getString("apellidos") + " | " +
            rs.getString("especialidad") + " | " +
            rs.getString("consultorio")
    );
    
    }while (rs.next());
    
    
    }catch (SQLException ex){
        Logger.getLogger(ActualizarProfesional.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }   
}
