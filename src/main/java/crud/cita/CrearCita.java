/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.cita;

import conexion.ConexionDB;  //paquete.Clase
import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrearCita {
    public static void main (String[] args){
        ConexionDB con = new ConexionDB();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        
   //Datos de la cita a ingresar  
    int id_consultante= 2; //Debe coincidir con el id en usuarios
    int id_profesional = 3; //Debe existir en profesionales
    String tipo_servicio = "control"; // Que coincida con las opciones que estan en la BD
    String fecha_hora = "2025-10-20 15:00:00"; //formato YY-MM-DD HH-mm-ss
    String estado = "agendada";
    
    //Para obtener la especialidad desde profesionales:
    String especialidad = null;
    
    try {
        Class.forName ("com.mysql.cj.jdbc.Driver"); //Cargar Driver
    } catch (ClassNotFoundException ex){
        Logger.getLogger(CrearCita.class.getName()).log(Level.SEVERE, null,ex);
    }
    
     try {
            cn = con.getConnection();
            st = cn.createStatement();
            
     //Traer la especialidad del profesional 
     rs = st.executeQuery("SELECT especialidad FROM profesionales WHERE id_profesional = " + id_profesional);
     if (rs.next()) {
         especialidad = rs.getString ("especialidad");
     }else {
         System.out.println ( "No se encontró el profesional con el ID: " + id_profesional);
         return;
     }
     
    //Para insertar en SQL
    String sql = "INSERT citas (id_consultante,id_profesional,especialidad, tipo_servicio, fecha_hora, estado) VALUES ("
            + "'" + id_consultante + "',"
            + "'" + id_profesional + "',"
            + "'" + especialidad + "',"
            + "'" + tipo_servicio + "',"
            + "'" + fecha_hora + "',"
            + "'" + estado + "') ";
            
    st.executeUpdate(sql);
    
    System.out.println("Cita creada correctamente.");

        } catch (SQLException ex) {
            Logger.getLogger(CrearCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
