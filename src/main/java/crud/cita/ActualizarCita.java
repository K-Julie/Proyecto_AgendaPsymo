/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud.cita;

import conexion.ConexionDB; //paquete.Clase
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class ActualizarCita {
    public static void main(String[] args) {
        ConexionDB con = new ConexionDB();
        Connection cn = null;
        Statement st = null;
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActualizarCita.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cn = con.getConnection();
            st = cn.createStatement();

            // Pedir datos al usuario
            System.out.print("Ingrese ID de la cita a actualizar: ");
            int idCita = sc.nextInt(); //Se lee el ID
            sc.nextLine(); // Consume el salto de línea, evita que la próxima lectura de texto con nextLine() se salte

            System.out.print("Ingrese nueva fecha y hora (YYYY-MM-DD HH:MM:SS): ");
            String nuevaFecha = sc.nextLine();

            System.out.print("Ingrese nuevo estado (agendada, cancelada): ");
            String nuevoEstado = sc.nextLine();

            String sql = "UPDATE citas SET fecha_hora = '" + nuevaFecha + "', estado = '" + nuevoEstado
                    + "' WHERE id_cita = " + idCita;
            st.executeUpdate(sql);

            System.out.println("Cita actualizada correctamente.");

        } catch (SQLException ex) {
            Logger.getLogger(ActualizarCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
