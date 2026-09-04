package dao;

import conexion.ConexionDB;
import modelo.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // REGISTRAR USUARIO
    public boolean registrarUsuario(Usuario u) {
        //Consulta SQL para insertar un nuevo usuario en la BD
        String sql = "INSERT INTO usuarios (tipo_documento, numero_documento, nombres, apellidos, contrasena, correo, telefono, fecha_nacimiento, rol) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Asignar cada valor del objeto Usuario a los parámetros del SQL
            ps.setString(1, u.getTipoDocumento());
            ps.setString(2, u.getNumeroDocumento());
            ps.setString(3, u.getNombres());
            ps.setString(4, u.getApellidos());
            ps.setString(5, u.getContrasena());
            ps.setString(6, u.getCorreo());
            ps.setString(7, u.getTelefono());

            // Si el objeto Usuario tiene fecha de nacimiento (no es null)
            if (u.getFechaNacimiento() != null) {
                // Convierte LocalDate a java.sql.Date y lo asigna al parámetro 8 del PreparedStatement
                ps.setDate(8, Date.valueOf(u.getFechaNacimiento()));
            } else {
                // Si no hay fecha, establece NULL en el parámetro 8 indicando que es un tipo DATE en la BD
                ps.setNull(8, Types.DATE);
            }

            ps.setString(9, u.getRol());
            
            //Ejecutar la sentencia 
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error registrando usuario: " + e.getMessage());
            return false;
        }
    }

    // ACTUALIZAR CONTRASEÑA
    public boolean actualizarContrasena(int idUsuario, String nuevaContrasena) {
        //Consulta SQL
        String sql = "UPDATE usuarios SET contrasena = ? WHERE id_usuario = ?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            //Asignar nueva contraseña al primer parametro
            ps.setString(1, nuevaContrasena);
            ps.setInt(2, idUsuario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizando contraseña: " + e.getMessage());
            return false;
        }
    }
  
    // BUSCAR USUARIO POR DOCUMENTO Y CORREO (Recuperar contraseña)
    public Usuario buscarPorDocumentoYCorreo(String numeroDocumento, String correo) {
        String sql = "SELECT * FROM usuarios WHERE numero_documento = ? AND correo = ?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroDocumento); //Asigna el número de documento al primer parametro
            ps.setString(2, correo);

            ResultSet rs = ps.executeQuery(); //Ejecuta la consulta
            
            //Sí se encontró un resultado
            if (rs.next()) {
                Usuario u = new Usuario(); // Crea objeto Usuario para llenar con los datos
                
                //Tomar los datos de la BD y colocarlos en las propiedades del Objeto Java : Usuario (Mapear)
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setTipoDocumento(rs.getString("tipo_documento"));
                u.setNumeroDocumento(rs.getString("numero_documento"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                u.setRol(rs.getString("rol"));

                Date fecha = rs.getDate("fecha_nacimiento");
                if (fecha != null) u.setFechaNacimiento(fecha.toLocalDate()); //Convierte a LocalDate

                return u; //Retorna el usuario encontrado
            }

        } catch (SQLException e) {
            System.out.println("Error buscando usuario por documento y correo: " + e.getMessage());
        }

        return null; // Si no encuentra ningun usuario, devuelve Null
    }

    //BUSCAR USUARIOS CON FILTRO (PARA ADMIN)
    public List<Usuario> buscarConsultantes(String filtro) {
        //Lista que almacenará los usuarios encontrados
        List<Usuario> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM usuarios WHERE rol = 'consultante' "
                   + "AND (nombres LIKE ? OR apellidos LIKE ? OR numero_documento LIKE ? OR correo LIKE ?)";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Se crea el patrón "%filtro%" para buscar coincidencias parciales (LIKE en SQL)
            // El mismo patrón se aplica a nombres, apellidos, documento y correo
            String f = "%" + filtro + "%";
            ps.setString(1, f);
            ps.setString(2, f);
            ps.setString(3, f);
            ps.setString(4, f);

            ResultSet rs = ps.executeQuery();
            
            //Recorre cada fila encontrada y crea un objeto Usuario
            while (rs.next()) {
                Usuario u = new Usuario();
                //Se mapean los valores de la BD al objeto Usuario 
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setTipoDocumento(rs.getString("tipo_documento"));
                u.setNumeroDocumento(rs.getString("numero_documento"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                u.setRol(rs.getString("rol"));
                //Convierte la fecha SQL a LocalDate
                Date fecha = rs.getDate("fecha_nacimiento");
                if (fecha != null) u.setFechaNacimiento(fecha.toLocalDate());
                
                //Agrega el usuario a la lista
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error buscando consultantes: " + e.getMessage());
        }

        return lista;
    }

    // OBTENER CONSULTANTE POR ID (Para editar)
    public Usuario obtenerConsultantePorId(int id) {
        // Consulta: obtiene un usuario exacto por ID y valida que sea consultante
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ? AND rol = 'consultante'";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            //Asigna el ID al parametro de la consulta
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                //Se mapean los datos de la BD al objeto Usuario
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setTipoDocumento(rs.getString("tipo_documento"));
                u.setNumeroDocumento(rs.getString("numero_documento"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));

                Date fecha = rs.getDate("fecha_nacimiento");
                if (fecha != null) u.setFechaNacimiento(fecha.toLocalDate());

                u.setRol(rs.getString("rol"));
                //Se devuelve el usuario encontrado
                return u;
            }

        } catch (SQLException e) {
            System.out.println("Error obteniendo consultante por ID: " + e.getMessage());
        }
        //Si no encuentra nada
        return null;
    }

    // ACTUALIZAR CONSULTANTE
    public boolean actualizarConsultante(Usuario u) {

        String sql = "UPDATE usuarios SET tipo_documento=?, numero_documento=?, nombres=?, apellidos=?, correo=?, telefono=?, fecha_nacimiento=? "
                   + "WHERE id_usuario=? AND rol='consultante'";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            //Se asignan al UPDATE los nuevos valores enviados desde el objeto Usuario
            ps.setString(1, u.getTipoDocumento());
            ps.setString(2, u.getNumeroDocumento());
            ps.setString(3, u.getNombres());
            ps.setString(4, u.getApellidos());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getTelefono());
            
            // Si el usuario tiene fecha de nacimiento, se envía; si no, se registra como NULL
            if (u.getFechaNacimiento() != null) {
                ps.setDate(7, Date.valueOf(u.getFechaNacimiento()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            // Indicar que el usuario se va a actualizar según su ID
            ps.setInt(8, u.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizando consultante: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR CONSULTANTE
    public boolean eliminarConsultante(int idUsuario) {

        String sql = "DELETE FROM usuarios WHERE id_usuario = ? AND rol = 'consultante'";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            //Indicar cual usuario se va a eliminar
            ps.setInt(1, idUsuario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error eliminando consultante: " + e.getMessage());
            return false;
        }
    }
    
// AGREGAR USUARIO (consultante)
public void agregarUsuario(Usuario u) {
    String sql = "INSERT INTO usuarios (tipo_documento, numero_documento, nombres, apellidos, correo, telefono, fecha_nacimiento, contrasena, rol) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        // Se envían los valores del objeto Usuario a cada campo del INSERT
        ps.setString(1, u.getTipoDocumento());
        ps.setString(2, u.getNumeroDocumento());
        ps.setString(3, u.getNombres());
        ps.setString(4, u.getApellidos());
        ps.setString(5, u.getCorreo());
        ps.setString(6, u.getTelefono());
        // Se convierte LocalDate a java.sql.Date para almacenarlo en la BD
        ps.setDate(7, java.sql.Date.valueOf(u.getFechaNacimiento()));
        // Se asigna la contraseña y el rol del usuario
        ps.setString(8, u.getContrasena());
        ps.setString(9, u.getRol());
        
        // Ejecuta el INSERT en la BD
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
