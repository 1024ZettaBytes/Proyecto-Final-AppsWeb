<%-- 
    Document   : soloUnCliente
    Created on : 3/12/2018, 04:01:53 PM
    Author     : mario
--%>

<%@page import="objetosNegocio.Cliente"%>
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
        <link href="css/Estilos.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">     
        <link rel="stylesheet" type="text/css" href="css/tablas.css">
        <title>Busqueda cliente</title>
    </head>
    <body>
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

            <%
                Cliente c = (Cliente) request.getAttribute("c");

                
                   
                    out.println("<h1>Datos del cliente</>");
                    out.println("<div class='table100 ver2 m-b-110'> <table data-vertable='ver2'><thead><tr class='row100 head'><th class='column100 column1' data-column='column1'>Credencial</th><th class='column100 column2' data-column='column2'>Nombre</th><th class='column100 column3' data-column='column3'>Direccion</th><th class='column100 column4' data-column='column4'>Teléfono</th></tr></thead><tbody>");
                    out.println("<tr class='row100'>");
                    out.println("<td class='column100 column1' data-column='column1'>" + c.getNumCredencial() + "</td>");
                    out.println("<td class='column100 column2' data-column='column2'>" + c.getNombre() + "</td>");
                    out.println("<td class='column100 column3' data-column='column3'>" + c.getDireccion() + "</td>");
                    out.println("<td class='column100 column4' data-column='column4'>" + c.getTelefono() + "</td>");
                    out.println("</tr>");
                    out.println("</tbody > </table > </div>");
                
            %>
        </div>
        <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
        <script src="vendor/bootstrap/js/popper.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="vendor/select2/select2.min.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
