<%-- 
    Document   : soloUnaRenta
    Created on : 5/12/2018, 08:19:17 PM
    Author     : Eduardo Ramírez
--%>

<%@page import="objetosNegocio.Renta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <% 
        
            Renta c = (Renta) request.getAttribute("renta");
            
       
        if(c==null){
            out.println("<h1>No se pudo encontrar la renta</>");
        }
        else{
        out.println("<h1>La renta fue encontrado</h1>");
       
        }


        %>
    </body>
</html>
