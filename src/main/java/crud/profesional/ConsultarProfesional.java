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

public class ConsultarProfesional {
     public static void main (String[] args){
    ConexionDB con = new ConexionDB();
    Connection cn = null;
    Statement st = null;
    ResultSet rs = null;
    
try{
    //crear conexión
    cn=con.getConnection();
    st=cn.createStatement();
    rs=st.executeQuery("SELECT * FROM profesionales "); //consulta a la tabla usuarios y guarda los resultados en rs

//Se recorre cada fila:
    while (rs.next()) {
    //se obtiene los datos de cada columna de la fila actual
    int id_profesional = rs.getInt("id_profesional");
    String tipo_documento = rs.getString("tipo_documento");
    String numero_documento = rs.getString ("numero_documento");
    String nombres = rs.getString ("nombres");
    String apellidos = rs.getString ("apellidos");
    String especialidad = rs.getString ("especialidad");
    String consultorio = rs.getString ("consultorio");
       
//mostrar datos en consolas
   System.out.println (   
           id_profesional+ "|" +tipo_documento + "|" +numero_documento+ "|" +nombres+ "|" +apellidos+ "|"+
           especialidad+ "|" +consultorio
   );
}
    
    }catch (SQLException ex){
        Logger.getLogger(ConsultarProfesional.class.getName()).log(Level.SEVERE, null, ex);
    
    }finally {
       try{
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (cn != null) cn.close();
       }catch (SQLException ex){
        Logger.getLogger(ConsultarProfesional.class.getName()).log(Level.SEVERE, null, ex);     
            }   
   }
}
    
}
