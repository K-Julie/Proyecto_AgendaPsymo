/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.cita;

import conexion.ConexionDB; //paquete.Clase
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CancelarCita {
     public static void main(String[] args) {
        ConexionDB con = new ConexionDB();
        Connection cn = null;
        Statement st = null;
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CancelarCita.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cn = con.getConnection();
            st = cn.createStatement();

            // Pedir ID de la cita
            System.out.print("Ingrese ID de la cita a cancelar: ");
            int idCita = sc.nextInt();

            // Se actualiza solo el estado
            String sql = "UPDATE citas SET estado = 'cancelada' WHERE id_cita = " + idCita;
            st.executeUpdate(sql);

            System.out.println("Cita cancelada correctamente.");

        } catch (SQLException ex) {
            Logger.getLogger(CancelarCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
