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

@WebServlet("/EditarConsultanteServlet")
public class EditarConsultanteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Obtener el ID del usuario enviado desde la tabla y botón "editar"
        int id = Integer.parseInt(request.getParameter("id"));
        
        //Llamar al DAO para obtener el usuario desde la BD
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.obtenerConsultantePorId(id);
        
        //Enviar el objeto Usuario al JSP para mostrarlo en el formulario
        request.setAttribute("consultante", u);
        request.getRequestDispatcher("editarconsultante.jsp").forward(request, response);
    }

    //Guardar los cambios editados
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Crear un objeto Usuario y asignar lso datos enviados desde el formulario
        Usuario u = new Usuario();
        //Campos del formulario
        u.setIdUsuario(Integer.parseInt(request.getParameter("id")));
        u.setNombres(request.getParameter("nombres"));
        u.setApellidos(request.getParameter("apellidos"));
        u.setTipoDocumento(request.getParameter("tipoDocumento"));
        u.setNumeroDocumento(request.getParameter("numeroDocumento"));
        u.setCorreo(request.getParameter("correo"));
        u.setTelefono(request.getParameter("telefono"));
        u.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento"))); //Convierte la fecha en texto a LocalDate

        //Instancia del DAO para actualizar en la BD
        UsuarioDAO dao = new UsuarioDAO();
        // Llamar al método que actualiza la información del consultante en la BD
        boolean actualizado = dao.actualizarConsultante(u);
        
        //Enviar al JSP:
        request.setAttribute("consultante", u); //Usuario actualizado
        request.setAttribute("exito", actualizado); // true = éxito, false = error
        request.getRequestDispatcher("editarconsultante.jsp").forward(request, response);
    }
}
