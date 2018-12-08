/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.Rentas;
import excepciones.PersistenciaException;
import interfaces.IPersistencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import objetosNegocio.Cliente;
import objetosNegocio.Renta;
import persistencia.PersistenciaBD;

/**
 *
 * @author mario
 */
@WebServlet(name = "operacionesClientes", urlPatterns = {"/operacionesClientes"})
public class operacionesClientes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Cookie loginCookie = null;
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
            boolean log=false;
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("usuario")){
    			log=true;
    			break;
    		}
                if(!log){
                response.sendRedirect("login.jsp");
                return;
                }
                
    	}
    	}
try{
        String tarea = request.getParameter("tarea");
        IPersistencia fachada = new PersistenciaBD();
        List listaClientes = fachada.consultarClientes();

        switch (tarea) {
            case "Guardar":
                String numCred = request.getParameter("cred"),
                 nombre = request.getParameter("nombre"),
                 direccion = request.getParameter("direccion"),
                 telefono = request.getParameter("telefono");

                Cliente clnt = new Cliente();
                clnt.setDireccion(direccion);
                clnt.setNombre(nombre);
                clnt.setNumCredencial(numCred);
                clnt.setTelefono(telefono);
                if(listaClientes.contains(clnt)){
                    request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "Ya existe un cliente regsitrado con la credencial "+numCred+".");
                      request.getRequestDispatcher("agregarCliente.jsp").forward(request, response);
                      return;
                }
                

                    fachada.agregar(clnt);
                     request.setAttribute("exito", "!LISTO!");
                     request.setAttribute("mensaje", "El cliente con credencial "+numCred+" ha quedado registrado.");
                      request.getRequestDispatcher("agregarCliente.jsp").forward(request, response);
                      

                
                break;
            case "consultarClientes":
                if(listaClientes.isEmpty()){
                request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "Aún no hay ningún cliente registrado.");
                      request.getRequestDispatcher("index.jsp").forward(request, response);
                      return;
                }
                request.setAttribute("listaClientes", listaClientes);
                request.getRequestDispatcher("listaClientes.jsp").forward(request, response);
            case "consultaCliente":
                response.sendRedirect("consultaClienteCred.jsp");
                break;

            case "Buscar por Credencial":
                numCred = request.getParameter("credencial");
                Cliente c = new Cliente(numCred);
                
                
                if(listaClientes.contains(c)){
                    c= (Cliente)listaClientes.get(listaClientes.indexOf(c));
                    request.setAttribute("c", c);
                      request.getRequestDispatcher("soloUnCliente.jsp").forward(request, response);
                      return;
                }
                else{
                    request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "No hay nigun cliente registrado con la credencial "+numCred+".");
                      request.getRequestDispatcher("consultaClienteCred.jsp").forward(request, response);
                      
                }
                

                break;

            case "Eliminar Cliente":
                
                numCred=request.getParameter("credencial");
                if(!listaClientes.contains(new Cliente(numCred))){
                     request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "No existe ningun cliente con credencial "+numCred+".");
                      request.getRequestDispatcher("eliminaCliente.jsp").forward(request, response);
                      return;
                }
                List<Renta> listaRentas = fachada.consultarRentasVideojuegos(new Cliente(numCred));
                if(listaRentas.size()>0){
                    request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "El cliente con credencial "+numCred+" cuenta con rentas activas.");
                      request.getRequestDispatcher("eliminaCliente.jsp").forward(request, response);
                }
                
                    fachada.eliminar(new Cliente(numCred));
                    request.setAttribute("exito", "!LISTO!");
                     request.setAttribute("mensaje", "El cliente con credencial "+numCred+" ah sido eliminado.");
                      request.getRequestDispatcher("eliminaCliente.jsp").forward(request, response);
                
                
                
                
                
        }
    }
        catch(PersistenciaException e){
                    request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "Ocurrió un error al conectarse a la base de datos. Inténtelo más tarde");
                      request.getRequestDispatcher("index.jsp").forward(request, response);
                }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
