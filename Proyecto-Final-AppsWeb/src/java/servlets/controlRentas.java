/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import persistencia.PersistenciaBD;

/**
 *
 * @author Eduardo Ramírez
 */
@WebServlet(name = "controlRentas", urlPatterns = {"/controlRentas"})
public class controlRentas extends HttpServlet {

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
                if(!log){
                response.sendRedirect("login.jsp");
                return;
                }
                
    	}
    	}
        String tarea = request.getParameter("tarea");
        IPersistencia fachada = new PersistenciaBD();

        List listaClientes = fachada.consultarClientes();
        request.setAttribute("listaClientes", listaClientes);

        switch (tarea) {
            case "rentar":
                request.getRequestDispatcher("rentarVideojuego.jsp").forward(request, response);
                
                break;

            case "devolver":
                request.getRequestDispatcher("devolverVideojuego.jsp").forward(request, response);

                break;

            case "consultaRentasCliente":
                request.getRequestDispatcher("consultaRentasCliente.jsp").forward(request, response);
                break;

            case "consultaPeriodo":
                request.getRequestDispatcher("consultaRentasPeriodo.jsp").forward(request, response);
                break;

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
