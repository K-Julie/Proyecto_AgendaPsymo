package controladores;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import java.time.LocalDate; // Import para manejar fechas
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegistrarUsuarioServlet")
public class RegistrarUsuarioServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Capturar datos del formulario
        String tipoDocumento = request.getParameter("tipoDocumento");
        String numeroDocumento = request.getParameter("numeroDocumento");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String contrasena = request.getParameter("contrasena");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento"); 

        // Convertir a LocalDate
        LocalDate fechaNacimiento = null;
        if (fechaNacimientoStr != null && !fechaNacimientoStr.isEmpty()) {
            fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        }

        // Asignar rol por defecto
        String rol = "consultante";

        // Crear objeto Usuario con los datos
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setTipoDocumento(tipoDocumento);
        nuevoUsuario.setNumeroDocumento(numeroDocumento);
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setFechaNacimiento(fechaNacimiento); 
        nuevoUsuario.setRol(rol);

        // Instanciar el DAO y registrar
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean registroExitoso = usuarioDAO.registrarUsuario(nuevoUsuario);

        // Enviar mensaje según resultado
        if (registroExitoso) {
            request.setAttribute("mensajeExito", true);
        } else {
            request.setAttribute("mensajeError", true);
        }

        // Redirigir nuevamente al JSP para mostrar el modal
        request.getRequestDispatcher("registrarusuario.jsp").forward(request, response);
    }
}
