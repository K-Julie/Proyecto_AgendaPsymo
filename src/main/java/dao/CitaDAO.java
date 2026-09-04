/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import modelo.Cita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    //Método para registrar (insertar) una nueva cita en la BD
    public boolean agendarCita(Cita cita){
        String sql = "INSERT INTO citas (id_consultante, id_profesional, especialidad, tipo_servicio, fecha_hora, estado)"
                   + " VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, cita.getIdConsultante());
            ps.setInt(2, cita.getIdProfesional());
            ps.setString(3, cita.getEspecialidad());
            ps.setString(4, cita.getTipoServicio());
            ps.setTimestamp(5, cita.getFechaHora());
            ps.setString(6, cita.getEstado());
            
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agendar la cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para cancelar una cita (cambia estado a "Cancelada")
    public boolean cancelarCita(int idCita) {
        String sql = "UPDATE citas SET estado = ? WHERE id_cita = ?";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "Cancelada");
            ps.setInt(2, idCita);
            
            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0; // Retorna true si se actualizó al menos una fila

        } catch (SQLException e) {
            System.out.println("Error al cancelar la cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todas las citas de un usuario por su id_consultante
    public List<Cita> obtenerCitasPorUsuario(int idUsuario) {
        List<Cita> listaCitas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE id_consultante = ?";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Cita c = new Cita();
                c.setIdCita(rs.getInt("id_cita"));
                c.setIdConsultante(rs.getInt("id_consultante"));
                c.setIdProfesional(rs.getInt("id_profesional"));
                c.setEspecialidad(rs.getString("especialidad"));
                c.setTipoServicio(rs.getString("tipo_servicio"));
                c.setFechaHora(rs.getTimestamp("fecha_hora"));
                c.setEstado(rs.getString("estado"));
                listaCitas.add(c);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener citas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listaCitas;
    }
}
