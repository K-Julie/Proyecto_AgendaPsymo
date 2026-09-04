/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.profesional;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EliminarProfesional {
    public static void main (String[] args){
    ConexionDB con=new ConexionDB();
    Connection cn;
    Statement st;
    ResultSet rs;
    
    //dato a eliminar 
     int id_profesional = 7;
     
     // instruccion sql
     
     String sql= "DELETE fROM profesionales WHERE id_profesional ="+id_profesional;
     
     try{
     Class.forName("com.mysql.cj.jdbc.Driver");
    }catch(ClassNotFoundException ex){
        Logger.getLogger(EliminarProfesional.class.getName()).log(Level.SEVERE, null, ex);    
    }
    try{
    cn=con.getConnection();
    st=cn.createStatement();
    st.executeUpdate(sql);
    rs=st.executeQuery("SELECT * FROM profesionales ");
    rs.next();
    
    //se imprime cada fila restante
    do{
    System.out.println(rs.getInt("id_profesional") + " | " +
            rs.getString("tipo_documento") + " | " +
            rs.getString("numero_documento") + " | " +
            rs.getString("nombres") + " | " +
            rs.getString("apellidos") + " | " +
            rs.getString("especialidad") + " | " +
            rs.getString("consultorio"));
    
    }while (rs.next());
    
    
    }catch (SQLException ex){
        Logger.getLogger(EliminarProfesional.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }   
}
