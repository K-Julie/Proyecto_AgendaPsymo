/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.sql.Timestamp;

public class Cita {
    // Atributos base (ya existentes en la BD)
    private int idCita;
    private int idConsultante;
    private int idProfesional;
    private String especialidad;
    private String tipoServicio;
    private Timestamp fechaHora;
    private String estado;
    
    // Nuevos atributos para mostrar en la tabla JSP
    private String nombreProfesional; // Nombre completo del profesional
    private String consultorio;       // Consultorio donde se atiende la cita
    
    // Constructor vacío
    public Cita() {}

    // Constructor para agendar citas (solo lo necesario para insertar en BD)
    public Cita(int idConsultante, int idProfesional, String especialidad, String tipoServicio, Timestamp fechaHora, String estado){
        this.idConsultante = idConsultante;
        this.idProfesional = idProfesional;
        this.especialidad = especialidad;
        this.tipoServicio = tipoServicio;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Constructor completo para mostrar en la tabla
    public Cita(int idCita, int idConsultante, int idProfesional, String especialidad, String tipoServicio,
                Timestamp fechaHora, String estado, String nombreProfesional, String consultorio) {
        this.idCita = idCita;
        this.idConsultante = idConsultante;
        this.idProfesional = idProfesional;
        this.especialidad = especialidad;
        this.tipoServicio = tipoServicio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.nombreProfesional = nombreProfesional;
        this.consultorio = consultorio;
    }

    // Getters y Setters
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdConsultante() { return idConsultante; }
    public void setIdConsultante(int idConsultante) { this.idConsultante = idConsultante; }

    public int getIdProfesional() { return idProfesional; }
    public void setIdProfesional(int idProfesional) { this.idProfesional = idProfesional; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNombreProfesional() { return nombreProfesional; }
    public void setNombreProfesional(String nombreProfesional) { this.nombreProfesional = nombreProfesional; }

    public String getConsultorio() { return consultorio; }
    public void setConsultorio(String consultorio) { this.consultorio = consultorio; }
}
