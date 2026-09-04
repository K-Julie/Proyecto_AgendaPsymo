package controladores;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AgregarConsultanteServlet")
public class AgregarConsultanteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Crea un nuevo objeto Usuario donde se guardarán los datos del formulario
        Usuario u = new Usuario();

        // Obtiene los valores enviados desde el formulario y los asigna al usuario
        u.setTipoDocumento(request.getParameter("tipoDocumento"));
        u.setNumeroDocumento(request.getParameter("numeroDocumento"));
        u.setNombres(request.getParameter("nombres"));
        u.setApellidos(request.getParameter("apellidos"));
        u.setCorreo(request.getParameter("correo"));
        u.setTelefono(request.getParameter("telefono"));
        u.setContrasena(request.getParameter("contrasena"));
        u.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento"))); //Convierte la fecha recibida en texto a un LocalDate
        u.setRol("consultante"); // Fijo porque es admin creando un usario consultante
        
        //DAO para acceder a la BD
        UsuarioDAO dao = new UsuarioDAO();
        dao.agregarUsuario(u); //Llama al metodo para guardar el usuario en la BD

        response.sendRedirect("agregarconsultante.jsp?exito=1");
    }
}
