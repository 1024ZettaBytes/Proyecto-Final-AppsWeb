<%-- 
    Document   : consultaClienteCred
    Created on : 3/12/2018, 03:51:49 PM
    Author     : mario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
       
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
    
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" href="css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="scripts/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="scripts/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="scripts/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        
        <link href="css/Estilos.css" rel="stylesheet" type="text/css"/>
        <title>Consultar Cliente</title>
    </head>
    <body>
         <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div id="mensaje" class="modal-body">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                </div>
            </div>
        </div>
    </div>
        <nav>
            <ul>
                <li><a href="index.jsp"><img  src="imgs/home.png" alt=""/></a></li>                
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">Rentas</a>
                    <div class="dropdown-content">
                        <a href="rentarVideojuego.jsp">Rentar un videojuego</a>                        
                        <a href="devolverVideojuego.jsp">Devolver un videojuego</a>
                        <a href="consultaRentasCliente.jsp">Consultar rentas de un cliente</a>                        
                        <a href="consultaRentasPeriodo.jsp">Consultar rentas dado un periodo de fechas</a>
                    </div>
                </li>

                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">Clientes</a>
                    <div class="dropdown-content">
                        <a href="agregarCliente.jsp">Agregar</a>                        
                        <a href="eliminaCliente.jsp">Eliminar</a>                         
                        <a href="controlClientes?tarea=consultaClientes">Consultar lista de clientes</a>
                        <a href="consultaClienteCred.jsp">Consultar cliente por ID</a>                        

                    </div>

                </li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">Videojuegos</a>
                    <div class="dropdown-content">                       
                        <a href="agregarVideojuego.jsp">Agregar videojuego</a>
                        <a href="controlVideojuegos?tarea=consultarRentados">Videojuegos Rentados</a>
                    </div>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">Inventario</a>
                    <div class="dropdown-content">                       
                        <a href=agregarInventario.jsp>Inventariar unidades</a>                        
                        <a href="eliminarInventario.jsp">Desinventariar videojuego</a>                                             
                    </div>
                </li>
                <li><a href="controlPrincipal?tarea=logout">Logout</a></li>
            </ul>
        </nav>
        <div class="contenido">
            <h1>Consultar Cliente</h1>
            <div class="form-style-8">
                <h2>Ingresa la credencial del cliente</h2>
                <form action="operacionesClientes">
                    <input type="text" name="credencial" placeholder="Número de credencial" required/><br/>
                    <input type="submit" name="tarea" value="Buscar por Credencial"/>
                </form>
            </div>
        </div>
    </body>
    <%
        if (request.getAttribute("exito") != null) {
            String titulo = String.valueOf(request.getAttribute("exito"));
            String mensaje = String.valueOf(request.getAttribute("mensaje"));
            out.print("<script>$('#myModal').modal('show') "
                    + "</script>");
            out.print("<script>"
                    + "document.getElementById('exampleModalLabel').innerHTML = '" + titulo + "';"
                    + "document.getElementById('mensaje').innerHTML = '" + mensaje + "';"
                    + "</script>");
            request.setAttribute("exito", null);
        }
    %>
</html>
