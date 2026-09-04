package modelo;

import java.time.LocalDate; // Para manejar fechas modernas en Java

public class Usuario {
    // Atributos del usuario (coinciden con las columnas de la tabla usuarios)
    private int idUsuario;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String contrasena;
    private LocalDate fechaNacimiento; 
    private String rol; // ENUM en la BD

    // Constructor vacío
    public Usuario() {}

    // Constructor completo (con fechaNacimiento)
    public Usuario(int idUsuario, String tipoDocumento, String numeroDocumento, String nombres, String apellidos,
                   String correo, String telefono, String contrasena, LocalDate fechaNacimiento, String rol) {
        this.idUsuario = idUsuario;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.fechaNacimiento = fechaNacimiento;
        this.rol = rol;
    }
    //Para pasar de Cedula a CC
    public String getTipoDocumentoDescripcion() {
    switch (this.tipoDocumento) {
        case "CC":
            return "Cédula de ciudadanía";
        case "CE":
            return "Cédula de extranjería";
        case "PA":
            return "Pasaporte";
        default:
            return this.tipoDocumento; // Por si acaso
    }
}

    // Getters y setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
