<%-- 
    Document   : loginError
    Created on : 6/12/2018, 04:41:37 AM
    Author     : Eduardo Ramírez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
    if(request.getAttribute("loginError")==null){
       
 Cookie loginCookie = null;
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("usuario")){
    			response.sendRedirect("index.html");
    			break;
    		}
                response.sendRedirect("login.jsp");
                
    	}
    	}
    }
    
%>
<head>

  <meta charset="UTF-8">

  <title>Iniciar Sesión</title>

 

    <link rel="stylesheet" href="css/Estilos.css" media="screen" type="text/css" />

</head>

<body>

 
    <div class="contenido">
    
  <div class="form-style-8">
    <h2>Iniciar Sesión</h2><br>
    <form method="POST" action="controlPrincipal">
    <input type="text" name="usuario" placeholder="Usuario" required>
    <input type="password" name="contra" placeholder="Contraseña" required>
    <input type="submit" name="tarea" class="login login-submit" value="LOGIN">
    <p style="color:red">Credenciales Incorrectas!</p>
     <p>(La se sesión expirará en 30 minutos)</p>
      
  </form>

  </div>
</div>

</body>

</html>
