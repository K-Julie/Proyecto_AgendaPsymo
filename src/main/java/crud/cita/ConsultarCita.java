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

public class ConsultarCita {
    public static void main(String[] args) {
        ConexionDB con = new ConexionDB();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConsultarCita.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cn = con.getConnection();
            st = cn.createStatement();
    

//Mostrar todas las citas insertadas con Nombres de paciente y profesional
    String consulta =
        "SELECT c.id_cita, "  //Select = Que columnas quiero traer de la tabla
          + "u.nombres AS nombre_consultante, u.apellidos AS apellido_consultante, "  // u. alias tabla usuarios nombres = nombre exacto de la columna 
            // AS también para que no haya confusión , ya que tengo nombres en ambas tablas
          + "p.nombres AS nombre_profesional, p.apellidos AS apellido_profesional, "  // AS = alias, para darle un nombre mas claro al resultado , resultSet
          + "c.especialidad, c.tipo_servicio, c.fecha_hora, c.estado " 
          + "FROM citas c " 
          + "JOIN usuarios u ON c.id_consultante = u.id_usuario " 
          + "JOIN profesionales p ON c.id_profesional = p.id_profesional";
    
    rs=st.executeQuery(consulta);
    
    if (rs.next()) {
        do {
         System.out.println(
            rs.getInt("id_cita") + " | " +
            rs.getString("nombre_consultante") + " " + rs.getString("apellido_consultante") + " | " + //uso los AS
            rs.getString("nombre_profesional") + " " + rs.getString("apellido_profesional") + " | " +
            rs.getString("especialidad") + " | " +
            rs.getString("tipo_servicio") + " | " +
            rs.getTimestamp("fecha_hora") + " | " +
            rs.getString("estado")
            );
      } while (rs.next());
     } else {
          System.out.println("No hay citas registradas.");
     }
      
    } catch (SQLException ex){
        Logger.getLogger(ConsultarCita.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }     
}
