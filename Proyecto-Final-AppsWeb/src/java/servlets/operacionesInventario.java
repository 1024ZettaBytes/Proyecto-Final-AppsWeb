/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import excepciones.PersistenciaException;
import interfaces.IPersistencia;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import objetosNegocio.Videojuego;
import persistencia.PersistenciaBD;

/**
 *
 * @author mario
 */
@WebServlet(name = "operacionesInventario", urlPatterns = {"/operacionesInventario"})
public class operacionesInventario extends HttpServlet {

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
        try{
        Cookie loginCookie = null;
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
            boolean log=false;
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("usuario")){
    			log=true;
    			break;
    		}
                if(!log)
                response.sendRedirect("login.jsp");
                
    	}
    	}
        String tarea = request.getParameter("tarea");
         IPersistencia fachada  = new PersistenciaBD();
         
        switch(tarea){
            case("Agregar Inventario"):
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                Videojuego v = new Videojuego();
                v.setNumCatalogo(request.getParameter("numCatalogo"));
                if(!fachada.consultarVideojuegos().contains(v)){
                     request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "No existe nigún videojuego con catálago "+v.getNumCatalogo()+" registrado.");
                      request.getRequestDispatcher("agregarInventario.jsp").forward(request, response);
                      return;
                }
                fachada.inventariar(v, cantidad);
                 request.setAttribute("exito", "!LISTO¡");
                     request.setAttribute("mensaje", "Se agregaron "+cantidad+" unidades al videojuego "+v.getNumCatalogo()+".");
                      request.getRequestDispatcher("agregarInventario.jsp").forward(request, response);
                      
                break;
                case("Desinventariar"):
                     int cant = Integer.parseInt(request.getParameter("cantidad"));
                        Videojuego vi = new Videojuego();
                vi.setNumCatalogo(request.getParameter("numCatalogo"));
                 if(!fachada.consultarVideojuegos().contains(vi)){
                     request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "No existe nigún videojuego con catálago "+vi.getNumCatalogo()+" registrado.");
                      request.getRequestDispatcher("eliminarInventario.jsp").forward(request, response);
                      return;
                }
                 try{
                fachada.desinventariar(vi, cant);
                request.setAttribute("exito", "!LISTO¡");
                     request.setAttribute("mensaje", "Se eliminaron "+cant+" unidades del videojuego con catálago "+vi.getNumCatalogo()+".");
                      request.getRequestDispatcher("eliminarInventario.jsp").forward(request, response);
                      return;
                 }catch(PersistenciaException e){
                     request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "No se pueden eliminar más unidades de las disponibles");
                      request.getRequestDispatcher("eliminarInventario.jsp").forward(request, response);
                      return;
                 }
                    
                
        }
        
    }catch(PersistenciaException e){
         request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "Ocurrió un error al conectarse a la base de datos. Inténtelo más tarde");
                      request.getRequestDispatcher("eliminarInventario.jsp").forward(request, response);
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
