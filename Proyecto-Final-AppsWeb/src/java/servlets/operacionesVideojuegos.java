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
import java.util.List;
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
 * @author Eduardo Ramírez
 */
@WebServlet(name = "operacionesVideojuegos", urlPatterns = {"/operacionesVideojuegos"})
public class operacionesVideojuegos extends HttpServlet {

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
    
    

        response.setContentType("text/html;charset=UTF-8");
        try{
       String tarea = request.getParameter("tarea");
        IPersistencia fachada = new PersistenciaBD();
        switch(tarea){
            case "Agregar Videojuego":
                Videojuego v = new Videojuego();
                
                v.setNumCatalogo(request.getParameter("numeroDeCatalogo"));
                v.setTitulo(request.getParameter("titulo"));
                v.setGenero(request.getParameter("genero"));
                v.setClasificacion(request.getParameter("clasificacion"));
                v.setConsola(request.getParameter("consola"));
                v.setFabricante(request.getParameter("fabricante"));
                v.setVersion(Integer.parseInt(request.getParameter("version")));
                if(fachada.consultarVideojuegos().contains(v)){
                    request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "Ya existe un videojuego con catálago "+v.getNumCatalogo()+".");
                      request.getRequestDispatcher("agregarVideojuego.jsp").forward(request, response);
                      return;
                }
                fachada.agregar(v);
                 request.setAttribute("exito", "!LISTO¡");
                     request.setAttribute("mensaje", "El videojuego con catálago "+v.getNumCatalogo()+" ah sido registrado.");
                      request.getRequestDispatcher("agregarVideojuego.jsp").forward(request, response);
                
               
        break;
            case "consultarRentados":
                request.setAttribute("listaRentas", fachada.consultarRentasVideojuegos());
                  request.getRequestDispatcher("videojuegosRentados.jsp").forward(request, response);
                
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
