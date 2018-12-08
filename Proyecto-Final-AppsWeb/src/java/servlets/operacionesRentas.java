/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mysql.fabric.xmlrpc.Client;
import excepciones.PersistenciaException;
import interfaces.IPersistencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import objetosNegocio.Articulo;
import objetosNegocio.ArticuloED;
import objetosNegocio.Cliente;
import objetosNegocio.Renta;
import objetosNegocio.Videojuego;
import objetosServicio.Fecha;
import objetosServicio.Periodo;
import persistencia.PersistenciaBD;

/**
 *
 * @author Eduardo Ramírez
 */
@WebServlet(name = "operacionesRentas", urlPatterns = {"/operacionesRentas"})
public class operacionesRentas extends HttpServlet {

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
        try{
         String tarea = request.getParameter("tarea");
        IPersistencia fachada = new PersistenciaBD();
        List listaClientes = fachada.consultarClientes();

        switch (tarea) {
            case "Rentar":
                String numCred = request.getParameter("cred"),
                 catalago = request.getParameter("catalago");
                 int tiempo =Integer.parseInt(request.getParameter("tiempo")); 
                 
              
                
                List<ArticuloED> lista = fachada.consultarVideojuegosDisponibles();
                if(!listaClientes.contains(new Cliente(numCred))){
                     request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "No existe ningun cliente con credencial "+numCred+" registrado.");
                      request.getRequestDispatcher("rentarVideojuego.jsp").forward(request, response);
                      return;
                }
                else
                   if(!fachada.consultarVideojuegos().contains(new Videojuego(catalago))){
                        request.setAttribute("exito", "ERROR");
                        request.setAttribute("mensaje", "No existe ningun videojuego con No. de catálogo "+catalago+" registrado.");
                       request.getRequestDispatcher("rentarVideojuego.jsp").forward(request, response);
                      return;
                   }
                   else{
                       boolean disponible=false;
                       for (ArticuloED articuloED : lista) {
                        if(articuloED.getArticulo().getNumCatalogo().equals(catalago)){
                            disponible=true;
                            break;
                        }
                    }

                       if(!disponible){
                            request.setAttribute("exito", "ERROR");
                            request.setAttribute("mensaje", "No hay disponibilidad del videojuego ingresado.");
                            request.getRequestDispatcher("rentarVideojuego.jsp").forward(request, response);
                       }
                       else{
                           for (Renta rentaCliente : fachada.consultarRentasVideojuegos(new Cliente(numCred))) {
                               if(rentaCliente.getArticulo().getNumCatalogo().equals(catalago)){
                                    request.setAttribute("exito", "ERROR");
                                   request.setAttribute("mensaje", "El cliente con credencial "+numCred+" ya tiene una renta para el videojuego con catálago "+catalago+".");
                            request.getRequestDispatcher("rentarVideojuego.jsp").forward(request, response);
                               }
                           }
                           
                       }
                       
                   }
                       
                    Videojuego video = fachada.obten(new Videojuego(catalago));
                    Articulo articulo = new Articulo(catalago, video.getTitulo(), video.getGenero(), video.getClasificacion());
                    Renta renta = new Renta(fachada.obten(new Cliente(numCred)), articulo, new Fecha(), tiempo);
                    fachada.rentarVideojuego(renta);
                     request.setAttribute("exito", "!LISTO¡");
                     request.setAttribute("mensaje", "El cliente con credencial "+numCred+" rentó el videojuego con catálago "+catalago+".");
                      request.getRequestDispatcher("rentarVideojuego.jsp").forward(request, response);
                      
                
                break;
            case "Devolver" :
                 numCred = request.getParameter("cred");
                 catalago= request.getParameter("catalago");
                 if(!fachada.consultarClientes().contains(new Cliente(numCred))){
                      request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "No existe ningun cliente con credencial "+numCred+" registrado.");
                      request.getRequestDispatcher("devolverVideojuego.jsp").forward(request, response);
                      return;
                 }
                 if(!fachada.consultarVideojuegos().contains(new Videojuego(catalago))){
                      request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "No existe ningun videojuego con catálago "+catalago+" registrado.");
                      request.getRequestDispatcher("devolverVideojuego.jsp").forward(request, response);
                      return;
                 }
                 for (Renta rentaClt : fachada.consultarRentasVideojuegos(new Cliente(numCred))) {
                    if(rentaClt.getArticulo().getNumCatalogo().equals(catalago)){
                        fachada.devolverVideojuego(rentaClt);
                        request.setAttribute("exito", "!LISTO¡");
                     request.setAttribute("mensaje", "El cliente con credencial "+numCred+"devolvió el videojuego con catálago "+ catalago+".");
                      request.getRequestDispatcher("devolverVideojuego.jsp").forward(request, response);
                      return;
                    }
                }
                 request.setAttribute("exito", "ERROR!");
                     request.setAttribute("mensaje", "El cliente con credencial "+ numCred+" no tiene rentado el videojuego con catálogo "+catalago+".");
                      request.getRequestDispatcher("devolverVideojuego.jsp").forward(request, response);
            
            case "Consultar Rentas":
                 numCred = request.getParameter("cred");
                 if(!fachada.consultarClientes().contains(new Cliente(numCred))){
                      request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "No existe ningun cliente con credencial "+numCred+" registrado.");
                      request.getRequestDispatcher("consultaRentasCliente.jsp").forward(request, response);
                      return;
                 }
                 else{
                     
                     List<Renta> listaRentas = fachada.consultarRentasVideojuegos(new Cliente(numCred));
                     if(listaRentas.isEmpty()){
                        request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "El cliente con credencial "+numCred+" no tiene rentas registradas.");
                      request.getRequestDispatcher("consultaRentasCliente.jsp").forward(request, response); 
                      return;
                     }
                     Cliente cliente = (Cliente)listaClientes.get(listaClientes.indexOf(new Cliente(numCred)));
                     request.setAttribute("cliente", cliente);
                     request.setAttribute("listaRentas", listaRentas);
                    
                      request.getRequestDispatcher("rentasCliente.jsp").forward(request, response);
                      return;
                 }
            case "Consultar Periodo":
                String sDesde = request.getParameter("desde");
                String sHasta = request.getParameter("hasta");
                Fecha desde=null, hasta=null;
                if(sDesde.length()==10 && sHasta.length()==10){
                    try{
                        String[] datosDesde= sDesde.split("-");
                        String[] datosHasta= sHasta.split("-");
                        desde= new Fecha(Integer.parseInt(datosDesde[2]), Integer.parseInt(datosDesde[1]), Integer.parseInt(datosDesde[0]));
                        hasta= new Fecha(Integer.parseInt(datosHasta[2]), Integer.parseInt(datosHasta[1]), Integer.parseInt(datosHasta[0]));
                        Periodo periodo = new Periodo(desde, hasta);
               List<Renta> listaRentas = fachada.consultarRentasVideojuegos(periodo);
               
               if(listaRentas.size()==0){
                   request.setAttribute("exito", "INFORMACIÖN");
                     request.setAttribute("mensaje", "No existen rentas dentro del periodo "+sDesde+" - "+sHasta);
                      request.getRequestDispatcher("consultaRentasPeriodo.jsp").forward(request, response);
                      return;
               }
               request.setAttribute("desde", sDesde);
               request.setAttribute("hasta", sHasta);
               request.setAttribute("listaRentas", listaRentas);
                    
                    request.getRequestDispatcher("rentasPeriodo.jsp").forward(request, response); 
                    }catch(Exception e){
                         request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "El formato de las fechas no es correcto.");
                      request.getRequestDispatcher("consultaRentasPeriodo.jsp").forward(request, response);
                      return;
                    }
                }
                else{
                     request.setAttribute("exito", "ERROR");
                     request.setAttribute("mensaje", "El formato de las fechas no es correcto.");
                      request.getRequestDispatcher("consultaRentasPeriodo.jsp").forward(request, response);
                }
                
//              
                      break;
        }
    }catch(PersistenciaException e){
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
